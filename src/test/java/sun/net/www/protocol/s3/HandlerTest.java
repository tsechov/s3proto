package sun.net.www.protocol.s3;

import com.amazonaws.ClientConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Handler.class)
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
//    @Mock
//    private ClientConfiguration configMock;

    @Test
    public void testNew() throws Exception {

        System.out.println("blah");
        ClientConfiguration configMock = PowerMockito.mock(ClientConfiguration.class);
        PowerMockito.whenNew(ClientConfiguration.class).withNoArguments().thenReturn(configMock);
//
//        Handler target = new Handler();
//        ClientConfiguration result = target.configuration();
//        Assert.assertEquals(configMock, result);

    }

}
