package sun.net.www.protocol.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static sun.net.www.protocol.s3.S3ClientBuilder.AWS_SOCKET_TIMEOUT;
import static sun.net.www.protocol.s3.S3ClientBuilder.DEFAULT_AWS_SOCKET_TIMEOUT;

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

    @After
    public void tearDown() {
        System.clearProperty(AWS_SOCKET_TIMEOUT);
    }

    @Test
    public void build() throws Exception {
        doTest(DEFAULT_AWS_SOCKET_TIMEOUT);
    }

    @Test
    public void buildWithTimeout() throws Exception {
        int expectedTimeout = RandomUtils.nextInt(0, 10000);
        System.setProperty(AWS_SOCKET_TIMEOUT, String.valueOf(expectedTimeout));
        doTest(expectedTimeout);
    }

    private void doTest(int timeout) {
        target = spy(new S3ClientBuilder());
        doReturn(clientConfiguration).when(target).configuration();
        doReturn(s3Client).when(target).s3Client(creds, clientConfiguration);

        AmazonS3 result = target.build(creds);

        verify(clientConfiguration).setSocketTimeout(timeout);

        assertEquals(s3Client, result);
    }
}