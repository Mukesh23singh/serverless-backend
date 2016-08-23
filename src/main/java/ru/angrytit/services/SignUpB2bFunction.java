package ru.angrytit.services;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.angrytit.model.CommonRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class SignUpB2bFunction implements HandleService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String EMAIL_ATTR = "email";
    private static final String NAME_ATTR = "name";
    private static final String TITLE_ATTR = "custom:title";
    private static final String BUSINESS_NAME = "custom:business_name";

    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    private final String applicationClientId;

    public SignUpB2bFunction(AWSCognitoIdentityProvider awsCognitoIdentityProvider, String applicationClientId) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
        this.applicationClientId = applicationClientId;
    }

    @Override
    public String handle(CommonRequest request) {
        String userName = UUID.randomUUID().toString();

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

        SignUpRequest signUpRequest = new SignUpRequest().
                withClientId(applicationClientId).
                withUsername(userName).
                withPassword(password).
                withUserAttributes(attributeTypes);


        awsCognitoIdentityProvider.signUp(signUpRequest);
        log.info("SignUp with username : {} was successfully", userName);

        return "OK";
    }
}
