
package com.clickatell.models.mc2.two_way_long.response.subscription_plans;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class SetupCost {

    private Integer id;
    private String price;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SetupCost() {
    }

    /**
     * 
     * @param id
     * @param price
     */
    public SetupCost(Integer id, String price) {
        this.id = id;
        this.price = price;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public SetupCost withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    public SetupCost withPrice(String price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(price).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SetupCost) == false) {
            return false;
        }
        SetupCost rhs = ((SetupCost) other);
        return new EqualsBuilder().append(id, rhs.id).append(price, rhs.price).isEquals();
    }

}
