package com.clickatell.models.mc2.payment_methods.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by oshchur on 26.08.2016.
 */
public class GetReferenceTokenResponse {
    private String referencedToken;

    /**
     * No args constructor for use in serialization
     */
    public GetReferenceTokenResponse() {
    }

    /**
     * @param referencedToken
     */
    public GetReferenceTokenResponse(String referencedToken) {
        this.referencedToken = referencedToken;
    }

    /**
     * @return The referencedToken
     */
    public String getReferencedToken() {
        return referencedToken;
    }

    /**
     * @param referencedToken The referencedToken
     */
    public void setReferencedToken(String referencedToken) {
        this.referencedToken = referencedToken;
    }

    public GetReferenceTokenResponse withReferencedToken(String referencedToken) {
        this.referencedToken = referencedToken;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(referencedToken).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GetReferenceTokenResponse) == false) {
            return false;
        }
        GetReferenceTokenResponse rhs = ((GetReferenceTokenResponse) other);
        return new EqualsBuilder().append(referencedToken, rhs.referencedToken).isEquals();
    }
}
