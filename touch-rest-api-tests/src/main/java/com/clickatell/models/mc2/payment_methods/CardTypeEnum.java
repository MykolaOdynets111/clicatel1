package com.clickatell.models.mc2.payment_methods;

/**
 * Gets or Sets cardType
 */
public enum CardTypeEnum {
    VISA("VISA"),
    MASTERCARD("MASTERCARD");

    private String value;

    CardTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
