package ru.angrytit.services;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.ChangePasswordRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.angrytit.model.CommonRequest;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class ChangePasswordB2bFunction implements HandleService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    public ChangePasswordB2bFunction(AWSCognitoIdentityProvider awsCognitoIdentityProvider) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
    }

    @Override
    public Object handle(CommonRequest request) {
        String accessToken = request.getAccessToken();
        log.info("Change password for user with access token : {}", accessToken);
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest().
                withAccessToken(accessToken).
                withPreviousPassword(request.getPreviousPassword()).
                withProposedPassword(request.getProposedPassword());

        awsCognitoIdentityProvider.changePassword(changePasswordRequest);
        log.info("Password was changed successfully for user with access token : {}", accessToken);
        return "OK";
    }
}
