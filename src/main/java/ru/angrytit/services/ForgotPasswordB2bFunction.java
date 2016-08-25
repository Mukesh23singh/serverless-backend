package ru.angrytit.services;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.ForgotPasswordRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.angrytit.model.CommonRequest;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class ForgotPasswordB2bFunction implements HandleService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    private final String applicationClientId;

    public ForgotPasswordB2bFunction(AWSCognitoIdentityProvider awsCognitoIdentityProvider, String applicationClientId) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
        this.applicationClientId = applicationClientId;
    }

    @Override
    public Object handle(CommonRequest request) {
        String email = request.getEmail();
        log.info("Forgot password for user with email : {}", email);
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest().
                withClientId(applicationClientId).
                withUsername(email);
        awsCognitoIdentityProvider.forgotPassword(forgotPasswordRequest);
        return "OK";
    }
}
