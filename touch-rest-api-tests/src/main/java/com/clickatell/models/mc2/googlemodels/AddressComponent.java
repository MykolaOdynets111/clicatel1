
package com.clickatell.models.mc2.googlemodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class AddressComponent {

    @JsonProperty("long_name")
    private String longName;
    @JsonProperty("short_name")
    private String shortName;
    private List<String> types = new ArrayList<String>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddressComponent() {
    }

    /**
     * 
     * @param longName
     * @param types
     * @param shortName
     */
    public AddressComponent(String longName, String shortName, List<String> types) {
        this.longName = longName;
        this.shortName = shortName;
        this.types = types;
    }

    /**
     * 
     * @return
     *     The longName
     */
    public String getLongName() {
        return longName;
    }

    /**
     * 
     * @param longName
     *     The long_name
     */
    public void setLongName(String longName) {
        this.longName = longName;
    }

    public AddressComponent withLongName(String longName) {
        this.longName = longName;
        return this;
    }

    /**
     * 
     * @return
     *     The shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * @param shortName
     *     The short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public AddressComponent withShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    /**
     * 
     * @return
     *     The types
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     * 
     * @param types
     *     The types
     */
    public void setTypes(List<String> types) {
        this.types = types;
    }

    public AddressComponent withTypes(List<String> types) {
        this.types = types;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(longName).append(shortName).append(types).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AddressComponent) == false) {
            return false;
        }
        AddressComponent rhs = ((AddressComponent) other);
        return new EqualsBuilder().append(longName, rhs.longName).append(shortName, rhs.shortName).append(types, rhs.types).isEquals();
    }

}
