package com.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
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

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Authorization", "Bearer " + token);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(con.getInputStream(), Map.class);
    }

    public static String getContentStr(String urlStr, String token) throws IOException {

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Authorization", "Bearer " + token);

        StringWriter writer = new StringWriter();
        IOUtils.copy(con.getInputStream(), writer, StandardCharsets.UTF_8);
        return writer.toString();
    }

}
