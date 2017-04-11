package com.touch.models.touch.chats;

/**
 * Created by kmakohoniuk on 4/11/2017.
 */
public class ChatPrivateHistoryRequest {


    private String messageText = null;
    private String to = null;

    public ChatPrivateHistoryRequest() {
    }

    public ChatPrivateHistoryRequest(String messageText, String to) {
        this.messageText = messageText;
        this.to = to;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatPrivateHistoryRequest that = (ChatPrivateHistoryRequest) o;

        if (messageText != null ? !messageText.equals(that.messageText) : that.messageText != null) return false;
        return to != null ? to.equals(that.to) : that.to == null;

    }

    @Override
    public int hashCode() {
        int result = messageText != null ? messageText.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatPrivateHistoryRequest{" +
                "messageText='" + messageText + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
