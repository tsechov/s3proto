package sun.net.www.protocol.s3;

import com.amazonaws.auth.BasicAWSCredentials;

import java.net.URL;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.System.getProperty;

public class CredentialsBuilder {
    public static final String AWS_ACCESS_KEY_ID = "AWS_ACCESS_KEY_ID";
    public static final String AWS_SECRET_ACCESS_KEY = "AWS_SECRET_ACCESS_KEY";

    public BasicAWSCredentials getCredentials(URL url) {


        if (url.getUserInfo() != null) {
            String[] creds = url.getUserInfo().split("[:]");
            checkArgument(creds.length == 2, "invalid userinfo in url");
            return credentials(creds[0], creds[1]);
        } else {
            return credentials(getProperty(AWS_ACCESS_KEY_ID), getProperty(AWS_SECRET_ACCESS_KEY));
        }
    }

    protected BasicAWSCredentials credentials(String accessKey, String secretKey) {
        return new BasicAWSCredentials(accessKey, secretKey);
    }
}
