package com.clickatell.models.mc2.payment_methods.request;

import com.clickatell.models.mc2.accounts.request.billing_info.BillingInfoRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by sbryt on 8/8/2016.
 */
public class CreateCreditCardRequest {
    private String tractFirstName;
    private String tractLastName;
    private String tractCardType;
    private String tractCardNumber;
    private String tractCvv;
    private String tractDate;
    private String tractEmail;
    private String tractAddress1;
    private String tractCity;
    private String tractState;
    private String tractCountry;
    private String tractPostalCode;
    private String tractToken;

    /**
     * No args constructor for use in serialization
     */
    public CreateCreditCardRequest() {
    }

    /**
     * @param tractCity
     * @param tractCvv
     * @param tractAddress1
     * @param tractPostalCode
     * @param tractCountry
     * @param tractCardNumber
     * @param tractCardType
     * @param tractLastName
     * @param tractDate
     * @param tractFirstName
     * @param tractState
     * @param tractEmail
     * @param tractToken
     */
    public CreateCreditCardRequest(String tractFirstName, String tractLastName, String tractCardType, String tractCardNumber, String tractCvv, String tractDate, String tractEmail, String tractAddress1, String tractCity, String tractState, String tractCountry, String tractPostalCode, String tractToken) {
        this.tractFirstName = tractFirstName;
        this.tractLastName = tractLastName;
        this.tractCardType = tractCardType;
        this.tractCardNumber = tractCardNumber;
        this.tractCvv = tractCvv;
        this.tractDate = tractDate;
        this.tractEmail = tractEmail;
        this.tractAddress1 = tractAddress1;
        this.tractCity = tractCity;
        this.tractState = tractState;
        this.tractCountry = tractCountry;
        this.tractPostalCode = tractPostalCode;
        this.tractToken = tractToken;
    }

    /**
     * @param billingInfoRequest
     * @param referenceToken
     */
    public CreateCreditCardRequest(BillingInfoRequest billingInfoRequest, String referenceToken) {
        this.tractFirstName = billingInfoRequest.getBillingContact().getFirstName();
        this.tractLastName = billingInfoRequest.getBillingContact().getLastName();
        this.tractCardType = "MASTERCARD";
        this.tractCardNumber = "5555555555554444";
        this.tractCvv = "123";
        this.tractDate = "07/2022";
        this.tractEmail = billingInfoRequest.getBillingContact().getEmailAddress();
        this.tractAddress1 = billingInfoRequest.getBillingAddress().getAddress1();
        this.tractCity = billingInfoRequest.getBillingAddress().getCity();
        this.tractState = billingInfoRequest.getBillingAddress().getProvince();
        this.tractCountry = "UKR";
        this.tractPostalCode = billingInfoRequest.getBillingAddress().getPostalCode();
        this.tractToken = referenceToken;
    }

    /**
     * @return The tractFirstName
     */
    @JsonProperty("tract_first_name")
    public String getTractFirstName() {
        return tractFirstName;
    }

    /**
     * @param tractFirstName The tract_first_name
     */
    public void setTractFirstName(String tractFirstName) {
        this.tractFirstName = tractFirstName;
    }

    public CreateCreditCardRequest withTractFirstName(String tractFirstName) {
        this.tractFirstName = tractFirstName;
        return this;
    }

    /**
     * @return The tractLastName
     */
    @JsonProperty("tract_last_name")
    public String getTractLastName() {
        return tractLastName;
    }

    /**
     * @param tractLastName The tract_last_name
     */
    public void setTractLastName(String tractLastName) {
        this.tractLastName = tractLastName;
    }

    public CreateCreditCardRequest withTractLastName(String tractLastName) {
        this.tractLastName = tractLastName;
        return this;
    }

    /**
     * @return The tractCardType
     */
    @JsonProperty("tract_card_type")
    public String getTractCardType() {
        return tractCardType;
    }

    /**
     * @param tractCardType The tract_card_type
     */
    public void setTractCardType(String tractCardType) {
        this.tractCardType = tractCardType;
    }

    public CreateCreditCardRequest withTractCardType(String tractCardType) {
        this.tractCardType = tractCardType;
        return this;
    }

    /**
     * @return The tractCardNumber
     */
    @JsonProperty("tract_card_number")
    public String getTractCardNumber() {
        return tractCardNumber;
    }

    /**
     * @param tractCardNumber The tract_card_number
     */
    public void setTractCardNumber(String tractCardNumber) {
        this.tractCardNumber = tractCardNumber;
    }

    public CreateCreditCardRequest withTractCardNumber(String tractCardNumber) {
        this.tractCardNumber = tractCardNumber;
        return this;
    }

    /**
     * @return The tractCvv
     */
    @JsonProperty("tract_cvv")
    public String getTractCvv() {
        return tractCvv;
    }

    /**
     * @param tractCvv The tract_cvv
     */
    public void setTractCvv(String tractCvv) {
        this.tractCvv = tractCvv;
    }

    public CreateCreditCardRequest withTractCvv(String tractCvv) {
        this.tractCvv = tractCvv;
        return this;
    }

    /**
     * @return The tractDate
     */
    @JsonProperty("tract_date")
    public String getTractDate() {
        return tractDate;
    }

    /**
     * @param tractDate The tract_date
     */
    public void setTractDate(String tractDate) {
        this.tractDate = tractDate;
    }

    public CreateCreditCardRequest withTractDate(String tractDate) {
        this.tractDate = tractDate;
        return this;
    }

    /**
     * @return The tractEmail
     */
    @JsonProperty("tract_email")
    public String getTractEmail() {
        return tractEmail;
    }

    /**
     * @param tractEmail The tract_email
     */
    public void setTractEmail(String tractEmail) {
        this.tractEmail = tractEmail;
    }

    public CreateCreditCardRequest withTractEmail(String tractEmail) {
        this.tractEmail = tractEmail;
        return this;
    }

    /**
     * @return The tractAddress1
     */
    @JsonProperty("tract_address_1")
    public String getTractAddress1() {
        return tractAddress1;
    }

    /**
     * @param tractAddress1 The tract_address_1
     */
    public void setTractAddress1(String tractAddress1) {
        this.tractAddress1 = tractAddress1;
    }

    public CreateCreditCardRequest withTractAddress1(String tractAddress1) {
        this.tractAddress1 = tractAddress1;
        return this;
    }

    /**
     * @return The tractCity
     */
    @JsonProperty("tract_city")
    public String getTractCity() {
        return tractCity;
    }

    /**
     * @param tractCity The tract_city
     */
    public void setTractCity(String tractCity) {
        this.tractCity = tractCity;
    }

    public CreateCreditCardRequest withTractCity(String tractCity) {
        this.tractCity = tractCity;
        return this;
    }

    /**
     * @return The tractState
     */
    @JsonProperty("tract_state")
    public String getTractState() {
        return tractState;
    }

    /**
     * @param tractState The tract_state
     */
    public void setTractState(String tractState) {
        this.tractState = tractState;
    }

    public CreateCreditCardRequest withTractState(String tractState) {
        this.tractState = tractState;
        return this;
    }

    /**
     * @return The tractCountry
     */
    @JsonProperty("tract_country")
    public String getTractCountry() {
        return tractCountry;
    }

    /**
     * @param tractCountry The tract_country
     */
    public void setTractCountry(String tractCountry) {
        this.tractCountry = tractCountry;
    }

    public CreateCreditCardRequest withTractCountry(String tractCountry) {
        this.tractCountry = tractCountry;
        return this;
    }

    /**
     * @return The tractPostalCode
     */
    @JsonProperty("tract_postal_code")
    public String getTractPostalCode() {
        return tractPostalCode;
    }

    /**
     * @param tractPostalCode The tract_postal_code
     */
    public void setTractPostalCode(String tractPostalCode) {
        this.tractPostalCode = tractPostalCode;
    }

    public CreateCreditCardRequest withTractPostalCode(String tractPostalCode) {
        this.tractPostalCode = tractPostalCode;
        return this;
    }

    /**
     * @return The tractToken
     */
    @JsonProperty("tract_token")
    public String getTractToken() {
        return tractToken;
    }

    /**
     * @param tractToken The tract_token
     */
    public void setTractToken(String tractToken) {
        this.tractToken = tractToken;
    }

    public CreateCreditCardRequest withTractToken(String tractToken) {
        this.tractToken = tractToken;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(tractFirstName).append(tractLastName).append(tractCardType).append(tractCardNumber).append(tractCvv).append(tractDate).append(tractEmail).append(tractAddress1).append(tractCity).append(tractState).append(tractCountry).append(tractPostalCode).append(tractToken).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CreateCreditCardRequest) == false) {
            return false;
        }
        CreateCreditCardRequest rhs = ((CreateCreditCardRequest) other);
        return new EqualsBuilder().append(tractFirstName, rhs.tractFirstName).append(tractLastName, rhs.tractLastName).append(tractCardType, rhs.tractCardType).append(tractCardNumber, rhs.tractCardNumber).append(tractCvv, rhs.tractCvv).append(tractDate, rhs.tractDate).append(tractEmail, rhs.tractEmail).append(tractAddress1, rhs.tractAddress1).append(tractCity, rhs.tractCity).append(tractState, rhs.tractState).append(tractCountry, rhs.tractCountry).append(tractPostalCode, rhs.tractPostalCode).append(tractToken, rhs.tractToken).isEquals();
    }
}
