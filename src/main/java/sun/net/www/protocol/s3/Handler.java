package sun.net.www.protocol.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import static com.google.common.base.Preconditions.checkArgument;


public class Handler extends URLStreamHandler {


    protected URLConnection openConnection(URL url) throws IOException {
        final int prefixEndPosition = s3InUrl(url);
        checkArgument(prefixEndPosition > 0, "invalid s3 url. missing s3.amazonaws.com suffix from host: %s", url.getHost());

        String bucket = url.getHost().substring(0, prefixEndPosition);

        AWSCredentials creds = credentials().build(url).getCredentials();

        AmazonS3 s3 = client().build(creds);

        return connection(url, s3, bucket);
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

    protected S3UrlConnection connection(URL url, AmazonS3 s3, String bucket) {
        return new S3UrlConnection(url, s3, bucket);
    }


    protected S3ClientBuilder client() {
        return new S3ClientBuilder();
    }


    private int s3InUrl(URL url) {
        return url.getHost().indexOf(".s3.amazonaws.com");
    }


}