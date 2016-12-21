package com.touch.models.touch.tenant;

/**
 * Created by kmakohoniuk on 9/6/2016.
 */
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class ListTenantProperties {

    @JsonProperty("tenantProperties")
    private List<TenantProperties> tenantProperties = new ArrayList<TenantProperties>();

    /**
     * No args constructor for use in serialization
     *
     */
    public ListTenantProperties() {
    }

    /**
     *
     * @param tenantProperties
     */
    public ListTenantProperties(List<TenantProperties> tenantProperties) {
        this.tenantProperties = tenantProperties;
    }

    /**
     *
     * @return
     * The tenantProperties
     */
    @JsonProperty("tenantProperties")
    public List<TenantProperties> getTenantProperties() {
        return tenantProperties;
    }

    /**
     *
     * @param tenantProperties
     * The tenantProperties
     */
    @JsonProperty("tenantProperties")
    public void setTenantProperties(List<TenantProperties> tenantProperties) {
        this.tenantProperties = tenantProperties;
    }

    public ListTenantProperties withTenantColours(List<TenantProperties> tenantProperties) {
        this.tenantProperties = tenantProperties;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(tenantProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ListTenantProperties) == false) {
            return false;
        }
        ListTenantProperties rhs = ((ListTenantProperties) other);
        return new EqualsBuilder().append(tenantProperties, rhs.tenantProperties).isEquals();
    }

}