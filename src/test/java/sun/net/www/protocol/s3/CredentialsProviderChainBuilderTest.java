package sun.net.www.protocol.s3;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URL;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class CredentialsProviderChainBuilderTest {

    @Mock
    private DefaultAWSCredentialsProviderChain defaultChain;
    @Mock
    private UserInfoCredentialsProvider userInfo;
    @Mock
    private AWSCredentialsProvider chained;

    @Before
    public void setup() {

    }

    @Test
    public void testBuild() throws Exception {
        URL url = new URL("s3://foo.bar/baz");
        CredentialsProviderChainBuilder target = spy(new CredentialsProviderChainBuilder());
        doReturn(defaultChain).when(target).defaultProvider();
        doReturn(userInfo).when(target).userInfoProvider(url);
        doReturn(chained).when(target).chain(userInfo, defaultChain);

        AWSCredentialsProvider result = target.build(url);

        Assert.assertEquals(chained, result);

    }
}