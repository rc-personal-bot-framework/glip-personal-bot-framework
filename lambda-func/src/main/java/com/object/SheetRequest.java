package com.object;

public class SheetRequest {
    private String userId;
    private String docId;
    private String tmsKey;
    private String from;
    private String to;

    public String getTmsKey() {
        return tmsKey;
    }

    public void setTmsKey(String tmsKey) {
        this.tmsKey = tmsKey;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
