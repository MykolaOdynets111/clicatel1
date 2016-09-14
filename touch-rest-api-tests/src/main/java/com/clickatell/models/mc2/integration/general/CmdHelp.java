
package com.clickatell.models.mc2.integration.general;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CmdHelp {

    private String id;
    private String keyword = "HELP";
    private String replyText = "Send STOP, END, CANCEL, UNSUBSCRIBE or QUIT to opt-out from this service. If you opt-out, you will not receive any more messages from this number.";
    private String type = "HELP";

    /**
     * No args constructor for use in serialization
     * 
     */
    public CmdHelp() {
    }

    /**
     * 
     * @param id
     * @param replyText
     * @param keyword
     * @param type
     */
    public CmdHelp(String id, String keyword, String replyText, String type) {
        this.id = id;
        this.keyword = keyword;
        this.replyText = replyText;
        this.type = type;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public CmdHelp withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * 
     * @param keyword
     *     The keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public CmdHelp withKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    /**
     * 
     * @return
     *     The replyText
     */
    public String getReplyText() {
        return replyText;
    }

    /**
     * 
     * @param replyText
     *     The replyText
     */
    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public CmdHelp withReplyText(String replyText) {
        this.replyText = replyText;
        return this;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public CmdHelp withType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
