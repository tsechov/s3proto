package sun.net.www.protocol.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
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


    public static final String AWS_SOCKET_TIMEOUT = "AWS_SOCKET_TIMEOUT";
    public static final int DEFAULT_AWS_SOCKET_TIMEOUT = 30000;

    protected URLConnection openConnection(URL url) throws IOException {
        checkArgument(s3InUrl(url) > 0, "invalid s3 url. missing s3.amazonaws.com suffix from host: %s", url.getHost());

        return new URLConnection(url) {

            @Override
            public InputStream getInputStream() throws IOException {

                String bucket = url.getHost().substring(0, s3InUrl(url));

                String key = url.getPath().substring(1);

                String timeout = getProperty(AWS_SOCKET_TIMEOUT, String.valueOf(DEFAULT_AWS_SOCKET_TIMEOUT));

                BasicAWSCredentials credentials = credentialsBuilder().getCredentials(url);

                ClientConfiguration client = configuration();

                client.setSocketTimeout(Integer.parseInt(timeout));

                AmazonS3Client amazonS3Client = s3Client(credentials, client);

                GetObjectRequest request = request(bucket, key);

                S3Object s3obj = amazonS3Client.getObject(request);

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


    protected CredentialsBuilder credentialsBuilder() {
        return new CredentialsBuilder();
    }

    protected GetObjectRequest request(String bucket, String key) {
        return new GetObjectRequest(bucket, key);
    }

    protected AmazonS3Client s3Client(BasicAWSCredentials credentials, ClientConfiguration client) {
        return new AmazonS3Client(credentials, client);
    }

    public ClientConfiguration configuration() {
        return new ClientConfiguration();
    }

    private int s3InUrl(URL url) {
        return url.getHost().indexOf(".s3.amazonaws.com");
    }


}