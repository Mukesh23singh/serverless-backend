exports.handler = function(event, context) {

    // Identify why was this function invoked
    if(event.triggerSource === "CustomMessage_SignUp"){
        // This Lambda function was invoked because a new user signed up to the user-pool
        // Ensure that your message contains event.request.codeParameter, this is the place holder for code that will be sent.
        // event.response.smsMessage = "Welcome to the service. Your confirmation code is " + event.request.codeParameter;
        event.response.emailSubject = "Welcome to the service";
        event.response.emailMessage = "Thank you for signing up. To complete your registration flow plz follow the link http://google.com/" + event.request.codeParameter;
    }
    // Return result to Cognito
    context.done(null, event);
};