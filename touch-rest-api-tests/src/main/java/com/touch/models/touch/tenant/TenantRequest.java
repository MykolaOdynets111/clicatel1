package com.touch.models.touch.tenant;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.touch.utils.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;



public class TenantRequest {
    public enum StateEnum {
        @SerializedName("ACTIVE")
        ACTIVE("ACTIVE"),

        @SerializedName("INACTIVE")
        INACTIVE("INACTIVE");

        private String value;

        StateEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
    @JsonProperty("accountId")
    private String accountId;
    @JsonProperty("tenantOrgName")
    private String tenantOrgName;
    @JsonProperty("contactEmail")
    private String contactEmail;
    @JsonProperty("state")
    private String state;
    @JsonProperty("description")
    private String description;
    @JsonProperty("shortDescription")
    private String shortDescription;
    @JsonProperty("tenantJName")
    private String tenantJName;
    @JsonProperty("tenantJBotName")
    private String tenantJBotName;
    @JsonProperty("category")
    private String category;
    @JsonProperty("tenantTags")
    private List<String> tenantTags = new ArrayList<String>();
    @JsonProperty("sessionsCapacity")
    private int sessionsCapacity;
    @JsonProperty("tenantFaqs")
    private List<TenantFaq> tenantFaqs = new ArrayList<TenantFaq>();
    @JsonProperty("tenantColours")
    private List<TenantColour> tenantColours = new ArrayList<TenantColour>();
    @JsonProperty("tenantAddresses")
    private List<TenantAddress> tenantAddresses = new ArrayList<>();
    @JsonProperty("mc2AccountRequest")
    private Mc2AccountRequest mc2AccountRequest;

    /**
     * No args constructor for use in serialization
     */
    public TenantRequest() {
        this.accountId = "2c9f830756e0e99f0156e13ed4ed003d";
        this.tenantOrgName = StringUtils.generateRandomString(10) + "Test1";
        this.contactEmail = StringUtils.generateRandomString(10) + "@sink.sendgrid.net";
        this.state = "ACTIVE";
        this.description = StringUtils.generateRandomString(10);
        this.shortDescription = StringUtils.generateRandomString(10);
        this.tenantJName = "Test"+StringUtils.generateRandomString(7);
        this.tenantJBotName = "Test"+StringUtils.generateRandomString(7);
        this.category = "Banking";
        this.tenantTags = new ArrayList<>();
        this.sessionsCapacity = 5;
        this.tenantAddresses = new ArrayList<>();
        tenantAddresses.add(new TenantAddress());
        this.tenantFaqs = new ArrayList<>();
        this.tenantColours = new ArrayList<>();
        tenantColours.add(new TenantColour());
        this.mc2AccountRequest = new Mc2AccountRequest();
    }

    public TenantRequest(String accountId, String tenantOrgName, String contactEmail, String state, String description, String shortDescription, String tenantJName, String tenantJBotName, String category, List<String> tenantTags, int sessionsCapacity, List<TenantFaq> tenantFaqs, List<TenantColour> tenantColours, List<TenantAddress> tenantAddresses, Mc2AccountRequest mc2AccountRequest) {
        this.accountId = accountId;
        this.tenantOrgName = tenantOrgName;
        this.contactEmail = contactEmail;
        this.state = state;
        this.description = description;
        this.shortDescription = shortDescription;
        this.tenantJName = tenantJName;
        this.tenantJBotName = tenantJBotName;
        this.category = category;
        this.tenantTags = tenantTags;
        this.sessionsCapacity = sessionsCapacity;
        this.tenantFaqs = tenantFaqs;
        this.tenantColours = tenantColours;
        this.tenantAddresses = tenantAddresses;
        this.mc2AccountRequest = mc2AccountRequest;
    }

    /**
     * @return The accountId
     */
    @JsonProperty("accountId")
    public String getAccountId() {
        return accountId;
    }

    /**
     * @param accountId The accountId
     */
    @JsonProperty("accountId")
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public TenantRequest withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * @return The tenantOrgName
     */
    @JsonProperty("tenantOrgName")
    public String getTenantOrgName() {
        return tenantOrgName;
    }

    /**
     * @param tenantOrgName The tenantOrgName
     */
    @JsonProperty("tenantOrgName")
    public void setTenantOrgName(String tenantOrgName) {
        this.tenantOrgName = tenantOrgName;
    }

    public TenantRequest withTenantOrgName(String tenantOrgName) {
        this.tenantOrgName = tenantOrgName;
        return this;
    }

    /**
     * @return The contactEmail
     */
    @JsonProperty("contactEmail")
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail The contactEmail
     */
    @JsonProperty("contactEmail")
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public TenantRequest withContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    /**
     * @return The state
     */
    @JsonProperty("state")
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public TenantRequest withState(String state) {
        this.state = state;
        return this;
    }

    /**
     * @return The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public TenantRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return The shortDescription
     */
    @JsonProperty("shortDescription")
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * @param shortDescription The shortDescription
     */
    @JsonProperty("shortDescription")
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public TenantRequest withShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    /**
     * @return The tenantJName
     */
    @JsonProperty("tenantJName")
    public String getTenantJName() {
        return tenantJName;
    }

    /**
     * @param tenantJName The tenantJName
     */
    @JsonProperty("tenantJName")
    public void setTenantJName(String tenantJName) {
        this.tenantJName = tenantJName;
    }

    public TenantRequest withTenantJName(String tenantJName) {
        this.tenantJName = tenantJName;
        return this;
    }

    /**
     * @return The tenantJBotName
     */
    @JsonProperty("tenantJBotName")
    public String getTenantJBotName() {
        return tenantJBotName;
    }

    /**
     * @param tenantJBotName The tenantJBotName
     */
    @JsonProperty("tenantJBotName")
    public void setTenantJBotName(String tenantJBotName) {
        this.tenantJBotName = tenantJBotName;
    }

    public TenantRequest withTenantJBotName(String tenantJBotName) {
        this.tenantJBotName = tenantJBotName;
        return this;
    }

    /**
     * @return The category
     */
    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    public TenantRequest withCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * @return The tenantTags
     */
    @JsonProperty("tenantTags")
    public List<String> getTenantTags() {
        return tenantTags;
    }

    /**
     * @param tenantTags The tenantTags
     */
    @JsonProperty("tenantTags")
    public void setTenantTags(List<String> tenantTags) {
        this.tenantTags = tenantTags;
    }

    public TenantRequest withTenantTags(List<String> tenantTags) {
        this.tenantTags = tenantTags;
        return this;
    }

    /**
     * @return The sessionsCapacity
     */
    @JsonProperty("sessionsCapacity")
    public int getSessionsCapacity() {
        return sessionsCapacity;
    }

    /**
     * @param sessionsCapacity The sessionsCapacity
     */
    @JsonProperty("sessionsCapacity")
    public void setSessionsCapacity(int sessionsCapacity) {
        this.sessionsCapacity = sessionsCapacity;
    }

    public TenantRequest withSessionsCapacity(int sessionsCapacity) {
        this.sessionsCapacity = sessionsCapacity;
        return this;
    }

    /**
     * @return The tenantFaqs
     */
    @JsonProperty("tenantFaqs")
    public List<TenantFaq> getTenantFaqs() {
        return tenantFaqs;
    }

    /**
     * @param tenantFaqs The tenantFaqs
     */
    @JsonProperty("tenantFaqs")
    public void setTenantFaqs(List<TenantFaq> tenantFaqs) {
        this.tenantFaqs = tenantFaqs;
    }

    public TenantRequest withTenantFaqs(List<TenantFaq> tenantFaqs) {
        this.tenantFaqs = tenantFaqs;
        return this;
    }

    /**
     * @return The tenantColours
     */
    @JsonProperty("tenantColours")
    public List<TenantColour> getTenantColours() {
        return tenantColours;
    }

    /**
     * @param tenantColours The tenantColours
     */
    @JsonProperty("tenantColours")
    public void setTenantColours(List<TenantColour> tenantColours) {
        this.tenantColours = tenantColours;
    }

    public TenantRequest withTenantColours(List<TenantColour> tenantColours) {
        this.tenantColours = tenantColours;
        return this;
    }

    /**
     * @return The tenantAddresses
     */
    @JsonProperty("tenantAddresses")
    public List<TenantAddress> getTenantAddresses() {
        return tenantAddresses;
    }

    /**
     * @param tenantAddresses The tenantAddresses
     */
    @JsonProperty("tenantAddresses")
    public void setTenantAddresses(List<TenantAddress> tenantAddresses) {
        this.tenantAddresses = tenantAddresses;
    }

    public TenantRequest withTenantAddresses(List<TenantAddress> tenantAddresses) {
        this.tenantAddresses = tenantAddresses;
        return this;
    }

    /**
     * @return The mc2AccountRequest
     */
    @JsonProperty("mc2AccountRequest")
    public Mc2AccountRequest getMc2AccountRequest() {
        return mc2AccountRequest;
    }

    /**
     * @param mc2AccountRequest The mc2AccountRequest
     */
    @JsonProperty("mc2AccountRequest")
    public void setMc2AccountRequest(Mc2AccountRequest mc2AccountRequest) {
        this.mc2AccountRequest = mc2AccountRequest;
    }

    public TenantRequest withMc2AccountRequest(Mc2AccountRequest mc2AccountRequest) {
        this.mc2AccountRequest = mc2AccountRequest;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(accountId).append(tenantOrgName).append(contactEmail).append(state).append(description).append(shortDescription).append(tenantJName).append(tenantJBotName).append(category).append(tenantTags).append(sessionsCapacity).append(tenantFaqs).append(tenantColours).append(tenantAddresses).append(mc2AccountRequest).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TenantRequest||other instanceof TenantResponseV4) == false) {
            return false;
        }
        if (other instanceof TenantResponseV4){
            TenantResponseV4 rhs = ((TenantResponseV4) other);
            return new EqualsBuilder().append(accountId, rhs.getAccountId()).append(tenantOrgName, rhs.getTenantOrgName()).
                            append(contactEmail, rhs.getContactEmail()).append(state, rhs.getState().toString()).append(description, rhs.getDescription()).
                            append(shortDescription, rhs.getShortDescription()).append(tenantJName, rhs.getTenantJid().split("@")[0]).append(tenantJBotName, rhs.getTenantBotJid().split("@")[0]).
                            append(category, rhs.getCategory()).append(tenantTags, rhs.getTenantTags()).append(tenantFaqs, rhs.getTenantFaqs()).
                            append(tenantColours, rhs.getTenantColours()).isEquals();

        }
        TenantRequest rhs = ((TenantRequest) other);
        return new EqualsBuilder().append(accountId, rhs.accountId).append(tenantOrgName, rhs.tenantOrgName).append(contactEmail, rhs.contactEmail).append(state, rhs.state).append(description, rhs.description).append(shortDescription, rhs.shortDescription).append(tenantJName, rhs.tenantJName).append(tenantJBotName, rhs.tenantJBotName).append(category, rhs.category).append(tenantTags, rhs.tenantTags).append(sessionsCapacity, rhs.sessionsCapacity).append(tenantFaqs, rhs.tenantFaqs).append(tenantColours, rhs.tenantColours).append(tenantAddresses, rhs.tenantAddresses).append(mc2AccountRequest, rhs.mc2AccountRequest).isEquals();
    }

}
