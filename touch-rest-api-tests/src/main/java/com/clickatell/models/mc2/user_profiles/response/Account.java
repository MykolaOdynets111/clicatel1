
package com.clickatell.models.mc2.user_profiles.response;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Account {

    private String id;
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Account() {
    }

    /**
     * 
     * @param id
     * @param name
     */
    public Account(String id, String name) {
        this.id = id;
        this.name = name;
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

    public Account withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Account withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
