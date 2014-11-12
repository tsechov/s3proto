package sun.net.www.protocol.s3;

import com.amazonaws.auth.AWSCredentials;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserInfoCredentialsProviderTest {
    private UserInfoCredentialsProvider target;


    @Test
    public void testGetCredentialsWithUserInfo() throws Exception {
        String expectedAccessKeyId = "foo";
        String expectedSecretKey = "bar/baz";
        URL url = new URL(String.format("s3://%s:%s@dev-shoehorn.s3.amazonaws.com/foo", expectedAccessKeyId, expectedSecretKey));
        target = new UserInfoCredentialsProvider(url);
        AWSCredentials result = target.getCredentials();

        assertEquals(expectedAccessKeyId, result.getAWSAccessKeyId());
        assertEquals(expectedSecretKey, result.getAWSSecretKey());
    }

    @Test
    public void testGetCredentialsWithNoUserInfo() throws Exception {

        URL url = new URL("s3://dev-shoehorn.s3.amazonaws.com/foo");
        target = new UserInfoCredentialsProvider(url);
        AWSCredentials result = target.getCredentials();

        assertNull(result.getAWSAccessKeyId());
        assertNull(result.getAWSSecretKey());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCredentialsWithBogusUserInfo() throws Exception {
        URL url = new URL("s3://asdfa@dev-shoehorn.s3.amazonaws.com/foo");
        target = new UserInfoCredentialsProvider(url);
        target.getCredentials();
    }
}