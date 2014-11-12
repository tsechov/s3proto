package sun.net.www.protocol.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URL;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class S3ClientBuilderTest {
    @Mock
    private UserInfoCredentialsProvider credentialsBuilder;

    @Mock
    private ClientConfiguration clientConfiguration;

    private S3ClientBuilder target;

    @Before
    public void setup() {
        target = spy(new S3ClientBuilder(credentialsBuilder));
        doReturn(clientConfiguration).when(target).configuration();
    }

    @Test
    public void testBuild() throws Exception {
        URL url = new URL("s3://dev-shoehorn.s3.amazonaws.com/foo");
        AmazonS3 result = target.build();
    }
}