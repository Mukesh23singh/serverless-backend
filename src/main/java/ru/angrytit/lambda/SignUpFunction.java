package ru.angrytit.lambda;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import ru.angrytit.lambda.model.SignUpRequest;

import java.util.ArrayList;
import java.util.List;

import static ru.angrytit.lambda.Config.APP_CLIENT_ID;
import static ru.angrytit.lambda.Config.REGION;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class SignUpFunction implements RequestHandler<SignUpRequest, String> {

    @Override
    public String handleRequest(SignUpRequest request, Context context) {
        AWSCognitoIdentityProvider provider =
                AWSCognitoIdentityProviderClientBuilder.
                        standard().
                        withRegion(REGION.getValue()).
                        build();
        LambdaLogger log = context.getLogger();

        log.log("signUp started");

        List<AttributeType> attributeTypes = new ArrayList<>();
        attributeTypes.add(new AttributeType().withName("email").withValue(request.getEmail()));

        com.amazonaws.services.cognitoidp.model.SignUpRequest signUpRequest =
                new com.amazonaws.services.cognitoidp.model.SignUpRequest().
                        withClientId(APP_CLIENT_ID.getValue()).
                        withUserAttributes(attributeTypes).
                        withUsername(request.getUserName()).
                        withPassword(request.getPassword());


        provider.signUp(signUpRequest);

        log.log("signUp finished");
        return "Ok";
    }
}
