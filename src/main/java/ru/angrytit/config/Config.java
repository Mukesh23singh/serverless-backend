package ru.angrytit.config;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
@Configuration
@ComponentScan
public class Config {

    @Value("${default.aws.region}")
    private String region;

    @Bean
    public AWSCognitoIdentityProvider awsCognitoIdentityProvider() {
        AWSCognitoIdentityProvider provider =
                AWSCognitoIdentityProviderClientBuilder.
                        standard().
                        withRegion(region).
                        build();
        return provider;
    }
}
