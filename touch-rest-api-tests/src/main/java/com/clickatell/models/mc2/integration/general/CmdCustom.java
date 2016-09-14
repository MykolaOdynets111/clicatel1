
package com.clickatell.models.mc2.integration.general;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CmdCustom {

    private String id;
    private String keyword;
    private String replyText;
    private String type = "CUSTOM";

    /**
     * No args constructor for use in serialization
     */
    public CmdCustom() {
    }

    /**
     * @param id
     * @param replyText
     * @param keyword
     * @param type
     */
    public CmdCustom(String id, String keyword, String replyText, String type) {
        this.id = id;
        this.keyword = keyword;
        this.replyText = replyText;
        this.type = type;
    }

    public CmdCustom(String keyword, String replyText) {
        this.keyword = keyword;
        this.replyText = replyText;
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

    public CmdCustom withId(String id) {
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

    public CmdCustom withKeyword(String keyword) {
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

    public CmdCustom withReplyText(String replyText) {
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

    public CmdCustom withType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmdCustom cmdCustom = (CmdCustom) o;

        if (!getKeyword().equals(cmdCustom.getKeyword())) return false;
        if (!getReplyText().equals(cmdCustom.getReplyText())) return false;
        return getType().equals(cmdCustom.getType());

    }

    @Override
    public int hashCode() {
        int result = getKeyword().hashCode();
        result = 31 * result + getReplyText().hashCode();
        result = 31 * result + getType().hashCode();
        return result;
    }
}
