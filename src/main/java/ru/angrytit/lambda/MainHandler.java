package ru.angrytit.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import ru.angrytit.model.CommonRequest;
import ru.angrytit.services.HandleService;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class MainHandler extends AbstractHandler implements RequestHandler<CommonRequest, String> {
    @Override
    public String handleRequest(CommonRequest commonRequest, Context context) {
        String functionName = context.getFunctionName();
        HandleService service = (HandleService) getApplicationContext().getBean(functionName);
        return service.handle(commonRequest);
    }
}
