package sun.net.www.protocol.s3;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

import java.net.URL;

public class CredentialsProviderChainBuilder {

    public AWSCredentialsProvider build(URL url) {
        UserInfoCredentialsProvider userInfo = userInfoProvider(url);
        DefaultAWSCredentialsProviderChain defaultChain = defaultProvider();

        return chain(userInfo, defaultChain);
    }

    protected AWSCredentialsProvider chain(UserInfoCredentialsProvider userInfo, DefaultAWSCredentialsProviderChain defaultChain) {
        return new AWSCredentialsProviderChain(userInfo, defaultChain);
    }

    protected DefaultAWSCredentialsProviderChain defaultProvider() {
        return new DefaultAWSCredentialsProviderChain();
    }

    protected UserInfoCredentialsProvider userInfoProvider(URL url) {
        return new UserInfoCredentialsProvider(url);
    }
}
