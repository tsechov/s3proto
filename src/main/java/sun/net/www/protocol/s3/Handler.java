package sun.net.www.protocol.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.GetObjectRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;


public class Handler extends URLStreamHandler {


    protected URLConnection openConnection(URL url) throws IOException {
        AmazonS3URI s3Uri = s3Uri(url);


        String bucket = s3Uri.getBucket();
        String key = s3Uri.getKey();

        AWSCredentials creds = credentials().build(url).getCredentials();

        AmazonS3 s3 = client().build(creds);

        return connection(url, s3, new GetObjectRequest(bucket, key));
    }

    private AmazonS3URI s3Uri(URL url) {
        try {
            return new AmazonS3URI(url.toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
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

    protected CredentialsProviderChainBuilder credentials() {
        return new CredentialsProviderChainBuilder();
    }

    protected S3UrlConnection connection(URL url, AmazonS3 s3, GetObjectRequest request) {
        return new S3UrlConnection(url, s3, request);
    }


    protected S3ClientBuilder client() {
        return new S3ClientBuilder();
    }


}