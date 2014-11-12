package sun.net.www.protocol.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.runners.MockitoJUnitRunner;

import java.net.URL;
import java.net.URLConnection;

@RunWith(MockitoJUnitRunner.class)

public class HandlerTest {

    //    @Test
//    public void test() throws Exception {
//        URL url = new URL("s3://AKIAI6IQPXKOPXEVOM6A:fbRcna3wva0dAmqrFVlZHTtN/EFyG4je6zz8iqlN@dev-shoehorn.s3.amazonaws.com/foo");
//        InputStream stream = url.openStream();
//        String result = IOUtils.toString(stream);
//
//        assertEquals("bar", result);
//
//    }
//
//    @Test
//    public void testWithEnvVars() throws Exception {
//        System.setProperty(CredentialsBuilder.AWS_ACCESS_KEY_ID, "AKIAI6IQPXKOPXEVOM6A");
//        System.setProperty(CredentialsBuilder.AWS_SECRET_ACCESS_KEY, "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");
//        URL url = new URL("s3://dev-shoehorn.s3.amazonaws.com/foo");
//        InputStream stream = url.openStream();
//        String result = IOUtils.toString(stream);
//
//        assertEquals("bar", result);
//
//    }

    @Mock
    private CredentialsBuilder credentialsBuilder;
    @Mock
    private GetObjectRequest request;
    @Mock
    private AmazonS3Client s3Client;
    @Mock
    private ClientConfiguration configMock;

    @Test
    public void testNew() throws Exception {


//
        Handler target = Mockito.spy(new Handler());
        Mockito.doReturn(configMock).when(target).configuration();


        ClientConfiguration result = target.configuration();
        Assert.assertEquals(configMock, result);

    }

}
