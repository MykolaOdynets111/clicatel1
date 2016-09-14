package com.clickatell.models.mc2.accounts.response.billing_info;

import com.clickatell.models.mc2.accounts.request.billing_info.ContactInfoRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by oshchur on 08.08.2016.
 */
public class BillingInfoResponse {
    private ContactInfoRequest billingContact;
    private AddressInfoResponse billingAddress;
    private Boolean businessTaxVatRegistered;
    private String vatCode;
    private String currency;

    /**
     * No args constructor for use in serialization
     */
    public BillingInfoResponse() {
    }

    /**
     * @param billingContact
     * @param businessTaxVatRegistered
     * @param vatCode
     * @param billingAddress
     * @param currency
     */
    public BillingInfoResponse(ContactInfoRequest billingContact, AddressInfoResponse billingAddress, Boolean businessTaxVatRegistered, String vatCode, String currency) {
        this.billingContact = billingContact;
        this.billingAddress = billingAddress;
        this.businessTaxVatRegistered = businessTaxVatRegistered;
        this.vatCode = vatCode;
        this.currency = currency;
    }

    /**
     * @return The billingContact
     */
    public ContactInfoRequest getBillingContact() {
        return billingContact;
    }

    /**
     * @param billingContact The billingContact
     */
    public void setBillingContact(ContactInfoRequest billingContact) {
        this.billingContact = billingContact;
    }

    public BillingInfoResponse withBillingContact(ContactInfoRequest billingContact) {
        this.billingContact = billingContact;
        return this;
    }

    /**
     * @return The billingAddress
     */
    public AddressInfoResponse getBillingAddress() {
        return billingAddress;
    }

    /**
     * @param billingAddress The billingAddress
     */
    public void setBillingAddress(AddressInfoResponse billingAddress) {
        this.billingAddress = billingAddress;
    }

    public BillingInfoResponse withBillingAddress(AddressInfoResponse billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    /**
     * @return The businessTaxVatRegistered
     */
    public Boolean getBusinessTaxVatRegistered() {
        return businessTaxVatRegistered;
    }

    /**
     * @param businessTaxVatRegistered The businessTaxVatRegistered
     */
    public void setBusinessTaxVatRegistered(Boolean businessTaxVatRegistered) {
        this.businessTaxVatRegistered = businessTaxVatRegistered;
    }

    public BillingInfoResponse withBusinessTaxVatRegistered(Boolean businessTaxVatRegistered) {
        this.businessTaxVatRegistered = businessTaxVatRegistered;
        return this;
    }

    /**
     * @return The vatCode
     */
    public String getVatCode() {
        return vatCode;
    }

    /**
     * @param vatCode The vatCode
     */
    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public BillingInfoResponse withVatCode(String vatCode) {
        this.vatCode = vatCode;
        return this;
    }

    /**
     * @return The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BillingInfoResponse withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(billingContact).append(billingAddress).append(businessTaxVatRegistered).append(vatCode).append(currency).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BillingInfoResponse) == false) {
            return false;
        }
        BillingInfoResponse rhs = ((BillingInfoResponse) other);
        return new EqualsBuilder().append(billingContact, rhs.billingContact).append(billingAddress, rhs.billingAddress).append(businessTaxVatRegistered, rhs.businessTaxVatRegistered).append(vatCode, rhs.vatCode).append(currency, rhs.currency).isEquals();
    }
}
