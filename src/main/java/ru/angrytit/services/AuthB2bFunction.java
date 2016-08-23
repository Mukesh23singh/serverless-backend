package ru.angrytit.services;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.angrytit.model.CommonRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class AuthB2bFunction implements HandleService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String USERNAME_ATTR = "USERNAME";
    private static final String PASSWORD_ATTR = "PASSWORD";

    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    private final String applicationClientId;

    private final String userPoolId;

    public AuthB2bFunction(AWSCognitoIdentityProvider awsCognitoIdentityProvider, String applicationClientId, String userPoolId) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
        this.applicationClientId = applicationClientId;
        this.userPoolId = userPoolId;
    }

    @Override
    public String handle(CommonRequest request) {
        String email = request.getEmail();
        String pass = request.getEmail();

        log.info("Auth for user with email : {}", email);

        Map<String, String> authParams = new HashMap<>();
        authParams.put(USERNAME_ATTR, email);
        authParams.put(PASSWORD_ATTR, pass);

        AdminInitiateAuthRequest adminInitiateAuthRequest = new AdminInitiateAuthRequest().
                withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH).
                withAuthParameters(authParams).
                withClientId(applicationClientId).
                withUserPoolId(userPoolId);
        AdminInitiateAuthResult result = awsCognitoIdentityProvider.adminInitiateAuth(adminInitiateAuthRequest);
        String accessToken = result.getAuthenticationResult().getAccessToken();

        log.info("Auth for user with email : {} was successfully with accessToken : {}", email, accessToken);

        return accessToken;
    }
}
