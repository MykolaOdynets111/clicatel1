package com.clickatell.models.mc2.payment_methods;

/**
 * Gets or Sets cardType
 */
public enum PaymentType {
    CREDIT_CARD("CREDIT_CARD");

    private String value;

    PaymentType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
