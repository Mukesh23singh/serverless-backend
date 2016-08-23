package ru.angrytit.services;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.angrytit.model.CommonRequest;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
@Service("SignUpConfirmManufactureFunction")
public class SignUpConfirmManufactureFunction implements HandleService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    @Value("${application.client.id}")
    private String applicationClientId;

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
