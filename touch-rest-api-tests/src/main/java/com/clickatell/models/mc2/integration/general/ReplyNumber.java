
package com.clickatell.models.mc2.integration.general;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ReplyNumber {

    private String id;
    private String number;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ReplyNumber() {
    }

    /**
     * 
     * @param id
     * @param number
     */
    public ReplyNumber(String id, String number) {
        this.id = id;
        this.number = number;
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

    public ReplyNumber withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The number
     */
    public String getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    public ReplyNumber withNumber(String number) {
        this.number = number;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
