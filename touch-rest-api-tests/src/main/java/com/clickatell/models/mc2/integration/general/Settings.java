
package com.clickatell.models.mc2.integration.general;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class Settings {

    private Boolean messagePartsEnabled = false;
    private Integer partCount;
    private Boolean phoneNumberConversionEnabled = false;
    private Boolean ipFraudProtectionEnabled = false;
    private List<String> whitelistIps = new ArrayList<String>();
    private Boolean deliveryNotificationEnabled = false;
    private String countryCode = null;
    private String callbackUrl;
    private String httpMethod = HttpMethod.GET_METHOD;
    private Boolean httpAuthHeaderEnabled = false;
    private String callbackUser;
    private String callbackPswd;

    /**
     * No args constructor for use in serialization
     */
    public Settings() {
    }

    /**
     * @param callbackUser
     * @param callbackUrl
     * @param messagePartsEnabled
     * @param httpAuthHeaderEnabled
     * @param callbackPswd
     * @param httpMethod
     * @param phoneNumberConversionEnabled
     * @param countryCode
     * @param deliveryNotificationEnabled
     * @param whitelistIps
     * @param partCount
     * @param ipFraudProtectionEnabled
     */
    public Settings(Boolean messagePartsEnabled, Integer partCount, Boolean phoneNumberConversionEnabled, Boolean ipFraudProtectionEnabled, List<String> whitelistIps, Boolean deliveryNotificationEnabled, String countryCode, String callbackUrl, String httpMethod, Boolean httpAuthHeaderEnabled, String callbackUser, String callbackPswd) {
        this.messagePartsEnabled = messagePartsEnabled;
        this.partCount = partCount;
        this.phoneNumberConversionEnabled = phoneNumberConversionEnabled;
        this.ipFraudProtectionEnabled = ipFraudProtectionEnabled;
        this.whitelistIps = whitelistIps;
        this.deliveryNotificationEnabled = deliveryNotificationEnabled;
        this.countryCode = countryCode;
        this.callbackUrl = callbackUrl;
        this.httpMethod = httpMethod;
        this.httpAuthHeaderEnabled = httpAuthHeaderEnabled;
        this.callbackUser = callbackUser;
        this.callbackPswd = callbackPswd;
    }

    /**
     * @return The messagePartsEnabled
     */
    public Boolean getMessagePartsEnabled() {
        return messagePartsEnabled;
    }

    /**
     * @param messagePartsEnabled The messagePartsEnabled
     */
    public Settings setMessagePartsEnabled(Boolean messagePartsEnabled) {
        this.messagePartsEnabled = messagePartsEnabled;
        return this;
    }

    /**
     * @return The partCount
     */
    public Integer getPartCount() {
        return partCount;
    }

    /**
     * @param partCount The partCount
     */
    public Settings setPartCount(Integer partCount) {
        this.partCount = partCount;
        return this;
    }

    /**
     * @return The phoneNumberConversionEnabled
     */
    public Boolean getPhoneNumberConversionEnabled() {
        return phoneNumberConversionEnabled;
    }

    /**
     * @param phoneNumberConversionEnabled The phoneNumberConversionEnabled
     */
    public Settings setPhoneNumberConversionEnabled(Boolean phoneNumberConversionEnabled) {
        this.phoneNumberConversionEnabled = phoneNumberConversionEnabled;
        return this;
    }

    /**
     * @return The ipFraudProtectionEnabled
     */
    public Boolean getIpFraudProtectionEnabled() {
        return ipFraudProtectionEnabled;
    }

    /**
     * @param ipFraudProtectionEnabled The ipFraudProtectionEnabled
     */
    public Settings setIpFraudProtectionEnabled(Boolean ipFraudProtectionEnabled) {
        this.ipFraudProtectionEnabled = ipFraudProtectionEnabled;
        return this;
    }

    /**
     * @return The whitelistIps
     */
    public List<String> getWhitelistIps() {
        return whitelistIps;
    }

    /**
     * @param whitelistIps The whitelistIps
     */
    public Settings setWhitelistIps(List<String> whitelistIps) {
        this.whitelistIps = whitelistIps;
        return this;
    }

    /**
     * @return The deliveryNotificationEnabled
     */
    public Boolean getDeliveryNotificationEnabled() {
        return deliveryNotificationEnabled;
    }

    /**
     * @param deliveryNotificationEnabled The deliveryNotificationEnabled
     */
    public Settings setDeliveryNotificationEnabled(Boolean deliveryNotificationEnabled) {
        this.deliveryNotificationEnabled = deliveryNotificationEnabled;
        return this;
    }

    /**
     * @return The countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode The countryCode
     */
    public Settings setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    /**
     * @return The callbackUrl
     */
    public String getCallbackUrl() {
        return callbackUrl;
    }

    /**
     * @param callbackUrl The callbackUrl
     */
    public Settings setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    /**
     * @return The httpMethod
     */
    public String getHttpMethod() {
        return httpMethod;
    }

    /**
     * @param httpMethod The httpMethod
     */
    public Settings setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    /**
     * @return The httpAuthHeaderEnabled
     */
    public Boolean getHttpAuthHeaderEnabled() {
        return httpAuthHeaderEnabled;
    }

    /**
     * @param httpAuthHeaderEnabled The httpAuthHeaderEnabled
     */
    public Settings setHttpAuthHeaderEnabled(Boolean httpAuthHeaderEnabled) {
        this.httpAuthHeaderEnabled = httpAuthHeaderEnabled;
        return this;
    }

    /**
     * @return The callbackUser
     */
    public String getCallbackUser() {
        return callbackUser;
    }

    /**
     * @param callbackUser The callbackUser
     */
    public Settings setCallbackUser(String callbackUser) {
        this.callbackUser = callbackUser;
        return this;
    }

    /**
     * @return The callbackPswd
     */
    public String getCallbackPswd() {
        return callbackPswd;
    }

    /**
     * @param callbackPswd The callbackPswd
     */
    public Settings setCallbackPswd(String callbackPswd) {
        this.callbackPswd = callbackPswd;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Settings)) {
            return false;
        }
        Settings rhs = ((Settings) other);
        return new EqualsBuilder().append(messagePartsEnabled, rhs.messagePartsEnabled).append(partCount, rhs.partCount).append(phoneNumberConversionEnabled, rhs.phoneNumberConversionEnabled).append(ipFraudProtectionEnabled, rhs.ipFraudProtectionEnabled).append(whitelistIps, rhs.whitelistIps).append(deliveryNotificationEnabled, rhs.deliveryNotificationEnabled).append(countryCode, rhs.countryCode).append(callbackUrl, rhs.callbackUrl).append(httpMethod, rhs.httpMethod).append(httpAuthHeaderEnabled, rhs.httpAuthHeaderEnabled).append(callbackUser, rhs.callbackUser).append(callbackPswd, rhs.callbackPswd).isEquals();
    }

}
