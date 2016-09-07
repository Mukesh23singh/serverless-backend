package ru.angrytit.lambda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class LambdaExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public void wrapException(Exception e) {
        String errorType = "UnhandledError";
        int httpStatus = 500;
        String text = "An unknown error has occurred";

        if (e instanceof com.amazonaws.services.cognitoidp.model.ResourceNotFoundException) {
            errorType = "ResourceNotFoundError";
            httpStatus = 404;
            text = "Resource not found";
        } else if (e instanceof com.amazonaws.services.cognitoidp.model.InvalidParameterException) {
            errorType = "InvalidParameterError";
            httpStatus = 400;
            text = "Invalid parameter";
        } else if (e instanceof com.amazonaws.services.cognitoidp.model.NotAuthorizedException) {
            errorType = "NotAuthorizedError";
            httpStatus = 401;
            text = "Authentication/Authorization error";
        } else if (e instanceof com.amazonaws.services.cognitoidp.model.TooManyRequestsException) {
            errorType = "TooManyRequestsError";
            httpStatus = 429;
            text = "Throttling error";
        } else if (e instanceof com.amazonaws.services.cognitoidp.model.PasswordResetRequiredException) {
            errorType = "PasswordResetRequiredError";
            httpStatus = 401;
            text = "Authentication/Authorization error, reset password required";
        } else if (e instanceof com.amazonaws.services.cognitoidp.model.UserNotFoundException) {
            errorType = "UserNotFoundError";
            httpStatus = 404;
            text = "User not found";
        } else if (e instanceof com.amazonaws.services.cognitoidp.model.UserNotConfirmedException) {
            errorType = "UserNotConfirmedError";
            httpStatus = 403;
            text = "User not confirmed";
        } else if (e instanceof com.amazonaws.services.cognitoidp.model.InvalidPasswordException) {
            errorType = "InvalidPasswordError";
            httpStatus = 400;
            text = "Invalid password";
        } else if (e instanceof com.amazonaws.services.cognitoidp.model.CodeMismatchException) {
            errorType = "CodeMismatchError";
            httpStatus = 400;
            text = "Code mismatch";
        } else if (e instanceof com.amazonaws.services.cognitoidp.model.ExpiredCodeException) {
            errorType = "ExpiredCodeError";
            httpStatus = 400;
            text = "Code expired";
        } else if (e instanceof com.amazonaws.services.cognitoidp.model.TooManyFailedAttemptsException) {
            errorType = "TooManyFailedAttemptsError";
            httpStatus = 400;
            text = "Too many failed attempts";
        } else if (e instanceof com.amazonaws.services.cognitoidp.model.UsernameExistsException) {
            errorType = "UsernameExistsError";
            httpStatus = 400;
            text = "Email already exist";
        } else if (e instanceof com.amazonaws.services.cognitoidp.model.AliasExistsException) {
            errorType = "AliasExistsException";
            httpStatus = 400;
            text = "Email already exist";
        }

        Map<String, Object> errorPayload = new HashMap();
        errorPayload.put("errorType", errorType);
        errorPayload.put("httpStatus", httpStatus);
        errorPayload.put("message", text);

        String message = "";
        try {
            message = new ObjectMapper().writeValueAsString(errorPayload);
        } catch (JsonProcessingException e1) {
            log.error("Error during json serialization", e1);
        }
        throw new RuntimeException(message);
    }
}
