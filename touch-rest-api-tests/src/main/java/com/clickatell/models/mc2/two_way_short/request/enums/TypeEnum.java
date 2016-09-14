package com.clickatell.models.mc2.two_way_short.request.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oshchur on 07.09.2016.
 */
public enum TypeEnum {
    @SerializedName("RANDOM")
    RANDOM("RANDOM"),

    @SerializedName("VANITY")
    VANITY("VANITY");

    private String value;

    TypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
