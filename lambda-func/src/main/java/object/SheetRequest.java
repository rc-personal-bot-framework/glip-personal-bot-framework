package object;

public class SheetRequest {
    private String tmsKey;
    private String docId;
    private String from;
    private String to;

    public String getTmsKey() {
        return tmsKey;
    }

    public void setTmsKey(String tmsKey) {
        this.tmsKey = tmsKey;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
