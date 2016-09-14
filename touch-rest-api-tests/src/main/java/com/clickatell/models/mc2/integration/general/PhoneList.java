
package com.clickatell.models.mc2.integration.general;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class PhoneList {

    private String id;
    private String number;
    private Boolean activated;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PhoneList() {
    }

    /**
     * 
     * @param id
     * @param activated
     * @param number
     */
    public PhoneList(String id, String number, Boolean activated) {
        this.id = id;
        this.number = number;
        this.activated = activated;
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

    /**
     * 
     * @return
     *     The activated
     */
    public Boolean getActivated() {
        return activated;
    }

    /**
     * 
     * @param activated
     *     The activated
     */
    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(number).append(activated).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PhoneList)) {
            return false;
        }
        PhoneList rhs = ((PhoneList) other);
        return new EqualsBuilder().append(id, rhs.id).append(number, rhs.number).append(activated, rhs.activated).isEquals();
    }

}
