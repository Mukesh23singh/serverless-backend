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
public class SignUpManufacturerFunction implements RequestHandler<SignUpRequest, Void> {

    private static final String EMAIL_ATTR = "email";
    private static final String NAME_ATTR = "name";
//    private static final String TITLE_ATTR = "title";
//    private static final String BUSINESS_NAME = "business_name";

    @Override
    public Void handleRequest(SignUpRequest request, Context context) {
        AWSCognitoIdentityProvider provider =
                AWSCognitoIdentityProviderClientBuilder.
                        standard().
                        withRegion(REGION.getValue()).
                        build();
        LambdaLogger log = context.getLogger();

        log.log("signUp : started\n");


        List<AttributeType> attributeTypes = new ArrayList<>();
        attributeTypes.add(new AttributeType().withName(EMAIL_ATTR).withValue(request.getEmail()));
        attributeTypes.add(new AttributeType().withName(NAME_ATTR).withValue(request.getName()));
//        attributeTypes.add(new AttributeType().withName(TITLE_ATTR).withValue(request.getTitle()));
//        attributeTypes.add(new AttributeType().withName(BUSINESS_NAME).withValue(request.getBusinessName()));

        com.amazonaws.services.cognitoidp.model.SignUpRequest signUpRequest =
                new com.amazonaws.services.cognitoidp.model.SignUpRequest().
                        withClientId(APP_CLIENT_ID.getValue()).
                        withUsername(request.getUserName()).
                        withPassword(request.getPassword()).
                        withUserAttributes(attributeTypes);


        provider.signUp(signUpRequest);

        log.log("signUp : finished\n");
        return null;
    }
}
