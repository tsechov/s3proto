package sun.net.www.protocol.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.util.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)

public class HandlerTest {

    @Mock
    private UserInfoCredentialsProvider credentialsBuilder;
    @Mock
    private GetObjectRequest request;
    @Mock
    private AmazonS3Client s3Client;
    @Mock
    private ClientConfiguration configMock;

    @Test
    public void testWithEnvVars() throws Exception {
        System.setProperty(SDKGlobalConfiguration.ACCESS_KEY_SYSTEM_PROPERTY, "AKIAI6IQPXKOPXEVOM6A");
        System.setProperty(SDKGlobalConfiguration.SECRET_KEY_SYSTEM_PROPERTY, "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");
        URL url = new URL("s3://dev-shoehorn.s3.amazonaws.com/foo");
        InputStream stream = url.openStream();
        String result = IOUtils.toString(stream);

        assertEquals("bar", result);

    }

    @Test
    public void test() throws Exception {
        URL url = new URL("s3://AKIAI6IQPXKOPXEVOM6A:fbRcna3wva0dAmqrFVlZHTtN/EFyG4je6zz8iqlN@dev-shoehorn.s3.amazonaws.com/foo");
        InputStream stream = url.openStream();
        String result = IOUtils.toString(stream);

        assertEquals("bar", result);

    }


}
