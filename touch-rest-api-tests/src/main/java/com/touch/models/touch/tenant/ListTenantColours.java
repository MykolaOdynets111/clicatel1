package com.touch.models.touch.tenant;

/**
 * Created by kmakohoniuk on 9/6/2016.
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "tenantColours"
})
public class ListTenantColours {

    @JsonProperty("tenantColours")
    private List<TenantColour> tenantColours = new ArrayList<TenantColour>();

    /**
     * No args constructor for use in serialization
     *
     */
    public ListTenantColours() {
    }

    /**
     *
     * @param tenantColours
     */
    public ListTenantColours(List<TenantColour> tenantColours) {
        this.tenantColours = tenantColours;
    }

    /**
     *
     * @return
     * The tenantColours
     */
    @JsonProperty("tenantColours")
    public List<TenantColour> getTenantColours() {
        return tenantColours;
    }

    /**
     *
     * @param tenantColours
     * The tenantColours
     */
    @JsonProperty("tenantColours")
    public void setTenantColours(List<TenantColour> tenantColours) {
        this.tenantColours = tenantColours;
    }

    public ListTenantColours withTenantColours(List<TenantColour> tenantColours) {
        this.tenantColours = tenantColours;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(tenantColours).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ListTenantColours) == false) {
            return false;
        }
        ListTenantColours rhs = ((ListTenantColours) other);
        return new EqualsBuilder().append(tenantColours, rhs.tenantColours).isEquals();
    }

}