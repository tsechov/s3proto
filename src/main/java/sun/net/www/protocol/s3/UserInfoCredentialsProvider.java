package sun.net.www.protocol.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

import java.net.URL;

import static com.google.common.base.Preconditions.checkArgument;

public class UserInfoCredentialsProvider implements AWSCredentialsProvider {

    private final URL url;

    public UserInfoCredentialsProvider(URL url) {
        this.url = url;
    }

    @Override
    public AWSCredentials getCredentials() {
        if (url.getUserInfo() != null) {
            String[] creds = url.getUserInfo().split("[:]");
            checkArgument(creds.length == 2, "invalid userinfo in url");
            return new BasicAWSCredentials(creds[0], creds[1]);
        } else {
            return new AnonymousAWSCredentials();
        }
    }

    @Override
    public void refresh() {

    }


}
