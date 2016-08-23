package ru.angrytit.services;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.angrytit.model.CommonRequest;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class SignUpConfirmManufactureFunction implements HandleService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    private final String applicationClientId;

    public SignUpConfirmManufactureFunction(AWSCognitoIdentityProvider awsCognitoIdentityProvider, String applicationClientId) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
        this.applicationClientId = applicationClientId;
    }

    @Override
    public String handle(CommonRequest request) {

        String confirmCode = request.getCode();
        String userName = request.getId();

        log.info("Confirm signUp for user name : {} with code : {}", userName, confirmCode);
        ConfirmSignUpRequest confirmSignUpRequest = new ConfirmSignUpRequest().
                withClientId(applicationClientId).
                withConfirmationCode(confirmCode).
                withUsername(userName);
        awsCognitoIdentityProvider.confirmSignUp(confirmSignUpRequest);
        log.info("Confirm signUp for user name : {} was successfully", userName);

        return "OK";
    }
}
