exports.handler = function(event, context) {

    // Identify why was this function invoked
    if(event.triggerSource === "CustomMessage_SignUp"){
        // This Lambda function was invoked because a new user signed up to the user-pool
        // Ensure that your message contains event.request.codeParameter, this is the place holder for code that will be sent.
        // event.response.smsMessage = "Welcome to the service. Your confirmation code is " + event.request.codeParameter;
        event.response.emailSubject = "Welcome to the service";
        event.response.emailMessage = "Thank you for signing up.\n" +
            "To complete your registration flow plz follow the link\n" +
            "https://59sc9dnn96.execute-api.us-east-1.amazonaws.com/stage/manufacturer/confirm?id=" + event.userName+ "&code=" + event.request.codeParameter;
    }
    // Return result to Cognito
    context.done(null, event);
};