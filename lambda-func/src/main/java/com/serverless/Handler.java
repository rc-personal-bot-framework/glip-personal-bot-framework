package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import object.SheetRequest;
import org.apache.log4j.Logger;
import sheet.GoogleSheetService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Handler implements RequestHandler<SheetRequest, ApiGatewayResponse> {

    private static final Logger LOG = Logger.getLogger(Handler.class);

    private GoogleSheetService service;

    public Handler() {
        service = new GoogleSheetService();
    }

    @Override
    public ApiGatewayResponse handleRequest(SheetRequest input, Context context) {
        LOG.info("received: " + input);

        String responseBody = null;
        try {
            responseBody = service.getContent(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Powered-By", "AWS Lambda & Serverless");
        headers.put("Content-Type", "application/json");
        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(responseBody)
                .setHeaders(headers)
                .build();
    }
}
