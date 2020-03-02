package $package;

import be.cloudway.gramba.runtime.GrambaRuntime;
import be.cloudway.gramba.runtime.aws.runtime.implementation.ContextImpl;
import be.cloudway.gramba.runtime.dev.addons.helpers.TestingRunner;
import be.cloudway.gramba.runtime.handler.GrambaLambdaHandler;
import be.cloudway.gramba.runtime.helpers.JacksonHelper;
import be.cloudway.gramba.runtime.model.ApiResponse;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.List;
import java.util.Map;

public class GrambaHandler implements GrambaLambdaHandler<APIGatewayProxyResponseEvent, ContextImpl> {
    private static final Handler handler = new Handler();
    private static final GrambaRuntime gramba = new GrambaRuntime(new GrambaHandler());

    public APIGatewayProxyResponseEvent customHandler(ApiResponse body, Map<String, List<String>> headers, JacksonHelper jacksonHelper) {
        APIGatewayProxyRequestEvent awsApiRequest =
                jacksonHelper.toObject(body.getResponse(), APIGatewayProxyRequestEvent.class);

        return handler.handleRequest(awsApiRequest, getContext());
    }

    public ContextImpl getContext() {
        return new ContextImpl();
    }

    public static void main (String... args) {
        if (TestingRunner.doRunTests(new GrambaHandler(), args)) return;

        gramba.runEventRunner();
    }
}
