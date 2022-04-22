package com.touch.models.touch.chats;

/**
 * Created by kmakohoniuk on 4/11/2017.
 */
public class ChatPrivateHistoryResponse {

    private Long messageTime = null;
    private String messageText = null;
    private String from = null;
    private String to = null;

    public Long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Long messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatPrivateHistoryResponse that = (ChatPrivateHistoryResponse) o;

        if (messageTime != null ? !messageTime.equals(that.messageTime) : that.messageTime != null) return false;
        if (messageText != null ? !messageText.equals(that.messageText) : that.messageText != null) return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        return to != null ? to.equals(that.to) : that.to == null;

    }

    @Override
    public int hashCode() {
        int result = messageTime != null ? messageTime.hashCode() : 0;
        result = 31 * result + (messageText != null ? messageText.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatPrivateHistoryResponse{" +
                "messageTime=" + messageTime +
                ", messageText='" + messageText + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
