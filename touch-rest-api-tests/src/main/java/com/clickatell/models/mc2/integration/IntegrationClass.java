
package com.clickatell.models.mc2.integration;

import com.clickatell.models.mc2.integration.general.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class IntegrationClass {

    private Details details;
    private Features features;
    private Settings settings;
    private TwoWaySettings twoWaySettings;
    private String id;
    private String apiKey;
    private Integer apiCallCount;
    private Boolean active;
    private Boolean archived;
    private Country country;
    private String accountId;
    private String userId;
    private String creationDate;
    private String creationDateMillis;
    private List<PhoneList> phoneList = new ArrayList<PhoneList>();
    private List<ReplyNumber> replyNumberList = new ArrayList<ReplyNumber>();

    /**
     * No args constructor for use in serialization
     */
    public IntegrationClass() {
    }

    /**
     * @param accountId
     * @param phoneList
     * @param settings
     * @param apiKey
     * @param country
     * @param replyNumberList
     * @param id
     * @param creationDateMillis
     * @param creationDate
     * @param details
     * @param apiCallCount
     * @param archived
     * @param userId
     * @param features
     * @param active
     * @param twoWaySettings
     */
    public IntegrationClass(Details details, Features features, Settings settings, TwoWaySettings twoWaySettings, String id, String apiKey, Integer apiCallCount, Boolean active, Boolean archived, Country country, String accountId, String userId, String creationDate, String creationDateMillis, List<PhoneList> phoneList, List<ReplyNumber> replyNumberList) {
        this.details = details;
        this.features = features;
        this.settings = settings;
        this.twoWaySettings = twoWaySettings;
        this.id = id;
        this.apiKey = apiKey;
        this.apiCallCount = apiCallCount;
        this.active = active;
        this.archived = archived;
        this.country = country;
        this.accountId = accountId;
        this.userId = userId;
        this.creationDate = creationDate;
        this.creationDateMillis = creationDateMillis;
        this.phoneList = phoneList;
        this.replyNumberList = replyNumberList;
    }

    /**
     * @return The details
     */
    public Details getDetails() {
        return details;
    }

    /**
     * @param details The details
     */
    public void setDetails(Details details) {
        this.details = details;
    }

    public IntegrationClass withDetails(Details details) {
        this.details = details;
        return this;
    }

    /**
     * @return The features
     */
    public Features getFeatures() {
        return features;
    }

    /**
     * @param features The features
     */
    public void setFeatures(Features features) {
        this.features = features;
    }

    public IntegrationClass withFeatures(Features features) {
        this.features = features;
        return this;
    }

    /**
     * @return The settings
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * @param settings The settings
     */
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public IntegrationClass withSettings(Settings settings) {
        this.settings = settings;
        return this;
    }

    /**
     * @return The twoWaySettings
     */
    public TwoWaySettings getTwoWaySettings() {
        return twoWaySettings;
    }

    /**
     * @param twoWaySettings The twoWaySettings
     */
    public void setTwoWaySettings(TwoWaySettings twoWaySettings) {
        this.twoWaySettings = twoWaySettings;
    }

    public IntegrationClass withTwoWaySettings(TwoWaySettings twoWaySettings) {
        this.twoWaySettings = twoWaySettings;
        return this;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public IntegrationClass withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey The apiKey
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public IntegrationClass withApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    /**
     * @return The apiCallCount
     */
    public Integer getApiCallCount() {
        return apiCallCount;
    }

    /**
     * @param apiCallCount The apiCallCount
     */
    public void setApiCallCount(Integer apiCallCount) {
        this.apiCallCount = apiCallCount;
    }

    public IntegrationClass withApiCallCount(Integer apiCallCount) {
        this.apiCallCount = apiCallCount;
        return this;
    }

    /**
     * @return The active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active The active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    public IntegrationClass withActive(Boolean active) {
        this.active = active;
        return this;
    }

    /**
     * @return The archived
     */
    public Boolean getArchived() {
        return archived;
    }

    /**
     * @param archived The archived
     */
    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public IntegrationClass withArchived(Boolean archived) {
        this.archived = archived;
        return this;
    }

    /**
     * @return The country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    public IntegrationClass withCountry(Country country) {
        this.country = country;
        return this;
    }

    /**
     * @return The accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * @param accountId The accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public IntegrationClass withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public IntegrationClass withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * @return The creationDate
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate The creationDate
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public IntegrationClass withCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * @return The creationDateMillis
     */
    public String getCreationDateMillis() {
        return creationDateMillis;
    }

    /**
     * @param creationDateMillis The creationDateMillis
     */
    public void setCreationDateMillis(String creationDateMillis) {
        this.creationDateMillis = creationDateMillis;
    }

    public IntegrationClass withCreationDateMillis(String creationDateMillis) {
        this.creationDateMillis = creationDateMillis;
        return this;
    }

    /**
     * @return The phoneList
     */
    public List<PhoneList> getPhoneList() {
        return phoneList;
    }

    /**
     * @param phoneList The phoneList
     */
    public void setPhoneList(List<PhoneList> phoneList) {
        this.phoneList = phoneList;
    }

    public IntegrationClass withPhoneList(List<PhoneList> phoneList) {
        this.phoneList = phoneList;
        return this;
    }

    /**
     * @return The replyNumberList
     */
    public List<ReplyNumber> getReplyNumberList() {
        return replyNumberList;
    }

    /**
     * @param replyNumberList The replyNumberList
     */
    public void setReplyNumberList(List<ReplyNumber> replyNumberList) {
        this.replyNumberList = replyNumberList;
    }

    public IntegrationClass withReplyNumbers(List<ReplyNumber> replyNumbers) {
        this.replyNumberList = replyNumbers;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntegrationClass that = (IntegrationClass) o;

        if (getDetails() != null ? !getDetails().equals(that.getDetails()) : that.getDetails() != null) return false;
        if (getFeatures() != null ? !getFeatures().equals(that.getFeatures()) : that.getFeatures() != null)
            return false;
        if (getSettings() != null ? !getSettings().equals(that.getSettings()) : that.getSettings() != null)
            return false;
        if (getTwoWaySettings() != null ? !getTwoWaySettings().equals(that.getTwoWaySettings()) : that.getTwoWaySettings() != null)
            return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getApiKey() != null ? !getApiKey().equals(that.getApiKey()) : that.getApiKey() != null) return false;
        if (getApiCallCount() != null ? !getApiCallCount().equals(that.getApiCallCount()) : that.getApiCallCount() != null)
            return false;
        if (getActive() != null ? !getActive().equals(that.getActive()) : that.getActive() != null) return false;
        if (getArchived() != null ? !getArchived().equals(that.getArchived()) : that.getArchived() != null)
            return false;
        if (getCountry() != null ? !getCountry().equals(that.getCountry()) : that.getCountry() != null) return false;
        if (getAccountId() != null ? !getAccountId().equals(that.getAccountId()) : that.getAccountId() != null)
            return false;
        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        if (getPhoneList() != null ? !getPhoneList().equals(that.getPhoneList()) : that.getPhoneList() != null)
            return false;
        return getReplyNumberList() != null ? getReplyNumberList().equals(that.getReplyNumberList()) : that.getReplyNumberList() == null;

    }

    @Override
    public int hashCode() {
        int result = getDetails() != null ? getDetails().hashCode() : 0;
        result = 31 * result + (getFeatures() != null ? getFeatures().hashCode() : 0);
        result = 31 * result + (getSettings() != null ? getSettings().hashCode() : 0);
        result = 31 * result + (getTwoWaySettings() != null ? getTwoWaySettings().hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getApiKey() != null ? getApiKey().hashCode() : 0);
        result = 31 * result + (getApiCallCount() != null ? getApiCallCount().hashCode() : 0);
        result = 31 * result + (getActive() != null ? getActive().hashCode() : 0);
        result = 31 * result + (getArchived() != null ? getArchived().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getAccountId() != null ? getAccountId().hashCode() : 0);
        result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
        result = 31 * result + (getPhoneList() != null ? getPhoneList().hashCode() : 0);
        result = 31 * result + (getReplyNumberList() != null ? getReplyNumberList().hashCode() : 0);
        return result;
    }
}
