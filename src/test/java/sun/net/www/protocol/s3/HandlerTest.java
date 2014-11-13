package sun.net.www.protocol.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static sun.net.www.protocol.s3.Randoms.randomString;

@RunWith(MockitoJUnitRunner.class)
public class HandlerTest {


    @Mock
    private GetObjectRequest request;
    @Mock
    private AmazonS3Client s3Client;
    @Mock
    private ClientConfiguration configMock;
    @Mock
    private CredentialsProviderChainBuilder credsBuilder;
    @Mock
    private AWSCredentialsProvider credsProvider;
    @Mock
    private AWSCredentials creds;
    @Mock
    private S3ClientBuilder clientBuilder;
    @Mock
    private AmazonS3 s3;

    @Mock
    private S3UrlConnection connection;

    @Test
    public void getInputStream() throws IOException {
        Handler target = spy(new Handler());
        String expectedBucket = randomString();
        URL url = new URL(String.format("s3://%s.s3.amazonaws.com/baz", expectedBucket));

        doReturn(credsBuilder).when(target).credentials();
        when(credsBuilder.build(url)).thenReturn(credsProvider);
        when(credsProvider.getCredentials()).thenReturn(creds);
        doReturn(clientBuilder).when(target).client();
        when(clientBuilder.build(creds)).thenReturn(s3);
        doReturn(connection).when(target).connection(url, s3, expectedBucket);

        URLConnection result = target.openConnection(url);

        assertEquals(connection, result);
    }


}
