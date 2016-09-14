package com.clickatell.models.mc2.two_way_long.response.subscription_plans.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sbryt on 8/25/2016.
 */
public enum CurrencyEnum {
    @SerializedName("USD")
    USD("USD"),

    @SerializedName("EUR")
    EUR("EUR"),

    @SerializedName("CAD")
    CAD("CAD"),

    @SerializedName("GBP")
    GBP("GBP"),

    @SerializedName("MXN")
    MXN("MXN"),

    @SerializedName("JPY")
    JPY("JPY"),

    @SerializedName("NOK")
    NOK("NOK"),

    @SerializedName("SEK")
    SEK("SEK"),

    @SerializedName("DKK")
    DKK("DKK"),

    @SerializedName("AUD")
    AUD("AUD"),

    @SerializedName("INR")
    INR("INR"),

    @SerializedName("NZD")
    NZD("NZD"),

    @SerializedName("ZAR")
    ZAR("ZAR"),

    @SerializedName("AED")
    AED("AED"),

    @SerializedName("SAR")
    SAR("SAR"),

    @SerializedName("QAR")
    QAR("QAR"),

    @SerializedName("EGP")
    EGP("EGP"),

    @SerializedName("OMR")
    OMR("OMR"),

    @SerializedName("KWD")
    KWD("KWD"),

    @SerializedName("BHD")
    BHD("BHD"),

    @SerializedName("ILS")
    ILS("ILS"),

    @SerializedName("CNY")
    CNY("CNY"),

    @SerializedName("HKD")
    HKD("HKD"),

    @SerializedName("CHF")
    CHF("CHF");

    private String value;

    CurrencyEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
