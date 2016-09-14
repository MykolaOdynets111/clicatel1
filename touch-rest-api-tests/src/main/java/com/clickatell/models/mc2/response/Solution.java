package com.clickatell.models.mc2.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by oshchur on 04.07.2016.
 */
public class Solution {
    private String type;
    private Integer usersLimit;
    private Integer usersAmount;

    /**
     * No args constructor for use in serialization
     *
     */
    public Solution() {
    }

    /**
     *
     * @param usersAmount
     * @param type
     * @param usersLimit
     */
    public Solution(String type, Integer usersLimit, Integer usersAmount) {
        this.type = type;
        this.usersLimit = usersLimit;
        this.usersAmount = usersAmount;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public Solution withType(String type) {
        this.type = type;
        return this;
    }

    /**
     *
     * @return
     * The usersLimit
     */
    public Integer getUsersLimit() {
        return usersLimit;
    }

    /**
     *
     * @param usersLimit
     * The usersLimit
     */
    public void setUsersLimit(Integer usersLimit) {
        this.usersLimit = usersLimit;
    }

    public Solution withUsersLimit(Integer usersLimit) {
        this.usersLimit = usersLimit;
        return this;
    }

    /**
     *
     * @return
     * The usersAmount
     */
    public Integer getUsersAmount() {
        return usersAmount;
    }

    /**
     *
     * @param usersAmount
     * The usersAmount
     */
    public void setUsersAmount(Integer usersAmount) {
        this.usersAmount = usersAmount;
    }

    public Solution withUsersAmount(Integer usersAmount) {
        this.usersAmount = usersAmount;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(type).append(usersLimit).append(usersAmount).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Solution) == false) {
            return false;
        }
        Solution rhs = ((Solution) other);
        return new EqualsBuilder().append(type, rhs.type).append(usersLimit, rhs.usersLimit).append(usersAmount, rhs.usersAmount).isEquals();
    }
}
