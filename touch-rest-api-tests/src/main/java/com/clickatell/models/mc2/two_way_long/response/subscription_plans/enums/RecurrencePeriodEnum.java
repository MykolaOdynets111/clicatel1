package com.clickatell.models.mc2.two_way_long.response.subscription_plans.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sbryt on 8/25/2016.
 */
public enum RecurrencePeriodEnum {
    @SerializedName("BILLCYCLE")
    BILLCYCLE("BILLCYCLE"),

    @SerializedName("DAY")
    DAY("DAY"),

    @SerializedName("FOURYEARS")
    FOURYEARS("FOURYEARS"),

    @SerializedName("FIVEYEARS")
    FIVEYEARS("FIVEYEARS"),

    @SerializedName("MONTH")
    MONTH("MONTH"),

    @SerializedName("QUARTER")
    QUARTER("QUARTER"),

    @SerializedName("THREEYEARS")
    THREEYEARS("THREEYEARS"),

    @SerializedName("TWICEAMONTH")
    TWICEAMONTH("TWICEAMONTH"),

    @SerializedName("TWICEAYEAR")
    TWICEAYEAR("TWICEAYEAR"),

    @SerializedName("TWOWEEKS")
    TWOWEEKS("TWOWEEKS"),

    @SerializedName("TWOYEARS")
    TWOYEARS("TWOYEARS"),

    @SerializedName("WEEK")
    WEEK("WEEK"),

    @SerializedName("YEAR")
    YEAR("YEAR"),

    @SerializedName("FOURMONTHS")
    FOURMONTHS("FOURMONTHS");

    private String value;

    RecurrencePeriodEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
