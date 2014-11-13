package sun.net.www.protocol.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class S3ClientBuilderTest {
    @Mock
    private AWSCredentials creds;

    @Mock
    private ClientConfiguration clientConfiguration;

    @Mock
    private AmazonS3 s3Client;

    private S3ClientBuilder target;


    @Before
    public void setup() {
        target = spy(new S3ClientBuilder());
        doReturn(clientConfiguration).when(target).configuration();
    }

    @Test
    public void testBuild() throws Exception {
        target = spy(new S3ClientBuilder());
        doReturn(clientConfiguration).when(target).configuration();
        doReturn(s3Client).when(target).s3Client(creds, clientConfiguration);

        AmazonS3 result = target.build(creds);

        verify(clientConfiguration).setSocketTimeout(S3ClientBuilder.DEFAULT_AWS_SOCKET_TIMEOUT);

        Assert.assertEquals(s3Client, result);
    }
}