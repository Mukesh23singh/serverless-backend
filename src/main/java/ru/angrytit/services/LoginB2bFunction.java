package ru.angrytit.services;

import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;
import com.amazonaws.services.cognitoidentity.model.GetIdRequest;
import com.amazonaws.services.cognitoidentity.model.GetIdResult;
import com.amazonaws.services.cognitosync.AmazonCognitoSync;
import com.amazonaws.services.cognitosync.AmazonCognitoSyncClient;
import com.amazonaws.services.cognitosync.model.ListDatasetsRequest;
import com.amazonaws.services.cognitosync.model.ListDatasetsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.angrytit.model.CommonRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class LoginB2bFunction implements HandleService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AmazonCognitoIdentity awsCognitoIdentityClient;

    private final String identityPoolId;

    private final String providerName;

    public LoginB2bFunction(AmazonCognitoIdentity awsCognitoIdentityClient, String identityPoolId, String providerName) {
        this.awsCognitoIdentityClient = awsCognitoIdentityClient;
        this.identityPoolId = identityPoolId;
        this.providerName = providerName;
    }

    @Override
    public Object handle(CommonRequest request) {
        String accessToken = request.getAccessToken();
        log.info("Login user with access token : {}", accessToken);

        GetIdRequest idRequest = new GetIdRequest();
        idRequest.setIdentityPoolId(identityPoolId);

        Map providerTokens = new HashMap();
        providerTokens.put(providerName, accessToken);
        idRequest.setLogins(providerTokens);

        GetIdResult idResp = awsCognitoIdentityClient.getId(idRequest);

        String identityId = idResp.getIdentityId();

        log.info("Got an identity id for user with access token : {}, identity id : {}", accessToken, identityId);

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

        log.info("Got temporary credentials with expiration : {} for user with access token : {}",
                credentials.getExpiration(), accessToken);

        AmazonCognitoSync syncClient = new AmazonCognitoSyncClient(sessionCredentials);
        ListDatasetsRequest syncRequest = new ListDatasetsRequest();
        syncRequest.setIdentityId(identityId);
        syncRequest.setIdentityPoolId(identityPoolId);

        ListDatasetsResult syncResp = syncClient.listDatasets(syncRequest);

        return syncResp.getDatasets();
    }
}
