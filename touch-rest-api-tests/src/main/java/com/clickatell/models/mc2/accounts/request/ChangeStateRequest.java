package com.clickatell.models.mc2.accounts.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by oshchur on 09.08.2016.
 */
public class ChangeStateRequest {
    private String state;

    /**
     * No args constructor for use in serialization
     *
     */
    public ChangeStateRequest() {
    }

    /**
     *
     * @param state
     */
    public ChangeStateRequest(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    public ChangeStateRequest withState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(state).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ChangeStateRequest)) {
            return false;
        }
        ChangeStateRequest rhs = ((ChangeStateRequest) other);
        return new EqualsBuilder().append(state, rhs.state).isEquals();
    }
}
