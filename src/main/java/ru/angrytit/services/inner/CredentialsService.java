package ru.angrytit.services.inner;

import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;
import com.amazonaws.services.cognitoidentity.model.GetIdRequest;
import com.amazonaws.services.cognitoidentity.model.GetIdResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class CredentialsService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AmazonCognitoIdentity awsCognitoIdentityClient;

    private final String identityPoolId;

    private final String providerName;

    public CredentialsService(AmazonCognitoIdentity awsCognitoIdentityClient, String identityPoolId, String providerName) {
        this.awsCognitoIdentityClient = awsCognitoIdentityClient;
        this.identityPoolId = identityPoolId;
        this.providerName = providerName;
    }

    public AWSSessionCredentials getSessionCredentials(String idToken) {
        log.info("Get credentials for user with id token : {}", idToken);

        GetIdRequest idRequest = new GetIdRequest();
        idRequest.setIdentityPoolId(identityPoolId);

        Map providerTokens = new HashMap();
        providerTokens.put(providerName, idToken);
        idRequest.setLogins(providerTokens);

        GetIdResult idResp = awsCognitoIdentityClient.getId(idRequest);

        String identityId = idResp.getIdentityId();

        log.info("Got an identity id for user with id token : {}, identity id : {}", idToken, identityId);

        GetCredentialsForIdentityRequest credentialsRequest = new GetCredentialsForIdentityRequest().
                withIdentityId(identityId).
                withLogins(providerTokens);
        GetCredentialsForIdentityResult result = awsCognitoIdentityClient.getCredentialsForIdentity(credentialsRequest);

        Credentials credentials = result.getCredentials();

        AWSSessionCredentials sessionCredentials = new BasicSessionCredentials(
                credentials.getAccessKeyId(),
                credentials.getSecretKey(),
                credentials.getSessionToken()
        );

        log.info("Got temporary credentials with expiration : {} for user with id token : {}",
                credentials.getExpiration(), idToken);

        return sessionCredentials;
    }
}
