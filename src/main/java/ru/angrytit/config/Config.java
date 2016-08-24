package ru.angrytit.config;

import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClientBuilder;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.angrytit.services.AuthB2bFunction;
import ru.angrytit.services.LoginB2bFunction;
import ru.angrytit.services.SignUpB2bFunction;
import ru.angrytit.services.SignUpConfirmB2bFunction;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class Config {

    @Value("${default.aws.region}")
    private String region;

    @Value("${application.client.id}")
    private String applicationClientId;

    @Value("${user.pool.id}")
    private String userPoolId;

    @Value("${identity.pool.id}")
    private String identityPoolId;


    @Bean
    public AWSCognitoIdentityProvider awsCognitoIdentityProvider() {
        return AWSCognitoIdentityProviderClientBuilder.standard().withRegion(region).build();
    }

    @Bean
    public AmazonCognitoIdentity awsCognitoIdentityClient() {
        return AmazonCognitoIdentityClientBuilder.standard().withRegion(region).build();
    }

    @Bean(name = "SignUpB2bFunction")
    public SignUpB2bFunction signUpB2bFunction() {
        return new SignUpB2bFunction(awsCognitoIdentityProvider(), applicationClientId);
    }

    @Bean(name = "SignUpConfirmB2bFunction")
    public SignUpConfirmB2bFunction signUpConfirmB2bFunction() {
        return new SignUpConfirmB2bFunction(awsCognitoIdentityProvider(), applicationClientId);
    }

    @Bean(name = "AuthB2bFunction")
    public AuthB2bFunction authB2bFunction() {
        return new AuthB2bFunction(awsCognitoIdentityProvider(), applicationClientId, userPoolId);
    }

    @Bean(name = "LoginB2bFunction")
    public LoginB2bFunction loginB2bFunction() {
        String providerName = "idp." + region + ".amazonaws.com/" + userPoolId;
        return new LoginB2bFunction(awsCognitoIdentityClient(), identityPoolId, providerName);
    }
}
