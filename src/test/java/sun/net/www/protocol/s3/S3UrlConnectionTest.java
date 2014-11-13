package sun.net.www.protocol.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static sun.net.www.protocol.s3.Randoms.randomString;

@RunWith(MockitoJUnitRunner.class)
public class S3UrlConnectionTest {


    @Mock
    private AWSCredentials creds;
    @Mock
    private AmazonS3 s3;
    @Mock
    private S3Object s3Object;
    @Mock
    private S3ObjectInputStream expectedResult;

    @Mock
    private GetObjectRequest request;


    @Test
    public void getInputStream() throws IOException {

        URL url = new URL("s3://foo.bar/baz");

        S3UrlConnection target = spy(new S3UrlConnection(url, s3, request));


        when(s3.getObject(request)).thenReturn(s3Object);
        when(s3Object.getObjectContent()).thenReturn(expectedResult);

        InputStream result = target.getInputStream();


        assertEquals(expectedResult, result);

    }

    @Test
    public void connect() throws IOException {
        URL url = new URL("s3://foo.bar/baz");

        S3UrlConnection target = new S3UrlConnection(url, s3, request);

        target.connect();
    }
}