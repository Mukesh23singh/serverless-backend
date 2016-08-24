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
import com.amazonaws.services.cognitosync.model.RecordPatch;
import com.amazonaws.services.cognitosync.model.UpdateRecordsRequest;
import com.amazonaws.services.cognitosync.model.UpdateRecordsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.angrytit.model.CommonRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        String idToken = request.getIdToken();
        log.info("Login user with id token : {}", idToken);

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

        AmazonCognitoSync syncClient = new AmazonCognitoSyncClient(sessionCredentials);
        ListDatasetsRequest syncRequest = new ListDatasetsRequest();
        syncRequest.setIdentityId(identityId);
        syncRequest.setIdentityPoolId(identityPoolId);

        ListDatasetsResult syncResp = syncClient.listDatasets(syncRequest);

        UpdateRecordsRequest updateRecordsRequest = new UpdateRecordsRequest();
        updateRecordsRequest.setDatasetName("SimpleSet");
        updateRecordsRequest.setIdentityId(identityId);
        updateRecordsRequest.setSyncSessionToken(credentials.getSessionToken());
        RecordPatch patch = new RecordPatch();
        patch.setKey("Key");
        patch.setValue("Value");
        List<RecordPatch> list = new ArrayList<RecordPatch>();
        list.add(patch);
        updateRecordsRequest.setRecordPatches(list);
        UpdateRecordsResult updateRecordsResult = syncClient.updateRecords(updateRecordsRequest);

        return syncResp.getDatasets();
    }
}
