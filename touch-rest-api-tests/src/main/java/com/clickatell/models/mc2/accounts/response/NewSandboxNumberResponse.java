package com.clickatell.models.mc2.accounts.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by oshchur on 10.08.2016.
 */
public class NewSandboxNumberResponse {
    private String id;

    /**
     * No args constructor for use in serialization
     *
     */
    public NewSandboxNumberResponse() {
    }

    /**
     *
     * @param id
     */
    public NewSandboxNumberResponse(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public NewSandboxNumberResponse withId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NewSandboxNumberResponse)) {
            return false;
        }
        NewSandboxNumberResponse rhs = ((NewSandboxNumberResponse) other);
        return new EqualsBuilder().append(id, rhs.id).isEquals();
    }
}
