package ru.angrytit.services;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.ConfirmForgotPasswordRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.angrytit.model.CommonRequest;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class ForgotPasswordConfirmB2bFunction implements HandleService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    private final String applicationClientId;

    public ForgotPasswordConfirmB2bFunction(AWSCognitoIdentityProvider awsCognitoIdentityProvider, String applicationClientId) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
        this.applicationClientId = applicationClientId;
    }

    @Override
    public Object handle(CommonRequest request) {
        String email = request.getEmail();
        String code = request.getCode();
        String newPassword = request.getProposedPassword();
        log.info("Confirm forgot password for user with email : {}");
        ConfirmForgotPasswordRequest confirmForgotPasswordRequest = new ConfirmForgotPasswordRequest().
                withClientId(applicationClientId).
                withUsername(email).
                withConfirmationCode(code).
                withPassword(newPassword);
        awsCognitoIdentityProvider.confirmForgotPassword(confirmForgotPasswordRequest);
        return "OK";
    }
}
