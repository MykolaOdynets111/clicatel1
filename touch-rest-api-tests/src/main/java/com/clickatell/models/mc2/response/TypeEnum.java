package com.clickatell.models.mc2.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sbryt on 8/25/2016.
 */
public enum TypeEnum {
    @SerializedName("LONG_NUMBER")
    LONG_NUMBER("LONG_NUMBER"),

    @SerializedName("SHORT_NUMBER")
    SHORT_NUMBER("SHORT_NUMBER");

    private String value;

    TypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
