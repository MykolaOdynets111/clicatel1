
package com.clickatell.models.mc2.googlemodels;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Bounds {

    private Northeast northeast;
    private Southwest southwest;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Bounds() {
    }

    /**
     * 
     * @param southwest
     * @param northeast
     */
    public Bounds(Northeast northeast, Southwest southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    /**
     * 
     * @return
     *     The northeast
     */
    public Northeast getNortheast() {
        return northeast;
    }

    /**
     * 
     * @param northeast
     *     The northeast
     */
    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    public Bounds withNortheast(Northeast northeast) {
        this.northeast = northeast;
        return this;
    }

    /**
     * 
     * @return
     *     The southwest
     */
    public Southwest getSouthwest() {
        return southwest;
    }

    /**
     * 
     * @param southwest
     *     The southwest
     */
    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }

    public Bounds withSouthwest(Southwest southwest) {
        this.southwest = southwest;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(northeast).append(southwest).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Bounds) == false) {
            return false;
        }
        Bounds rhs = ((Bounds) other);
        return new EqualsBuilder().append(northeast, rhs.northeast).append(southwest, rhs.southwest).isEquals();
    }

}
