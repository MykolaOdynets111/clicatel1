
package com.clickatell.models.mc2.googlemodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Result {

    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents = new ArrayList<AddressComponent>();
    @JsonProperty("formatted_address")
    private String formattedAddress;
    private Geometry geometry;
    @JsonProperty("place_id")
    private String placeId;
    private List<String> types = new ArrayList<String>();
    @JsonProperty("postcode_localities")
    private List<String> postcodeLocalities = new ArrayList<String>();

    /**
     * No args constructor for use in serialization
     */
    public Result() {
    }

    /**
     * @param placeId
     * @param postcodeLocalities
     * @param formattedAddress
     * @param types
     * @param addressComponents
     * @param geometry
     */
    public Result(List<AddressComponent> addressComponents, String formattedAddress, Geometry geometry, String placeId, List<String> types, List<String> postcodeLocalities) {
        this.addressComponents = addressComponents;
        this.formattedAddress = formattedAddress;
        this.geometry = geometry;
        this.placeId = placeId;
        this.types = types;
        this.postcodeLocalities = postcodeLocalities;
    }

    /**
     * @return The addressComponents
     */
    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    /**
     * @param addressComponents The address_components
     */
    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public Result withAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
        return this;
    }

    /**
     * @return The formattedAddress
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     * @param formattedAddress The formatted_address
     */
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Result withFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
        return this;
    }

    /**
     * @return The geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * @param geometry The geometry
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Result withGeometry(Geometry geometry) {
        this.geometry = geometry;
        return this;
    }

    /**
     * @return The placeId
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     * @param placeId The place_id
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Result withPlaceId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    /**
     * @return The types
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     * @param types The types
     */
    public void setTypes(List<String> types) {
        this.types = types;
    }

    public Result withTypes(List<String> types) {
        this.types = types;
        return this;
    }

    /**
     * @return The postcodeLocalities
     */
    public List<String> getPostcodeLocalities() {
        return postcodeLocalities;
    }

    /**
     * @param postcodeLocalities The postcode_localities
     */
    public void setPostcodeLocalities(List<String> postcodeLocalities) {
        this.postcodeLocalities = postcodeLocalities;
    }

    public Result withPostcodeLocalities(List<String> postcodeLocalities) {
        this.postcodeLocalities = postcodeLocalities;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(addressComponents).append(formattedAddress).append(geometry).append(placeId).append(types).append(postcodeLocalities).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Result) == false) {
            return false;
        }
        Result rhs = ((Result) other);
        return new EqualsBuilder().append(addressComponents, rhs.addressComponents).append(formattedAddress, rhs.formattedAddress).append(geometry, rhs.geometry).append(placeId, rhs.placeId).append(types, rhs.types).append(postcodeLocalities, rhs.postcodeLocalities).isEquals();
    }

}
