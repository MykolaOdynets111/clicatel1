package com.clickatell.models.mc2.accounts.request.billing_info;

import com.clickatell.utils.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by oshchur on 08.08.2016.
 */
public class BillingInfoRequest {
    private ContactInfoRequest billingContact;
    private AddressInfoRequest billingAddress;
    private Boolean businessTaxVatRegistered;
    private String vatCode;
    private String currency;

    /**
     * No args constructor for use in serialization
     */
    public BillingInfoRequest(Integer countryId) {
        String province;
        String postalCode;

        switch (countryId) {
            //USA
            case 236: {
                province = "TX";
                postalCode = "77001";
                break;
            }
            //CANADA
            case 40: {
                province = "ON";
                postalCode = "K1P 1J1";
                break;
            }
            default: {
                province = "Province" + StringUtils.generateRandomString(8);
                postalCode = StringUtils.generateRandomIntegerString(5);
            }
        }

        this.billingContact = new ContactInfoRequest("8014684022",
                "email" + StringUtils.generateRandomString(10) + "@fakeemail.perfectial.com",
                "FirstName" + StringUtils.generateRandomString(8),
                "LastName" + StringUtils.generateRandomString(8));
        this.billingAddress = new AddressInfoRequest("AddressLine1_" + StringUtils.generateRandomString(8),
                "AddressLine2_" + StringUtils.generateRandomString(8),
                "City_" + StringUtils.generateRandomString(8),
                province,
                postalCode,
                countryId);
        this.businessTaxVatRegistered = false;
        this.vatCode = null;
        this.currency = "USD";
    }

    public BillingInfoRequest() {
        this(236);
    }

    /**
     * @param billingContact
     * @param businessTaxVatRegistered
     * @param vatCode
     * @param billingAddress
     * @param currency
     */
    public BillingInfoRequest(ContactInfoRequest billingContact, AddressInfoRequest billingAddress, Boolean businessTaxVatRegistered, String vatCode, String currency) {
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

    public BillingInfoRequest withBillingContact(ContactInfoRequest billingContact) {
        this.billingContact = billingContact;
        return this;
    }

    /**
     * @return The billingAddress
     */
    public AddressInfoRequest getBillingAddress() {
        return billingAddress;
    }

    /**
     * @param billingAddress The billingAddress
     */
    public void setBillingAddress(AddressInfoRequest billingAddress) {
        this.billingAddress = billingAddress;
    }

    public BillingInfoRequest withBillingAddress(AddressInfoRequest billingAddress) {
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

    public BillingInfoRequest withBusinessTaxVatRegistered(Boolean businessTaxVatRegistered) {
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

    public BillingInfoRequest withVatCode(String vatCode) {
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

    public BillingInfoRequest withCurrency(String currency) {
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
        if ((other instanceof BillingInfoRequest) == false) {
            return false;
        }
        BillingInfoRequest rhs = ((BillingInfoRequest) other);
        return new EqualsBuilder().append(billingContact, rhs.billingContact).append(billingAddress, rhs.billingAddress).append(businessTaxVatRegistered, rhs.businessTaxVatRegistered).append(vatCode, rhs.vatCode).append(currency, rhs.currency).isEquals();
    }

    @Override
    public String toString() {
        return "BillingInfoRequest{" +
                "billingContact=" + billingContact +
                ", billingAddress=" + billingAddress +
                ", businessTaxVatRegistered=" + businessTaxVatRegistered +
                ", vatCode='" + vatCode + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
