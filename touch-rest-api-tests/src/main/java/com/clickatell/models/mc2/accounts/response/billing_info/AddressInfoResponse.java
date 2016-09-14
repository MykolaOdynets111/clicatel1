package com.clickatell.models.mc2.accounts.response.billing_info;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by oshchur on 08.08.2016.
 */
public class AddressInfoResponse {
    private String address1;
    private String address2;
    private String city;
    private String province;
    private String postalCode;
    private Country country;

    /**
     * No args constructor for use in serialization
     */
    public AddressInfoResponse() {
    }

    /**
     * @param country
     * @param postalCode
     * @param province
     * @param address1
     * @param address2
     * @param city
     */
    public AddressInfoResponse(String address1, String address2, String city, String province, String postalCode, Country country) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
    }

    /**
     * @return The address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * @param address1 The address1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public AddressInfoResponse withAddress1(String address1) {
        this.address1 = address1;
        return this;
    }

    /**
     * @return The address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @param address2 The address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public AddressInfoResponse withAddress2(String address2) {
        this.address2 = address2;
        return this;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    public AddressInfoResponse withCity(String city) {
        this.city = city;
        return this;
    }

    /**
     * @return The province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province The province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    public AddressInfoResponse withProvince(String province) {
        this.province = province;
        return this;
    }

    /**
     * @return The postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode The postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public AddressInfoResponse withPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    /**
     * @return The countryId
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @param country The Country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    public AddressInfoResponse withCountry(Country country) {
        this.country = country;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(address1).append(address2).append(city).append(province).append(postalCode).append(country).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AddressInfoResponse) == false) {
            return false;
        }
        AddressInfoResponse rhs = ((AddressInfoResponse) other);
        return new EqualsBuilder().append(address1, rhs.address1).append(address2, rhs.address2).append(city, rhs.city).append(province, rhs.province).append(postalCode, rhs.postalCode).append(country, rhs.country).isEquals();
    }
}
