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
import ru.angrytit.services.ChangePasswordB2bFunction;
import ru.angrytit.services.ForgotPasswordB2bFunction;
import ru.angrytit.services.ForgotPasswordConfirmB2bFunction;
import ru.angrytit.services.GetUserB2bFunction;
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

//    @Bean
//    public CredentialsService credentialsService() {
//        String providerName = "cognito-idp." + region + ".amazonaws.com/" + userPoolId;
//        return new CredentialsService(awsCognitoIdentityClient(), identityPoolId, providerName);
//    }

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

    @Bean(name = "GetUserB2bFunction")
    public GetUserB2bFunction userB2bFunction() {
        return new GetUserB2bFunction(awsCognitoIdentityProvider());
    }

    @Bean(name = "ChangePasswordB2bFunction")
    public ChangePasswordB2bFunction changePasswordB2bFunction() {
        return new ChangePasswordB2bFunction(awsCognitoIdentityProvider());
    }

    @Bean(name = "ForgotPasswordB2bFunction")
    public ForgotPasswordB2bFunction forgotPasswordB2bFunction() {
        return new ForgotPasswordB2bFunction(awsCognitoIdentityProvider(), applicationClientId);
    }

    @Bean(name = "ForgotPasswordConfirmB2bFunction")
    public ForgotPasswordConfirmB2bFunction forgotPasswordConfirmB2bFunction() {
        return new ForgotPasswordConfirmB2bFunction(awsCognitoIdentityProvider(), applicationClientId);
    }

}
