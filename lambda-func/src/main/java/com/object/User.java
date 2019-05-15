package com.object;

public class User {
    private String userId;
    private String docId;
    private String qaText;

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

    public String getQaText() {
        return qaText;
    }

    public void setQaText(String qaText) {
        this.qaText = qaText;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", docId='" + docId + '\'' +
                ", qaText='" + qaText + '\'' +
                '}';
    }
}
