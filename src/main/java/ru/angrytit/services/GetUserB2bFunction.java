package ru.angrytit.services;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.angrytit.model.CommonRequest;
import ru.angrytit.model.UserAttributes;

import static java.util.stream.Collectors.toList;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class GetUserB2bFunction implements HandleService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    public GetUserB2bFunction(AWSCognitoIdentityProvider awsCognitoIdentityProvider) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
    }

    @Override
    public Object handle(CommonRequest request) {
        String accessToken = request.getAccessToken();
        log.info("Get user info for access token : {}", accessToken);
        GetUserRequest getUserRequest = new GetUserRequest().withAccessToken(accessToken);
        GetUserResult result = awsCognitoIdentityProvider.getUser(getUserRequest);
        log.info("Got user info for access token : {}, user name : {}", accessToken, result.getUsername());
        return result.getUserAttributes().stream().
                peek(each -> log.info("Name : {}, value : {}", each.getName(), each.getValue())).
                filter(each -> UserAttributes.contains(each.getName())).
                peek(each -> log.info("Name : {}, value : {}", each.getName(), each.getValue())).
                collect(toList());
    }
}
