
package com.touch.models.touch.tenant;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.touch.utils.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;



public class TenantRequest {

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
    @JsonProperty("tenantName")
    private String tenantName;
    @JsonProperty("botName")
    private String botName;
    @JsonProperty("category")
    private String category;
    @JsonProperty("tenantTags")
    private List<String> tenantTags = null;
    @JsonProperty("sessionsCapacity")
    private Long sessionsCapacity;
    @JsonProperty("tenantFaqs")
    private List<TenantFaqRequest> tenantFaqs = null;
    @JsonProperty("tenantProperties")
    private List<TenantProperties> tenantProperties = null;
    @JsonProperty("tenantAddresses")
    private List<TenantAddress> tenantAddresses = null;
    @JsonProperty("tenantAgentTransferReason")
    private List<TenantAgentTransferReason> tenantAgentTransferReason = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public TenantRequest() {
        this.accountId = "2c9f830756e0e99f0156e13ed4ed003d";
        this.tenantOrgName = StringUtils.generateRandomString(10) + "test1";
        this.contactEmail = StringUtils.generateRandomString(10) + "@sink.sendgrid.net";
        this.state = "ACTIVE";
        this.description = StringUtils.generateRandomString(10);
        this.shortDescription = StringUtils.generateRandomString(10);
        this.tenantName = "Test"+StringUtils.generateRandomString(7);
        this.category = "Banking";
        this.tenantTags = new ArrayList<>();
        tenantTags.add("Test");
        this.sessionsCapacity = 5L;
        this.tenantAddresses = new ArrayList<>();
        this.tenantAddresses.add(new TenantAddress());
        this.tenantFaqs = new ArrayList<TenantFaqRequest>();
        tenantFaqs.add(new TenantFaqRequest());
        this.tenantProperties = new ArrayList<>();
        tenantProperties.add(new TenantProperties());
        this.botName = StringUtils.generateRandomString(10) + "test1";
        this.tenantAgentTransferReason =  new ArrayList<TenantAgentTransferReason>();
        tenantAgentTransferReason.add(new TenantAgentTransferReason());
    }

    /**
     *
     * @param tenantTags
     * @param accountId
     * @param tenantOrgName
     * @param contactEmail
     * @param state
     * @param tenantAddresses
     * @param botName
     * @param tenantName
     * @param sessionsCapacity
     * @param category
     * @param shortDescription
     * @param description
     * @param tenantProperties
     * @param tenantFaqs
     * @param tenantAgentTransferReason
     */
    public TenantRequest(String accountId, String tenantOrgName, String contactEmail, String state, String description, String shortDescription, String tenantName, String botName, String category, List<String> tenantTags, Long sessionsCapacity, List<TenantFaqRequest> tenantFaqs, List<TenantProperties> tenantProperties, List<TenantAddress> tenantAddresses, List<TenantAgentTransferReason> tenantAgentTransferReason) {
        super();
        this.accountId = accountId;
        this.tenantOrgName = tenantOrgName;
        this.contactEmail = contactEmail;
        this.state = state;
        this.description = description;
        this.shortDescription = shortDescription;
        this.tenantName = tenantName;
        this.botName = botName;
        this.category = category;
        this.tenantTags = tenantTags;
        this.sessionsCapacity = sessionsCapacity;
        this.tenantFaqs = tenantFaqs;
        this.tenantProperties = tenantProperties;
        this.tenantAddresses = tenantAddresses;
        this.tenantAgentTransferReason = tenantAgentTransferReason;
    }

    @JsonProperty("accountId")
    public String getAccountId() {
        return accountId;
    }

    @JsonProperty("accountId")
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public TenantRequest withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    @JsonProperty("tenantOrgName")
    public String getTenantOrgName() {
        return tenantOrgName;
    }

    @JsonProperty("tenantOrgName")
    public void setTenantOrgName(String tenantOrgName) {
        this.tenantOrgName = tenantOrgName;
    }

    public TenantRequest withTenantOrgName(String tenantOrgName) {
        this.tenantOrgName = tenantOrgName;
        return this;
    }

    @JsonProperty("contactEmail")
    public String getContactEmail() {
        return contactEmail;
    }

    @JsonProperty("contactEmail")
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public TenantRequest withContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public TenantRequest withState(String state) {
        this.state = state;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public TenantRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("shortDescription")
    public String getShortDescription() {
        return shortDescription;
    }

    @JsonProperty("shortDescription")
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public TenantRequest withShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    @JsonProperty("tenantName")
    public String getTenantName() {
        return tenantName;
    }

    @JsonProperty("tenantName")
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public TenantRequest withTenantName(String tenantName) {
        this.tenantName = tenantName;
        return this;
    }

    @JsonProperty("botName")
    public String getBotName() {
        return botName;
    }

    @JsonProperty("botName")
    public void setBotName(String botName) {
        this.botName = botName;
    }

    public TenantRequest withBotName(String botName) {
        this.botName = botName;
        return this;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    public TenantRequest withCategory(String category) {
        this.category = category;
        return this;
    }

    @JsonProperty("tenantTags")
    public List<String> getTenantTags() {
        return tenantTags;
    }

    @JsonProperty("tenantTags")
    public void setTenantTags(List<String> tenantTags) {
        this.tenantTags = tenantTags;
    }

    public TenantRequest withTenantTags(List<String> tenantTags) {
        this.tenantTags = tenantTags;
        return this;
    }

    @JsonProperty("sessionsCapacity")
    public Long getSessionsCapacity() {
        return sessionsCapacity;
    }

    @JsonProperty("sessionsCapacity")
    public void setSessionsCapacity(Long sessionsCapacity) {
        this.sessionsCapacity = sessionsCapacity;
    }

    public TenantRequest withSessionsCapacity(Long sessionsCapacity) {
        this.sessionsCapacity = sessionsCapacity;
        return this;
    }

    @JsonProperty("tenantFaqs")
    public List<TenantFaqRequest> getTenantFaqs() {
        return tenantFaqs;
    }

    @JsonProperty("tenantFaqs")
    public void setTenantFaqs(List<TenantFaqRequest> tenantFaqs) {
        this.tenantFaqs = tenantFaqs;
    }

    public TenantRequest withTenantFaqs(List<TenantFaqRequest> tenantFaqs) {
        this.tenantFaqs = tenantFaqs;
        return this;
    }

    @JsonProperty("tenantProperties")
    public List<TenantProperties> getTenantProperties() {
        return tenantProperties;
    }

    @JsonProperty("tenantProperties")
    public void setTenantProperties(List<TenantProperties> tenantProperties) {
        this.tenantProperties = tenantProperties;
    }

    public TenantRequest withTenantProperties(List<TenantProperties> tenantProperties) {
        this.tenantProperties = tenantProperties;
        return this;
    }

    @JsonProperty("tenantAddresses")
    public List<TenantAddress> getTenantAddresses() {
        return tenantAddresses;
    }

    @JsonProperty("tenantAddresses")
    public void setTenantAddresses(List<TenantAddress> tenantAddresses) {
        this.tenantAddresses = tenantAddresses;
    }

    public TenantRequest withTenantAddresses(List<TenantAddress> tenantAddresses) {
        this.tenantAddresses = tenantAddresses;
        return this;
    }

    @JsonProperty("tenantAgentTransferReason")
    public List<TenantAgentTransferReason> getTenantAgentTransferReason() {
        return tenantAgentTransferReason;
    }

    @JsonProperty("tenantAgentTransferReason")
    public void setTenantAgentTransferReason(List<TenantAgentTransferReason> tenantAgentTransferReason) {
        this.tenantAgentTransferReason = tenantAgentTransferReason;
    }

    public TenantRequest withTenantAgentTransferReason(List<TenantAgentTransferReason> tenantAgentTransferReason) {
        this.tenantAgentTransferReason = tenantAgentTransferReason;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(accountId).append(tenantOrgName).append(contactEmail).append(state).append(description).append(shortDescription).append(tenantName).append(botName).append(category).append(tenantTags).append(sessionsCapacity).append(tenantFaqs).append(tenantProperties).append(tenantAddresses).append(tenantAgentTransferReason).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TenantRequest) == false) {
            return false;
        }
        TenantRequest rhs = ((TenantRequest) other);
        return new EqualsBuilder().append(accountId, rhs.accountId).append(tenantOrgName, rhs.tenantOrgName).append(contactEmail, rhs.contactEmail).append(state, rhs.state).append(description, rhs.description).append(shortDescription, rhs.shortDescription).append(tenantName, rhs.tenantName).append(botName, rhs.botName).append(category, rhs.category).append(tenantTags, rhs.tenantTags).append(sessionsCapacity, rhs.sessionsCapacity).append(tenantFaqs, rhs.tenantFaqs).append(tenantProperties, rhs.tenantProperties).append(tenantAddresses, rhs.tenantAddresses).append(tenantAgentTransferReason, rhs.tenantAgentTransferReason).isEquals();
    }

}
