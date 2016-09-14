package com.clickatell.models.mc2.response.country_phones;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbryt on 8/23/2016.
 */
public class PhoneCodesResponse {
    private String name;
    private String iso2;
    private Integer phoneCode;
    private Integer priority = null;
    private List<Integer> ariaCodes = new ArrayList<Integer>();

    /**
     * No args constructor for use in serialization
     */
    public PhoneCodesResponse() {
    }

    /**
     * @param phoneCode
     * @param priority
     * @param name
     * @param iso2
     * @param ariaCodes
     */
    public PhoneCodesResponse(String name, String iso2, Integer phoneCode, Integer priority, List<Integer> ariaCodes) {
        this.name = name;
        this.iso2 = iso2;
        this.phoneCode = phoneCode;
        this.priority = priority;
        this.ariaCodes = ariaCodes;
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

    public PhoneCodesResponse withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The iso2
     */
    public String getIso2() {
        return iso2;
    }

    /**
     * @param iso2 The iso2
     */
    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public PhoneCodesResponse withIso2(String iso2) {
        this.iso2 = iso2;
        return this;
    }

    /**
     * @return The phoneCode
     */
    public Integer getPhoneCode() {
        return phoneCode;
    }

    /**
     * @param phoneCode The phone_code
     */
    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
    }

    public PhoneCodesResponse withPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
        return this;
    }

    /**
     * @return The priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority The priority
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public PhoneCodesResponse withPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    /**
     * @return The ariaCodes
     */
    public List<Integer> getAriaCodes() {
        return ariaCodes;
    }

    /**
     * @param ariaCodes The aria_codes
     */
    public void setAriaCodes(List<Integer> ariaCodes) {
        this.ariaCodes = ariaCodes;
    }

    public PhoneCodesResponse withAriaCodes(List<Integer> ariaCodes) {
        this.ariaCodes = ariaCodes;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(iso2).append(phoneCode).append(priority).append(ariaCodes).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PhoneCodesResponse) == false) {
            return false;
        }
        PhoneCodesResponse rhs = ((PhoneCodesResponse) other);
        return new EqualsBuilder().append(name, rhs.name).append(iso2, rhs.iso2).append(phoneCode, rhs.phoneCode).append(priority, rhs.priority).append(ariaCodes, rhs.ariaCodes).isEquals();
    }

}
