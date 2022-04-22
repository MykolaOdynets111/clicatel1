package com.touch.models;


public class Message {
    protected String message = "";


    public Message(String message) {
        this.message = message;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "MessageResponse{" +
                "message='" + message + "\'}";
    }
}
