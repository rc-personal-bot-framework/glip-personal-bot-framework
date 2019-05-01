package sheet;

import object.SheetRequest;
import util.ClientUtil;

import java.io.IOException;

//https://sheets.googleapis.com/v4/spreadsheets/1VrQaPSPHnblVNrc06dyXCExdvlls14Ero0qu51wdzYw/values/A1:B2
public class GoogleSheetService extends AbstractSheetService {

    private final String API_URL = "https://sheets.googleapis.com/v4/spreadsheets/%s/values/%s";
    private final String TMS_URL = "http://tms-xmup.us-east-1.elasticbeanstalk.com/restapi/v1.0/third_party/token/GOOGLE_DRIVE/%s/";
    private final String TEST_URL = "https://www.google.com/";
    private final String TMS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrSUFja0xiNlB0c1pnREhEIiwiZXhwIjoxNzQ4NTg4OTQzLCJpYXQiOjE1NTY2MDgxNDN9.dmPeVmQfivWUe_ue4I0f93LStP5zaPjbpc0C_JeOAEQ";

    public static void main(String... args) throws IOException {
        GoogleSheetService service = new GoogleSheetService();
        SheetRequest sr = new SheetRequest();
        sr.setTmsKey("lewei");
        sr.setDocId("1VrQaPSPHnblVNrc06dyXCExdvlls14Ero0qu51wdzYw");
        sr.setFrom("A1");
        sr.setTo("A1");
        String result = service.getContent(sr);
        System.out.println(result);

    }

    public String getContent(SheetRequest request) throws IOException {

        String tmsUrl = String.format(TMS_URL, request.getTmsKey());
        String googleToken = ClientUtil.getContent(tmsUrl, TMS_TOKEN);

        String range = request.getFrom() + ":" + request.getTo();
        String googleUrl = String.format(API_URL, request.getDocId(), range);
        return ClientUtil.getContent(googleUrl, googleToken);

    }
}
