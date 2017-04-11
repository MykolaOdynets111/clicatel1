package com.touch.models.touch.chats;

import java.util.List;

/**
 * Created by kmakohoniuk on 4/11/2017.
 */
public class ChatPrivateHistoryList {

    private List<ChatPrivateHistoryResponse> records = null;

    public List<ChatPrivateHistoryResponse> getRecords() {
        return records;
    }

    public void setRecords(List<ChatPrivateHistoryResponse> records) {
        this.records = records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatPrivateHistoryList that = (ChatPrivateHistoryList) o;

        return records != null ? records.equals(that.records) : that.records == null;

    }

    @Override
    public int hashCode() {
        return records != null ? records.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ChatPrivateHistoryList{" +
                "records=" + records +
                '}';
    }
}
