package ru.angrytit.config;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.angrytit.services.SignUpConfirmManufactureFunction;
import ru.angrytit.services.SignUpManufacturerFunction;

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


    @Bean
    public AWSCognitoIdentityProvider awsCognitoIdentityProvider() {
        System.err.println("region : " + region + '\n');
        AWSCognitoIdentityProvider provider =
                AWSCognitoIdentityProviderClientBuilder.
                        standard().
                        withRegion(region).
                        build();
        return provider;
    }

    @Bean(name = "SignUpManufacturerFunction")
    public SignUpManufacturerFunction signUpManufacturerFunction() {
        System.err.println("applicationClientId : " + applicationClientId + '\n');
        return new SignUpManufacturerFunction(awsCognitoIdentityProvider(), applicationClientId);
    }

    @Bean(name = "SignUpConfirmManufactureFunction")
    public SignUpConfirmManufactureFunction signUpConfirmManufactureFunction() {
        System.err.println("applicationClientId : " + applicationClientId + '\n');
        return new SignUpConfirmManufactureFunction(awsCognitoIdentityProvider(), applicationClientId);
    }
}
