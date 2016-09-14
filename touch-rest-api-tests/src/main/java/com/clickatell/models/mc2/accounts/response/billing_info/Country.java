package com.clickatell.models.mc2.accounts.response.billing_info;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by oshchur on 08.08.2016.
 */
public class Country {
    private Integer id;
    private String name;
    private String isoCode3;
    private String isoCode2;

    /**
     * No args constructor for use in serialization
     */
    public Country() {
    }

    /**
     * @param id
     * @param isoCode2
     * @param isoCode3
     * @param name
     */
    public Country(Integer id, String name, String isoCode3, String isoCode2) {
        this.id = id;
        this.name = name;
        this.isoCode3 = isoCode3;
        this.isoCode2 = isoCode2;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Country withId(Integer id) {
        this.id = id;
        return this;
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

    public Country withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The isoCode3
     */
    public String getIsoCode3() {
        return isoCode3;
    }

    /**
     * @param isoCode3 The isoCode3
     */
    public void setIsoCode3(String isoCode3) {
        this.isoCode3 = isoCode3;
    }

    public Country withIsoCode3(String isoCode3) {
        this.isoCode3 = isoCode3;
        return this;
    }

    /**
     * @return The isoCode2
     */
    public String getIsoCode2() {
        return isoCode2;
    }

    /**
     * @param isoCode2 The isoCode2
     */
    public void setIsoCode2(String isoCode2) {
        this.isoCode2 = isoCode2;
    }

    public Country withIsoCode2(String isoCode2) {
        this.isoCode2 = isoCode2;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(isoCode3).append(isoCode2).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Country) == false) {
            return false;
        }
        Country rhs = ((Country) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(isoCode3, rhs.isoCode3).append(isoCode2, rhs.isoCode2).isEquals();
    }
}
