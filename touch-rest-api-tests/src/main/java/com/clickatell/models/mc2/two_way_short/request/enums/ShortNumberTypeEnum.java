package com.clickatell.models.mc2.two_way_short.request.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oshchur on 07.09.2016.
 */
public enum ShortNumberTypeEnum {
    @SerializedName("SHORT_NUMBER_RANDOM")
    SHORT_NUMBER_RANDOM("SHORT_NUMBER_RANDOM"),

    @SerializedName("SHORT_NUMBER_VANITY")
    SHORT_NUMBER_VANITY("SHORT_NUMBER_VANITY");

    private String value;

    ShortNumberTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
