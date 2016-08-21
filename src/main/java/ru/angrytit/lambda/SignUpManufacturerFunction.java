package ru.angrytit.lambda;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final String TITLE_ATTR = "custom:title";
    private static final String BUSINESS_NAME = "custom:business_name";

    @Override
    public Void handleRequest(SignUpRequest request, Context context) {
        AWSCognitoIdentityProvider provider =
                AWSCognitoIdentityProviderClientBuilder.
                        standard().
                        withRegion(REGION.getValue()).
                        build();
        LambdaLogger lambdaLogger = context.getLogger();
        Logger log = LoggerFactory.getLogger(getClass());

        lambdaLogger.log("signUp : started\n");
        String userName = request.getUserName();
        String password = request.getPassword();
        String email = request.getEmail();
        String title = request.getTitle();
        String name = request.getName();
        String businessName = request.getBusinessName();
        log.info("SignUp with username : {}, email : {}, name : {}, title : {} and business name : {}",
                userName, email, name, title, businessName);

        List<AttributeType> attributeTypes = new ArrayList<>();
        attributeTypes.add(new AttributeType().withName(EMAIL_ATTR).withValue(email));
        attributeTypes.add(new AttributeType().withName(NAME_ATTR).withValue(name));
        attributeTypes.add(new AttributeType().withName(TITLE_ATTR).withValue(title));
        attributeTypes.add(new AttributeType().withName(BUSINESS_NAME).withValue(businessName));

        com.amazonaws.services.cognitoidp.model.SignUpRequest signUpRequest =
                new com.amazonaws.services.cognitoidp.model.SignUpRequest().
                        withClientId(APP_CLIENT_ID.getValue()).
                        withUsername(userName).
                        withPassword(password).
                        withUserAttributes(attributeTypes);


        provider.signUp(signUpRequest);
        log.info("SignUp with username : {} was successfully", userName);

        lambdaLogger.log("signUp : finished\n");
        return null;
    }
}
