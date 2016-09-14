package com.clickatell.models.mc2.payment_methods.response;

import com.clickatell.models.mc2.payment_methods.CardTypeEnum;
import com.clickatell.models.mc2.payment_methods.PaymentType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by sbryt on 8/9/2016.
 */

public class CreditCardResponse {

    private String id;
    private PaymentType paymentType;
    private CardTypeEnum cardType;
    private String cardNumber;
    private String cardHolderFirstName;
    private String cardHolderLastName;
    private Integer expirationMonth;
    private Integer expirationYear;

    /**
     * No args constructor for use in serialization
     */
    public CreditCardResponse() {
    }

    /**
     * @param expirationYear
     * @param expirationMonth
     * @param paymentType
     * @param cardHolderLastName
     * @param cardType
     * @param cardHolderFirstName
     * @param cardNumber
     */
    public CreditCardResponse(String id, PaymentType paymentType, CardTypeEnum cardType, String cardNumber, String cardHolderFirstName, String cardHolderLastName, Integer expirationMonth, Integer expirationYear) {
        this.id = id;
        this.paymentType = paymentType;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardHolderFirstName = cardHolderFirstName;
        this.cardHolderLastName = cardHolderLastName;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public CreditCardResponse withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The paymentType
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType The paymentType
     */
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public CreditCardResponse withPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    /**
     * @return The cardType
     */
    public CardTypeEnum getCardType() {
        return cardType;
    }

    /**
     * @param cardType The cardType
     */
    public void setCardType(CardTypeEnum cardType) {
        this.cardType = cardType;
    }

    public CreditCardResponse withCardType(CardTypeEnum cardType) {
        this.cardType = cardType;
        return this;
    }

    /**
     * @return The cardNumber
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @param cardNumber The cardNumber
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CreditCardResponse withCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    /**
     * @return The cardHolderFirstName
     */
    public String getCardHolderFirstName() {
        return cardHolderFirstName;
    }

    /**
     * @param cardHolderFirstName The cardHolderFirstName
     */
    public void setCardHolderFirstName(String cardHolderFirstName) {
        this.cardHolderFirstName = cardHolderFirstName;
    }

    public CreditCardResponse withCardHolderFirstName(String cardHolderFirstName) {
        this.cardHolderFirstName = cardHolderFirstName;
        return this;
    }

    /**
     * @return The cardHolderLastName
     */
    public String getCardHolderLastName() {
        return cardHolderLastName;
    }

    /**
     * @param cardHolderLastName The cardHolderLastName
     */
    public void setCardHolderLastName(String cardHolderLastName) {
        this.cardHolderLastName = cardHolderLastName;
    }

    public CreditCardResponse withCardHolderLastName(String cardHolderLastName) {
        this.cardHolderLastName = cardHolderLastName;
        return this;
    }

    /**
     * @return The expirationMonth
     */
    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    /**
     * @param expirationMonth The expirationMonth
     */
    public void setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public CreditCardResponse withExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
        return this;
    }

    /**
     * @return The expirationYear
     */
    public Integer getExpirationYear() {
        return expirationYear;
    }

    /**
     * @param expirationYear The expirationYear
     */
    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    public CreditCardResponse withExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(paymentType).append(cardType).append(cardNumber).append(cardHolderFirstName).append(cardHolderLastName).append(expirationMonth).append(expirationYear).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CreditCardResponse) == false) {
            return false;
        }
        CreditCardResponse rhs = ((CreditCardResponse) other);
        return new EqualsBuilder().append(id, rhs.id).append(paymentType, rhs.paymentType).append(cardType, rhs.cardType).append(cardNumber, rhs.cardNumber).append(cardHolderFirstName, rhs.cardHolderFirstName).append(cardHolderLastName, rhs.cardHolderLastName).append(expirationMonth, rhs.expirationMonth).append(expirationYear, rhs.expirationYear).isEquals();
    }

}
