package com.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.object.SheetResponse;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ClientUtil {

    private static final String TMS_URL = "http://tms-xmup.us-east-1.elasticbeanstalk.com/restapi/v1.0/third_party/token/GOOGLE_DRIVE/lewei/";
    private static final String TMS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrSUFja0xiNlB0c1pnREhEIiwiZXhwIjoxNzQ4NTg4OTQzLCJpYXQiOjE1NTY2MDgxNDN9.dmPeVmQfivWUe_ue4I0f93LStP5zaPjbpc0C_JeOAEQ";


    public static void main(String[] args) throws IOException {
        ClientUtil.getContentStr(TMS_URL, TMS_TOKEN);
    }

    public static Map getContentMap(String urlStr, String token) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getContent(urlStr, token), Map.class);
    }

    public static SheetResponse getContentSheetResponse(String urlStr, String token) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getContent(urlStr, token), SheetResponse.class);
    }

    public static String getContentStr(String urlStr, String token) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(getContent(urlStr, token), writer, StandardCharsets.UTF_8);
        return writer.toString();
    }

    public static InputStream getContent(String urlStr, String token) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Authorization", "Bearer " + token);
        return con.getInputStream();
    }

}
