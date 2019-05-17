package com.sheet;

import com.object.SheetRequest;
import com.object.User;
import com.util.ClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mortbay.util.ajax.JSON;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//https://sheets.googleapis.com/v4/spreadsheets/1VrQaPSPHnblVNrc06dyXCExdvlls14Ero0qu51wdzYw/values/A1:B2
public class GoogleSheetService extends AbstractSheetService {
    private static final Logger LOG = Logger.getLogger(GoogleSheetService.class);

    private final String API_URL = "https://sheets.googleapis.com/v4/spreadsheets/%s/values/%s";
    private final String TMS_URL = "http://tms-xmup.us-east-1.elasticbeanstalk.com/restapi/v1.0/third_party/token/GOOGLE_DRIVE/%s/";
    private final String TEST_URL = "https://www.google.com/";
    private final String TMS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrSUFja0xiNlB0c1pnREhEIiwiZXhwIjoxNzQ4NTg4OTQzLCJpYXQiOjE1NTY2MDgxNDN9.dmPeVmQfivWUe_ue4I0f93LStP5zaPjbpc0C_JeOAEQ";

    public static void main(String... args) throws IOException {
        GoogleSheetService service = new GoogleSheetService();
        SheetRequest sr = new SheetRequest();

        sr.setDocId("1VrQaPSPHnblVNrc06dyXCExdvlls14Ero0qu51wdzYw");
        sr.setFrom("A1");
        sr.setTo("A1");


        String jsonStr = "{\"range\":\"'Class Data'!A1:B2\",\"majorDimension\":\"ROWS\",\"values\":[[\"question1\",\"Answer1\"],[\"question2\",\"Answer2\"]]}";
        Map response = (Map) JSON.parse(jsonStr);
        System.out.println(response);
        Object[] sheetValues = (Object[]) response.get("values");

        for (int i = 0; i < sheetValues.length; i++) {

            Object[] value = (Object[]) sheetValues[i];

            if ("question1".contains((String) value[0])) {
                System.out.println(value[0]);
                System.out.println(value[1]);
            }
        }

    }

    public Map getContent(SheetRequest request) throws IOException, SQLException {
        User u = MysqlCon.getUser(request.getUserId());
        if (u == null) {
            MysqlCon.saveUser(request);
            Map result = new HashMap();
            result.put("S", "Create use success");
            return result;
        }

        LOG.info("user : " + u);
        if (StringUtils.isNotEmpty(u.getQaText())) {
            LOG.info("read data from db");
            Map result = new HashMap();
            String text = u.getQaText();
            String[] lines = text.split("\n");
            List<List<String>> values = new ArrayList<>();
            for (int i = 0; i < lines.length; i++) {
                List<String> l = new ArrayList<>();
                String lStr = lines[i];
                String[] cells = lStr.split(" ");
                l.add(cells[0]);
                l.add(cells[1]);
                values.add(l);

            }
            result.put("values", values);
            return result;
        }
        LOG.info("read data from google sheet");

        String tmsUrl = String.format(TMS_URL, request.getUserId());
        String googleToken = ClientUtil.getContentStr(tmsUrl, TMS_TOKEN);

        String range = request.getFrom() + ":" + request.getTo();
        String googleUrl = String.format(API_URL, u.getDocId(), range);
        return ClientUtil.getContentMap(googleUrl, googleToken);

    }
}
