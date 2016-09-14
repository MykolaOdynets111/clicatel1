package com.clickatell.models;

/**
 * Created by sbryt on 6/7/2016.
 */
public class MessageResponse {
    protected String message = "";
    protected int statusCode;
    protected String renewedAPIKey;

    public MessageResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public MessageResponse() {
    }

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "message='" + message + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
