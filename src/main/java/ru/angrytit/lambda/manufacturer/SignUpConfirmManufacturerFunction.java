package ru.angrytit.lambda.manufacturer;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.angrytit.lambda.model.ConfirmRequest;

import static ru.angrytit.lambda.Config.APP_CLIENT_ID;
import static ru.angrytit.lambda.Config.REGION;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class SignUpConfirmManufacturerFunction implements RequestHandler<ConfirmRequest, Void> {

    @Override
    public Void handleRequest(ConfirmRequest confirmRequest, Context context) {
        return confirm(confirmRequest, context);
    }

    private Void confirm(ConfirmRequest confirmRequest, Context context) {
        AWSCognitoIdentityProvider provider =
                AWSCognitoIdentityProviderClientBuilder.
                        standard().
                        withRegion(REGION.getValue()).
                        build();
        LambdaLogger lambdaLogger = context.getLogger();
        Logger log = LoggerFactory.getLogger(getClass());

        lambdaLogger.log("confirm : started\n");

        String confirmCode = confirmRequest.getCode();
        String userName = confirmRequest.getId();

        log.info("Confirm signUp for user name : {} with code : {}", userName, confirmCode);
        ConfirmSignUpRequest confirmSignUpRequest = new ConfirmSignUpRequest().
                withClientId(APP_CLIENT_ID.getValue()).
                withConfirmationCode(confirmCode).
                withUsername(userName);
        provider.confirmSignUp(confirmSignUpRequest);
        log.info("Confirm signUp for user name : {} was successfully", userName);

        lambdaLogger.log("confirm : finished\n");
        return null;
    }
}
