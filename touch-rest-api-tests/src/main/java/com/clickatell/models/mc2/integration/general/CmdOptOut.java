
package com.clickatell.models.mc2.integration.general;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class CmdOptOut {

    private String id;
    private String keyword;
    private String replyText;
    private String type;

    /**
     * No args constructor for use in serialization
     */
    public CmdOptOut() {
    }

    /**
     * @param id
     * @param replyText
     * @param keyword
     * @param type
     */
    public CmdOptOut(String id, String keyword, String replyText, String type) {
        this.id = id;
        this.keyword = keyword;
        this.replyText = replyText;
        this.type = type;
    }

   public CmdOptOut(String keyword){
       replyText = "You have been unsubscribed from the service and will not receive any more messages from this number. To re-subscribe, send SUBSCRIBE to the service number.";
       type = "OPT_OUT";
       this.keyword = keyword;
   }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public CmdOptOut withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword The keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public CmdOptOut withKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    /**
     * @return The replyText
     */
    public String getReplyText() {
        return replyText;
    }

    /**
     * @param replyText The replyText
     */
    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public CmdOptOut withReplyText(String replyText) {
        this.replyText = replyText;
        return this;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public CmdOptOut withType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
