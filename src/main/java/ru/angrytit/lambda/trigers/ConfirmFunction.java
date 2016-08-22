package ru.angrytit.lambda.trigers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.CognitoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class ConfirmFunction implements RequestHandler<CognitoEvent, String> {
    @Override
    public String handleRequest(CognitoEvent cognitoEvent, Context context) {
        LambdaLogger lambdaLogger = context.getLogger();
        Logger log = LoggerFactory.getLogger(getClass());
        lambdaLogger.log("confirm trigger : started\n");

        log.info(cognitoEvent.getEventType());
        log.info(cognitoEvent.getIdentityId());
        cognitoEvent.getDatasetRecords().entrySet().stream().forEach(entry ->
                log.info("key : {}, value : {}", entry.getKey(), entry.getValue())
        );
        lambdaLogger.log("confirm trigger : finished\n");
        return "Hello Man";
    }
}
