package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.object.SheetRequest;
import com.sheet.GoogleSheetService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
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

        Map responseBody = null;
        try {
            responseBody = service.getContent(input);
        } catch (IOException | SQLException e) {
            LOG.error("", e);
        }
        LOG.info(String.format("response result : %s ", responseBody));

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
