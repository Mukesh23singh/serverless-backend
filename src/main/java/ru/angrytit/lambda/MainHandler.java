package ru.angrytit.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.angrytit.model.CommonRequest;
import ru.angrytit.services.HandleService;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class MainHandler extends AbstractHandler implements RequestHandler<CommonRequest, Object> {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final LambdaExceptionHandler lambdaExceptionHandler = new LambdaExceptionHandler();

    @Override
    public Object handleRequest(CommonRequest commonRequest, Context context) {
        try {
            String functionName = context.getFunctionName();
            HandleService service = (HandleService) getApplicationContext().getBean(functionName);
            return service.handle(commonRequest);
        } catch (Exception e) {
            log.error("Error during execution", e);
            lambdaExceptionHandler.wrapException(e);
        }
        return "Something really bad happened";
    }
}
