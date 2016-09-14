package com.clickatell.models.mc2.accounts;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by sbryt on 6/10/2016.
 */
public class Account {

    private String id;
    private String name;
    private Boolean active;

    /**
     * No args constructor for use in serialization
     */
    public Account() {
    }

    /**
     * @param id
     * @param name
     */
    public Account(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Account(String id, String name, Boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
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

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Account)) {
            return false;
        }
        Account rhs = ((Account) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).isEquals();
    }

}
