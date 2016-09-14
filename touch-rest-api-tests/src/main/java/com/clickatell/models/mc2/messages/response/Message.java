
package com.clickatell.models.mc2.messages.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Message {

    private String apiMessageId;
    private Boolean accepted;
    private String to;
    private Object error;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Message() {
    }

    /**
     * 
     * @param to
     * @param apiMessageId
     * @param error
     * @param accepted
     */
    public Message(String apiMessageId, Boolean accepted, String to, Object error) {
        this.apiMessageId = apiMessageId;
        this.accepted = accepted;
        this.to = to;
        this.error = error;
    }

    /**
     * 
     * @return
     *     The apiMessageId
     */
    public String getApiMessageId() {
        return apiMessageId;
    }

    /**
     * 
     * @param apiMessageId
     *     The apiMessageId
     */
    public void setApiMessageId(String apiMessageId) {
        this.apiMessageId = apiMessageId;
    }

    public Message withApiMessageId(String apiMessageId) {
        this.apiMessageId = apiMessageId;
        return this;
    }

    /**
     * 
     * @return
     *     The accepted
     */
    public Boolean getAccepted() {
        return accepted;
    }

    /**
     * 
     * @param accepted
     *     The accepted
     */
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Message withAccepted(Boolean accepted) {
        this.accepted = accepted;
        return this;
    }

    /**
     * 
     * @return
     *     The to
     */
    public String getTo() {
        return to;
    }

    /**
     * 
     * @param to
     *     The to
     */
    public void setTo(String to) {
        this.to = to;
    }

    public Message withTo(String to) {
        this.to = to;
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

    public Message withError(Object error) {
        this.error = error;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
