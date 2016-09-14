package com.clickatell.models.mc2.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sbryt on 8/25/2016.
 */
public enum SolutionsEnum {
    @SerializedName("PLATFORM")
    PLATFORM("PLATFORM"),

    @SerializedName("TOUCH")
    TOUCH("TOUCH"),

    @SerializedName("SECURE")
    SECURE("SECURE"),

    @SerializedName("ENGAGE")
    ENGAGE("ENGAGE"),

    @SerializedName("ALL")
    ALL("ALL");

    private String value;

    SolutionsEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
