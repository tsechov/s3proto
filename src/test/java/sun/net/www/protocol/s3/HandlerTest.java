package sun.net.www.protocol.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.MalformedURLException;
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
        String expectedKey = randomString();
        URL url = new URL(String.format("s3://%s/%s", expectedBucket, expectedKey));

        doReturn(credsBuilder).when(target).credentials();
        when(credsBuilder.build(url)).thenReturn(credsProvider);
        when(credsProvider.getCredentials()).thenReturn(creds);
        doReturn(clientBuilder).when(target).client();
        when(clientBuilder.build(creds)).thenReturn(s3);
        ArgumentCaptor<GetObjectRequest> captor = ArgumentCaptor.forClass(GetObjectRequest.class);
        doReturn(connection).when(target).connection(eq(url), eq(s3), captor.capture());

        URLConnection result = target.openConnection(url);

        assertEquals(expectedBucket, captor.getValue().getBucketName());
        assertEquals(expectedKey, captor.getValue().getKey());

        assertEquals(connection, result);
    }

    @Test
    public void integration() throws IOException {
        Handler target = new Handler();
        target.openConnection(new URL("s3://john:doe@foo/bar"));
    }



}
