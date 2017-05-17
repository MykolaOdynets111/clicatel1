
package com.touch.models.touch.tenant;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.touch.utils.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "firstAddressLine",
    "secondAddressLine",
    "city",
    "stateOrProvince",
    "postalCode",
    "lat",
    "lng",
    "businessHours",
    "phones"
})
public class TenantAddress {

    @JsonProperty("firstAddressLine")
    private String firstAddressLine;
    @JsonProperty("secondAddressLine")
    private String secondAddressLine;
    @JsonProperty("city")
    private String city;
    @JsonProperty("stateOrProvince")
    private String stateOrProvince;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("lat")
    private Long lat;
    @JsonProperty("lng")
    private Long lng;
    @JsonProperty("businessHours")
    private List<BusinessHour> businessHours = null;
    @JsonProperty("phones")
    private List<Phone> phones = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TenantAddress() {
        this.firstAddressLine = StringUtils.generateRandomString(10);
        this.secondAddressLine = StringUtils.generateRandomString(10);
        this.city = StringUtils.generateRandomString(10);
        this.stateOrProvince = StringUtils.generateRandomString(10);
        this.postalCode = StringUtils.generateRandomString(10);
        this.lat = 0L;
        this.lng = 0L;
        this.businessHours = new ArrayList<>();
        this.businessHours.add(new BusinessHour());
        this.phones = new ArrayList<>();
        phones.add(new Phone());

    }

    /**
     * 
     * @param postalCode
     * @param secondAddressLine
     * @param businessHours
     * @param lng
     * @param stateOrProvince
     * @param firstAddressLine
     * @param lat
     * @param phones
     * @param city
     */
    public TenantAddress(String firstAddressLine, String secondAddressLine, String city, String stateOrProvince, String postalCode, Long lat, Long lng, List<BusinessHour> businessHours, List<Phone> phones) {
        super();
        this.firstAddressLine = firstAddressLine;
        this.secondAddressLine = secondAddressLine;
        this.city = city;
        this.stateOrProvince = stateOrProvince;
        this.postalCode = postalCode;
        this.lat = lat;
        this.lng = lng;
        this.businessHours = businessHours;
        this.phones = phones;
    }

    @JsonProperty("firstAddressLine")
    public String getFirstAddressLine() {
        return firstAddressLine;
    }

    @JsonProperty("firstAddressLine")
    public void setFirstAddressLine(String firstAddressLine) {
        this.firstAddressLine = firstAddressLine;
    }

    public TenantAddress withFirstAddressLine(String firstAddressLine) {
        this.firstAddressLine = firstAddressLine;
        return this;
    }

    @JsonProperty("secondAddressLine")
    public String getSecondAddressLine() {
        return secondAddressLine;
    }

    @JsonProperty("secondAddressLine")
    public void setSecondAddressLine(String secondAddressLine) {
        this.secondAddressLine = secondAddressLine;
    }

    public TenantAddress withSecondAddressLine(String secondAddressLine) {
        this.secondAddressLine = secondAddressLine;
        return this;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    public TenantAddress withCity(String city) {
        this.city = city;
        return this;
    }

    @JsonProperty("stateOrProvince")
    public String getStateOrProvince() {
        return stateOrProvince;
    }

    @JsonProperty("stateOrProvince")
    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public TenantAddress withStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
        return this;
    }

    @JsonProperty("postalCode")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public TenantAddress withPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    @JsonProperty("lat")
    public Long getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Long lat) {
        this.lat = lat;
    }

    public TenantAddress withLat(Long lat) {
        this.lat = lat;
        return this;
    }

    @JsonProperty("lng")
    public Long getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(Long lng) {
        this.lng = lng;
    }

    public TenantAddress withLng(Long lng) {
        this.lng = lng;
        return this;
    }

    @JsonProperty("businessHours")
    public List<BusinessHour> getBusinessHours() {
        return businessHours;
    }

    @JsonProperty("businessHours")
    public void setBusinessHours(List<BusinessHour> businessHours) {
        this.businessHours = businessHours;
    }

    public TenantAddress withBusinessHours(List<BusinessHour> businessHours) {
        this.businessHours = businessHours;
        return this;
    }

    @JsonProperty("phones")
    public List<Phone> getPhones() {
        return phones;
    }

    @JsonProperty("phones")
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public TenantAddress withPhones(List<Phone> phones) {
        this.phones = phones;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(firstAddressLine).append(secondAddressLine).append(city).append(stateOrProvince).append(postalCode).append(lat).append(lng).append(businessHours).append(phones).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TenantAddress) == false) {
            return false;
        }
        TenantAddress rhs = ((TenantAddress) other);
        return new EqualsBuilder().append(firstAddressLine, rhs.firstAddressLine).append(secondAddressLine, rhs.secondAddressLine).append(city, rhs.city).append(stateOrProvince, rhs.stateOrProvince).append(postalCode, rhs.postalCode).append(lat, rhs.lat).append(lng, rhs.lng).append(businessHours, rhs.businessHours).append(phones, rhs.phones).isEquals();
    }

}
