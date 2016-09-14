package com.clickatell.models.mc2.common_data.response.account_types;

/**
 * Created by sbryt on 8/9/2016.
 */
public enum AccountTypeNames {
    BUSINESS("Business"),
    PERSONAL("Personal"),
    RESELLER("Reseller");

    String value;

    AccountTypeNames(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
