
package com.clickatell.models.mc2.integration;

import com.clickatell.models.mc2.integration.general.Details;
import com.clickatell.models.mc2.integration.general.Features;
import com.clickatell.models.mc2.integration.general.Settings;
import com.clickatell.models.mc2.integration.general.TwoWaySettings;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class IntegrationRequest {

    private Details details;
    private Features features;
    private Settings settings;
    private TwoWaySettings twoWaySettings;
    private List<String> phoneNumberIds = new ArrayList<String>();
    private List<String> replyNumberIds = new ArrayList<String>();
    @JsonIgnore
    private String id;
    @JsonIgnore
    private String integrationName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public IntegrationRequest() {
        this.details = new Details();
        this.features = new Features();
        this.settings = new Settings();
        this.twoWaySettings = new TwoWaySettings();
        twoWaySettings.withCustomCommandsEnabled(false);
        this.id = "";
    }

    /**
     * 
     * @param replyNumberIds
     * @param phoneNumberIds
     * @param details
     * @param settings
     * @param features
     * @param twoWaySettings
     */
    public IntegrationRequest(Details details, Features features, Settings settings, TwoWaySettings twoWaySettings, List<String> phoneNumberIds, List<String> replyNumberIds) {
        this.details = details;
        this.features = features;
        this.settings = settings;
        this.twoWaySettings = twoWaySettings;
        this.phoneNumberIds = phoneNumberIds;
        this.replyNumberIds = replyNumberIds;
    }

    /**
     * 
     * @return
     *     The details
     */
    public Details getDetails() {
        return details;
    }

    /**
     * 
     * @param details
     *     The details
     */
    public void setDetails(Details details) {
        this.details = details;
    }

    public IntegrationRequest withDetails(Details details) {
        this.details = details;
        return this;
    }

    /**
     * 
     * @return
     *     The features
     */
    public Features getFeatures() {
        return features;
    }

    /**
     * 
     * @param features
     *     The features
     */
    public void setFeatures(Features features) {
        this.features = features;
    }

    public IntegrationRequest withFeatures(Features features) {
        this.features = features;
        return this;
    }

    /**
     * 
     * @return
     *     The settings
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * 
     * @param settings
     *     The settings
     */
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public IntegrationRequest withSettings(Settings settings) {
        this.settings = settings;
        return this;
    }

    /**
     * 
     * @return
     *     The twoWaySettings
     */
    public TwoWaySettings getTwoWaySettings() {
        return twoWaySettings;
    }

    /**
     * 
     * @param twoWaySettings
     *     The twoWaySettings
     */
    public void setTwoWaySettings(TwoWaySettings twoWaySettings) {
        this.twoWaySettings = twoWaySettings;
    }

    public IntegrationRequest withTwoWaySettings(TwoWaySettings twoWaySettings) {
        this.twoWaySettings = twoWaySettings;
        return this;
    }

    /**
     * 
     * @return
     *     The phoneNumberIds
     */
    public List<String> getPhoneNumberIds() {
        return phoneNumberIds;
    }

    /**
     * 
     * @param phoneNumberIds
     *     The phoneNumberIds
     */
    public void setPhoneNumberIds(List<String> phoneNumberIds) {
        this.phoneNumberIds = phoneNumberIds;
    }

    public IntegrationRequest withPhoneNumberIds(List<String> phoneNumberIds) {
        this.phoneNumberIds = phoneNumberIds;
        return this;
    }

    /**
     * 
     * @return
     *     The replyNumbers
     */
    public List<String> getReplyNumberIds() {
        return replyNumberIds;
    }

    /**
     * 
     * @param replyNumbers
     *     The replyNumbers
     */
    public void setReplyNumberIds(List<String> replyNumbers) {
        this.replyNumberIds = replyNumberIds;
    }

    public IntegrationRequest withReplyNumbersIds(List<String> replyNumberIds) {
        this.replyNumberIds = replyNumberIds;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntegrationName() {
        return integrationName;
    }

    public void setIntegrationName(String integrationName) {
        this.integrationName = integrationName;
    }
}
