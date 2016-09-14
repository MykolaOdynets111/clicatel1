
package com.clickatell.models.mc2.messages.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class SendMessageResponse {

    private List<Message> messages = new ArrayList<Message>();
    private Object error;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SendMessageResponse() {
    }

    /**
     * 
     * @param error
     * @param messages
     */
    public SendMessageResponse(List<Message> messages, Object error) {
        this.messages = messages;
        this.error = error;
    }

    /**
     * 
     * @return
     *     The messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * 
     * @param messages
     *     The messages
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public SendMessageResponse withMessages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

    /**
     * 
     * @return
     *     The error
     */
    public Object getError() {
        return error;
    }

    /**
     * 
     * @param error
     *     The error
     */
    public void setError(Object error) {
        this.error = error;
    }

    public SendMessageResponse withError(Object error) {
        this.error = error;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
