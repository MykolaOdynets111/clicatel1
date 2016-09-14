package com.clickatell.models.mc2.user_profiles.request;

/**
 * Created by oshchur on 14.07.2016.
 */
public class UserQuotaRequest {

    private Integer maxAllowedAccountLimit;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserQuotaRequest() {
    }

    /**
     *
     * @param maxAllowedAccountLimit
     */
    public UserQuotaRequest(Integer maxAllowedAccountLimit) {
        this.maxAllowedAccountLimit = maxAllowedAccountLimit;
    }

    /**
     *
     * @return
     * The maxAllowedAccountLimit
     */
    public Integer getMaxAllowedAccountLimit() {
        return maxAllowedAccountLimit;
    }

    /**
     *
     * @param maxAllowedAccountLimit
     * The maxAllowedAccountLimit
     */
    public void setMaxAllowedAccountLimit(Integer maxAllowedAccountLimit) {
        this.maxAllowedAccountLimit = maxAllowedAccountLimit;
    }

    public UserQuotaRequest withMaxAllowedAccountLimit(Integer maxAllowedAccountLimit) {
        this.maxAllowedAccountLimit = maxAllowedAccountLimit;
        return this;
    }
}
