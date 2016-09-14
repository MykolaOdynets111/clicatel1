package com.clickatell.models.mc2.accounts.request.billing_info;

import com.clickatell.utils.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by oshchur on 08.08.2016.
 */
public class AddressInfoRequest {
    private String address1;
    private String address2;
    private String city;
    private String province;
    private String postalCode;
    private Integer countryId;

    /**
     * No args constructor for use in serialization
     */
    public AddressInfoRequest() {
        this("Address1_" + StringUtils.generateRandomString(6),
                "Address1_" + StringUtils.generateRandomString(6),
                "City_" + StringUtils.generateRandomString(6),
                "Province_" + StringUtils.generateRandomString(6),
                "00000",
                null);
    }

    public AddressInfoRequest(Integer countryId) {
        this();
        this.countryId = countryId;
    }

    /**
     * @param countryId
     * @param postalCode
     * @param province
     * @param address1
     * @param address2
     * @param city
     */
    public AddressInfoRequest(String address1, String address2, String city, String province, String postalCode, Integer countryId) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.countryId = countryId;
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

    public AddressInfoRequest withAddress1(String address1) {
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

    public AddressInfoRequest withAddress2(String address2) {
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

    public AddressInfoRequest withCity(String city) {
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

    public AddressInfoRequest withProvince(String province) {
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

    public AddressInfoRequest withPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    /**
     * @return The countryId
     */
    public Integer getCountryId() {
        return countryId;
    }

    /**
     * @param countryId The countryId
     */
    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public AddressInfoRequest withCountryId(Integer countryId) {
        this.countryId = countryId;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(address1).append(address2).append(city).append(province).append(postalCode).append(countryId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AddressInfoRequest) == false) {
            return false;
        }
        AddressInfoRequest rhs = ((AddressInfoRequest) other);
        return new EqualsBuilder().append(address1, rhs.address1).append(address2, rhs.address2).append(city, rhs.city).append(province, rhs.province).append(postalCode, rhs.postalCode).append(countryId, rhs.countryId).isEquals();
    }

    @Override
    public String toString() {
        return "AddressInfoRequest{" +
                "address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", countryId=" + countryId +
                '}';
    }
}
