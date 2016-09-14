package com.clickatell.models.mc2.payment_methods.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbryt on 8/9/2016.
 */

public class CreditCardResponseAll {

    private List<CreditCardResponse> paymentMethods = new ArrayList<CreditCardResponse>();

    /**
     * No args constructor for use in serialization
     *
     */
    public CreditCardResponseAll() {
    }

    /**
     *
     * @param paymentMethods
     */
    public CreditCardResponseAll(List<CreditCardResponse> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    /**
     *
     * @return
     *     The paymentMethods
     */
    public List<CreditCardResponse> getPaymentMethods() {
        return paymentMethods;
    }

    /**
     *
     * @param paymentMethods
     *     The paymentMethods
     */
    public void setPaymentMethods(List<CreditCardResponse> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public CreditCardResponseAll withPaymentMethods(List<CreditCardResponse> paymentMethods) {
        this.paymentMethods = paymentMethods;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(paymentMethods).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CreditCardResponseAll) == false) {
            return false;
        }
        CreditCardResponseAll rhs = ((CreditCardResponseAll) other);
        return new EqualsBuilder().append(paymentMethods, rhs.paymentMethods).isEquals();
    }

}

