package com.clickatell.models.mc2.payment_methods.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by oshchur on 26.08.2016.
 */
public class ReferenceKeyRequest {
    private String referenceKey;

    /**
     * No args constructor for use in serialization
     */
    public ReferenceKeyRequest() {
    }

    /**
     * @param referenceKey
     */
    public ReferenceKeyRequest(String referenceKey) {
        this.referenceKey = referenceKey;
    }

    /**
     * @return The referenceKey
     */
    public String getReferenceKey() {
        return referenceKey;
    }

    /**
     * @param referenceKey The referenceKey
     */
    public void setReferenceKey(String referenceKey) {
        this.referenceKey = referenceKey;
    }

    public ReferenceKeyRequest withReferenceKey(String referenceKey) {
        this.referenceKey = referenceKey;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(referenceKey).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ReferenceKeyRequest) == false) {
            return false;
        }
        ReferenceKeyRequest rhs = ((ReferenceKeyRequest) other);
        return new EqualsBuilder().append(referenceKey, rhs.referenceKey).isEquals();
    }
}
