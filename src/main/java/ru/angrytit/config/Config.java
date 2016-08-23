package ru.angrytit.config;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.angrytit.services.AuthB2bFunction;
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


    @Bean
    public AWSCognitoIdentityProvider awsCognitoIdentityProvider() {
        AWSCognitoIdentityProvider provider =
                AWSCognitoIdentityProviderClientBuilder.
                        standard().
                        withRegion(region).
                        build();
        return provider;
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
}
