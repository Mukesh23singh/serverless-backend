package ru.angrytit.services;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.UpdateUserAttributesRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.angrytit.model.CommonRequest;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static ru.angrytit.model.UserAttributes.CALL_US_HERE;
import static ru.angrytit.model.UserAttributes.COMPANY_STORY;
import static ru.angrytit.model.UserAttributes.EMAIL_US_HERE;
import static ru.angrytit.model.UserAttributes.FACEBOOK_PAGE;
import static ru.angrytit.model.UserAttributes.FOUNDER_INFO;
import static ru.angrytit.model.UserAttributes.INSTAGRAM_PAGE;
import static ru.angrytit.model.UserAttributes.STORE_ADDRESS;
import static ru.angrytit.model.UserAttributes.TWITTER_ADDRESS;
import static ru.angrytit.model.UserAttributes.WEBSITE_ADDRESS;
import static ru.angrytit.model.UserAttributes.WE_ARE;
import static ru.angrytit.model.UserAttributes.WE_ARE_UNIQUE;
import static ru.angrytit.model.UserAttributes.YOU_CAN_FIND_US_HERE;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class UpdateUserInfoB2bFunction implements HandleService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    public UpdateUserInfoB2bFunction(AWSCognitoIdentityProvider awsCognitoIdentityProvider) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
    }

    @Override
    public Object handle(CommonRequest request) {
        List<AttributeType> attributes = new ArrayList<>();
        String accessToke = request.getAccessToken();
        String weAre = request.getWeAre();
        String facebookPage = request.getFacebookPage();
        String twitterAddress = request.getTwitterAddress();
        String instagramPage = request.getInstagramPage();
        String youCanFindUsHere = request.getYouCanFindUsHere();
        String weAreUnique = request.getWeAreUnique();
        String companyStory = request.getCompanyStory();
        String founderInfo = request.getFounderInfo();
        String storeAddress = request.getStoreAddress();
        String websiteAddress = request.getWebsiteAddress();
        String callUsHere = request.getCallUsHere();
        String emailUsHere = request.getEmailUsHere();
        log.info("Update user attributes for user with access token : {}", accessToke);
        if (!isEmpty(weAre)) {
            attributes.add(new AttributeType().withName(WE_ARE.getValue()).withValue(weAre));
        }
        if (!isEmpty(facebookPage)) {
            attributes.add(new AttributeType().withName(FACEBOOK_PAGE.getValue()).withValue(facebookPage));
        }
        if (!isEmpty(twitterAddress)) {
            attributes.add(new AttributeType().withName(TWITTER_ADDRESS.getValue()).withValue(twitterAddress));
        }
        if (!isEmpty(instagramPage)) {
            attributes.add(new AttributeType().withName(INSTAGRAM_PAGE.getValue()).withValue(instagramPage));
        }
        if (!isEmpty(youCanFindUsHere)) {
            attributes.add(new AttributeType().withName(YOU_CAN_FIND_US_HERE.getValue()).withValue(youCanFindUsHere));
        }
        if (!isEmpty(weAreUnique)) {
            attributes.add(new AttributeType().withName(WE_ARE_UNIQUE.getValue()).withValue(weAreUnique));
        }
        if (!isEmpty(companyStory)) {
            attributes.add(new AttributeType().withName(COMPANY_STORY.getValue()).withValue(companyStory));
        }
        if (!isEmpty(founderInfo)) {
            attributes.add(new AttributeType().withName(FOUNDER_INFO.getValue()).withValue(founderInfo));
        }
        if (!isEmpty(storeAddress)) {
            attributes.add(new AttributeType().withName(STORE_ADDRESS.getValue()).withValue(storeAddress));
        }
        if (!isEmpty(websiteAddress)) {
            attributes.add(new AttributeType().withName(WEBSITE_ADDRESS.getValue()).withValue(websiteAddress));
        }
        if (!isEmpty(callUsHere)) {
            attributes.add(new AttributeType().withName(CALL_US_HERE.getValue()).withValue(callUsHere));
        }
        if (!isEmpty(emailUsHere)) {
            attributes.add(new AttributeType().withName(EMAIL_US_HERE.getValue()).withValue(emailUsHere));
        }
        UpdateUserAttributesRequest updateUserAttributesRequest = new UpdateUserAttributesRequest().
                withUserAttributes(attributes).
                withAccessToken(accessToke);
        awsCognitoIdentityProvider.updateUserAttributes(updateUserAttributesRequest);
        log.info("Update user attributes was successfully");
        return "OK";
    }
}
