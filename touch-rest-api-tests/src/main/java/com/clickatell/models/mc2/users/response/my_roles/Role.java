
package com.clickatell.models.mc2.users.response.my_roles;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Role {

    private String id;
    private String name;
    private String description;
    private Boolean removable;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Role() {
    }

    /**
     * 
     * @param id
     * @param description
     * @param removable
     * @param name
     */
    public Role(String id, String name, String description, Boolean removable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.removable = removable;
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

    public Role withId(String id) {
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

    public Role withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Role withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * 
     * @return
     *     The removable
     */
    public Boolean getRemovable() {
        return removable;
    }

    /**
     * 
     * @param removable
     *     The removable
     */
    public void setRemovable(Boolean removable) {
        this.removable = removable;
    }

    public Role withRemovable(Boolean removable) {
        this.removable = removable;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
