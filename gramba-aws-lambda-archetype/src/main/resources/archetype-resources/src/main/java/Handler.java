package $package;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import ${package}.model.ResponseObject;

import static be.cloudway.gramba.runtime.GrambaRuntime.STATIC_REFERENCES;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResponse("The ID is: " + input.getPathParameters().get("id"));

        APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
        apiGatewayProxyResponseEvent.setStatusCode(200);
        apiGatewayProxyResponseEvent.setBody(STATIC_REFERENCES.jacksonHelper.fromObj(responseObject));

        return apiGatewayProxyResponseEvent;
    }
}
