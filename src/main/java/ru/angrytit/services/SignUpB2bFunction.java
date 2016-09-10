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

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static ru.angrytit.model.UserAttributes.BUSINESS_NAME;
import static ru.angrytit.model.UserAttributes.EMAIL_ATTR;
import static ru.angrytit.model.UserAttributes.NAME_ATTR;
import static ru.angrytit.model.UserAttributes.TITLE_ATTR;
import static ru.angrytit.model.UserAttributes.TYPE;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class SignUpB2bFunction implements HandleService {
    private final Logger log = LoggerFactory.getLogger(getClass());

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
        String type = request.getType();

        log.info("SignUp with username : {}, email : {}, name : {}, title : {} and business name : {}, type : {}",
                userName, email, name, title, businessName, type);

        List<AttributeType> attributeTypes = new ArrayList<>();
        attributeTypes.add(new AttributeType().withName(EMAIL_ATTR.getValue()).withValue(email));

        if (!isEmpty(name)) {
            attributeTypes.add(new AttributeType().withName(NAME_ATTR.getValue()).withValue(name));
        }
        if (!isEmpty(title)) {
            attributeTypes.add(new AttributeType().withName(TITLE_ATTR.getValue()).withValue(title));
        }
        if (!isEmpty(businessName)) {
            attributeTypes.add(new AttributeType().withName(BUSINESS_NAME.getValue()).withValue(businessName));
        }
        if (!isEmpty(type)){
            attributeTypes.add(new AttributeType().withName(TYPE.getValue()).withValue(type));
        }

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
