package sun.net.www.protocol.s3;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.System.getProperty;


public class Handler extends URLStreamHandler {


    protected URLConnection openConnection(URL url) throws IOException {
        final int prefixEndPosition = s3InUrl(url);
        checkArgument(prefixEndPosition > 0, "invalid s3 url. missing s3.amazonaws.com suffix from host: %s", url.getHost());

        return new URLConnection(url) {

            @Override
            public InputStream getInputStream() throws IOException {

                String bucket = url.getHost().substring(0, prefixEndPosition);

                String key = url.getPath().substring(1);

                AmazonS3 s3 = client(credentials().build(url)).build();

                GetObjectRequest request = request(bucket, key);

                S3Object s3obj = s3.getObject(request);

                return s3obj.getObjectContent();

            }

            @Override
            public void connect() throws IOException {
            }
        };
    }


    @Override
    protected void parseURL(URL u, String spec, int start, int limit) {
        //got to hack around userinfo because AWS creds can contain '/'
        String userinfo = null;

        int atIndex = spec.indexOf("@");

        if (atIndex > -1) {
            userinfo = spec.substring(start + 2, atIndex);
            spec = u.getProtocol() + "://" + (spec.substring(atIndex + 1));
        }

        super.parseURL(u, spec, start, spec.length());

        setURL(
                u,
                u.getProtocol(),
                u.getHost(),
                u.getPort(),
                u.getAuthority(),
                userinfo,
                u.getPath(),
                u.getQuery(),
                u.getRef()
        );
    }


    protected GetObjectRequest request(String bucket, String key) {
        return new GetObjectRequest(bucket, key);
    }

    protected CredentialsProviderChainBuilder credentials() {
        return new CredentialsProviderChainBuilder();

    }

    protected S3ClientBuilder client(AWSCredentialsProvider credentialsProvider) {
        return new S3ClientBuilder(credentialsProvider);
    }


    private int s3InUrl(URL url) {
        return url.getHost().indexOf(".s3.amazonaws.com");
    }


}