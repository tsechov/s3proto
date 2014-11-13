package sun.net.www.protocol.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.common.base.Function;
import com.google.common.base.Optional;

import static com.google.common.base.Optional.fromNullable;
import static java.lang.Integer.parseInt;
import static java.lang.System.getProperty;


public class S3ClientBuilder {
    public static final String AWS_SOCKET_TIMEOUT = "AWS_SOCKET_TIMEOUT";
    public static final int DEFAULT_AWS_SOCKET_TIMEOUT = 30000;


    public AmazonS3 build(AWSCredentials creds) {

        ClientConfiguration client = configuration();
        client.setSocketTimeout(timeout().or(DEFAULT_AWS_SOCKET_TIMEOUT));
        return s3Client(creds, client);
    }

    protected AmazonS3 s3Client(AWSCredentials credentials, ClientConfiguration client) {
        return new AmazonS3Client(credentials, client);
    }


    protected ClientConfiguration configuration() {
        return new ClientConfiguration();
    }

    private Optional<Integer> timeout() {
        String timeout = getProperty(AWS_SOCKET_TIMEOUT);
        return fromNullable(timeout).transform(new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return parseInt(input);
            }
        });

    }
}
