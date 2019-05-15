package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.object.SheetRequest;
import com.sheet.GoogleSheetService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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

        Map sheetData = null;
        try {
            sheetData = service.getContent(input);

        } catch (IOException | SQLException e) {
            LOG.error("", e);
        }
        LOG.info(String.format("response result : %s ", sheetData));

        Map<String, Object> result = new HashMap();
        String keyword = input.getKeyword();
        LOG.info("important!");
        List sheetValues = (List) sheetData.get("values");
        if (sheetValues == null) {
            result = sheetData;
        } else {
            LOG.info("sheetValues : " + sheetValues);
            for (int i = 0; i < sheetValues.size(); i++) {

                List value = (List) sheetValues.get(i);
                LOG.info("value : " + value);

                if (keyword.contains((String) value.get(0))) {
                    result.put("Q", value.get(0));
                    try {
                        result.put("A", value.get(1));
                    } catch (IndexOutOfBoundsException e) {
                        result.put("E", "no answer");
                    }
                }
            }
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Powered-By", "AWS Lambda & Serverless");
        headers.put("Content-Type", "application/json");
        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(result)
                .setHeaders(headers)
                .build();
    }
}
