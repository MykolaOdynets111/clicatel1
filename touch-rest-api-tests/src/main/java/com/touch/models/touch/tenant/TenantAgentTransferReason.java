
package com.touch.models.touch.tenant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "transferReason"
})
public class TenantAgentTransferReason {

    @JsonProperty("transferReason")
    private String transferReason;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TenantAgentTransferReason() {
        this.transferReason = "test";
    }

    /**
     * 
     * @param transferReason
     */
    public TenantAgentTransferReason(String transferReason) {
        super();
        this.transferReason = transferReason;
    }

    @JsonProperty("transferReason")
    public String getTransferReason() {
        return transferReason;
    }

    @JsonProperty("transferReason")
    public void setTransferReason(String transferReason) {
        this.transferReason = transferReason;
    }

    public TenantAgentTransferReason withTransferReason(String transferReason) {
        this.transferReason = transferReason;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(transferReason).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TenantAgentTransferReason) == false) {
            return false;
        }
        TenantAgentTransferReason rhs = ((TenantAgentTransferReason) other);
        return new EqualsBuilder().append(transferReason, rhs.transferReason).isEquals();
    }

}
