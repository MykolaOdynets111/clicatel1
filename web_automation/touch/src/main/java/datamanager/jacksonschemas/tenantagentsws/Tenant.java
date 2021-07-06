package datamanager.jacksonschemas.tenantagentsws;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.javafaker.Faker;
import datamanager.Agents;
import datamanager.Tenants;
import sun.management.resources.agent;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "agentFeedback",
        "agentInactivityTimeoutSec",
        "attachmentLifeTimeDays",
        "autoTicketScheduling",
        "category",
        "chatTranscriptMode",
        "city",
        "contactEmail",
        "country",
        "departmentPrimaryStatus",
        "globalInactivityTimeoutSec",
        "lastAgentMode",
        "maxChatsPerAgent",
        "maxOnlineAgentLimit",
        "mc2AccountId",
        "orgName",
        "pendingChatAutoClosureTimeSec",
        "routingType",
        "supportEmail",
        "tenantMode",
        "tenantName",
        "ticketTimeoutHours",
        "timezone",
        "transferTimeoutSec"
})
@Generated("jsonschema2pojo")
public class Tenant {

    @JsonProperty("agentFeedback")
    private Boolean agentFeedback;
    @JsonProperty("agentInactivityTimeoutSec")
    private Integer agentInactivityTimeoutSec;
    @JsonProperty("attachmentLifeTimeDays")
    private Integer attachmentLifeTimeDays;
    @JsonProperty("autoTicketScheduling")
    private Boolean autoTicketScheduling;
    @JsonProperty("category")
    private String category;
    @JsonProperty("chatTranscriptMode")
    private String chatTranscriptMode;
    @JsonProperty("city")
    private String city;
    @JsonProperty("contactEmail")
    private String contactEmail;
    @JsonProperty("country")
    private String country;
    @JsonProperty("departmentPrimaryStatus")
    private Boolean departmentPrimaryStatus;
    @JsonProperty("globalInactivityTimeoutSec")
    private Integer globalInactivityTimeoutSec;
    @JsonProperty("lastAgentMode")
    private Boolean lastAgentMode;
    @JsonProperty("maxChatsPerAgent")
    private Integer maxChatsPerAgent;
    @JsonProperty("maxOnlineAgentLimit")
    private Integer maxOnlineAgentLimit;
    @JsonProperty("mc2AccountId")
    private String mc2AccountId;
    @JsonProperty("orgName")
    private String orgName;
    @JsonProperty("pendingChatAutoClosureTimeSec")
    private Integer pendingChatAutoClosureTimeSec;
    @JsonProperty("routingType")
    private String routingType;
    @JsonProperty("supportEmail")
    private String supportEmail;
    @JsonProperty("tenantMode")
    private String tenantMode;
    @JsonProperty("tenantName")
    private String tenantName;
    @JsonProperty("ticketTimeoutHours")
    private Integer ticketTimeoutHours;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("transferTimeoutSec")
    private Integer transferTimeoutSec;

    public Tenant (String tenantorgName, String tenantName, String mail){
        Faker fake = new Faker();
        setOrgName(tenantorgName);
        setTenantName(tenantName);
        setContactEmail(mail);
        setCategory("Automotive");
        setMaxOnlineAgentLimit(100);
        setMc2AccountId(fake.numerify("aqaId##########"));
        setTenantMode("BOT");
        setRoutingType("RANDOM");
        setPendingChatAutoClosureTimeSec(3600);
        setAttachmentLifeTimeDays(1);
        setAgentInactivityTimeoutSec(3600);
        setGlobalInactivityTimeoutSec(3600);
    }

    @JsonProperty("agentFeedback")
    public Boolean getAgentFeedback() {
        return agentFeedback;
    }

    @JsonProperty("agentFeedback")
    public void setAgentFeedback(Boolean agentFeedback) {
        this.agentFeedback = agentFeedback;
    }

    @JsonProperty("agentInactivityTimeoutSec")
    public Integer getAgentInactivityTimeoutSec() {
        return agentInactivityTimeoutSec;
    }

    @JsonProperty("agentInactivityTimeoutSec")
    public void setAgentInactivityTimeoutSec(Integer agentInactivityTimeoutSec) {
        this.agentInactivityTimeoutSec = agentInactivityTimeoutSec;
    }

    @JsonProperty("attachmentLifeTimeDays")
    public Integer getAttachmentLifeTimeDays() {
        return attachmentLifeTimeDays;
    }

    @JsonProperty("attachmentLifeTimeDays")
    public void setAttachmentLifeTimeDays(Integer attachmentLifeTimeDays) {
        this.attachmentLifeTimeDays = attachmentLifeTimeDays;
    }

    @JsonProperty("autoTicketScheduling")
    public Boolean getAutoTicketScheduling() {
        return autoTicketScheduling;
    }

    @JsonProperty("autoTicketScheduling")
    public void setAutoTicketScheduling(Boolean autoTicketScheduling) {
        this.autoTicketScheduling = autoTicketScheduling;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("chatTranscriptMode")
    public String getChatTranscriptMode() {
        return chatTranscriptMode;
    }

    @JsonProperty("chatTranscriptMode")
    public void setChatTranscriptMode(String chatTranscriptMode) {
        this.chatTranscriptMode = chatTranscriptMode;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("contactEmail")
    public String getContactEmail() {
        return contactEmail;
    }

    @JsonProperty("contactEmail")
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("departmentPrimaryStatus")
    public Boolean getDepartmentPrimaryStatus() {
        return departmentPrimaryStatus;
    }

    @JsonProperty("departmentPrimaryStatus")
    public void setDepartmentPrimaryStatus(Boolean departmentPrimaryStatus) {
        this.departmentPrimaryStatus = departmentPrimaryStatus;
    }

    @JsonProperty("globalInactivityTimeoutSec")
    public Integer getGlobalInactivityTimeoutSec() {
        return globalInactivityTimeoutSec;
    }

    @JsonProperty("globalInactivityTimeoutSec")
    public void setGlobalInactivityTimeoutSec(Integer globalInactivityTimeoutSec) {
        this.globalInactivityTimeoutSec = globalInactivityTimeoutSec;
    }

    @JsonProperty("lastAgentMode")
    public Boolean getLastAgentMode() {
        return lastAgentMode;
    }

    @JsonProperty("lastAgentMode")
    public void setLastAgentMode(Boolean lastAgentMode) {
        this.lastAgentMode = lastAgentMode;
    }

    @JsonProperty("maxChatsPerAgent")
    public Integer getMaxChatsPerAgent() {
        return maxChatsPerAgent;
    }

    @JsonProperty("maxChatsPerAgent")
    public void setMaxChatsPerAgent(Integer maxChatsPerAgent) {
        this.maxChatsPerAgent = maxChatsPerAgent;
    }

    @JsonProperty("maxOnlineAgentLimit")
    public Integer getMaxOnlineAgentLimit() {
        return maxOnlineAgentLimit;
    }

    @JsonProperty("maxOnlineAgentLimit")
    public void setMaxOnlineAgentLimit(Integer maxOnlineAgentLimit) {
        this.maxOnlineAgentLimit = maxOnlineAgentLimit;
    }

    @JsonProperty("mc2AccountId")
    public String getMc2AccountId() {
        return mc2AccountId;
    }

    @JsonProperty("mc2AccountId")
    public void setMc2AccountId(String mc2AccountId) {
        this.mc2AccountId = mc2AccountId;
    }

    @JsonProperty("orgName")
    public String getOrgName() {
        return orgName;
    }

    @JsonProperty("orgName")
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @JsonProperty("pendingChatAutoClosureTimeSec")
    public Integer getPendingChatAutoClosureTimeSec() {
        return pendingChatAutoClosureTimeSec;
    }

    @JsonProperty("pendingChatAutoClosureTimeSec")
    public void setPendingChatAutoClosureTimeSec(Integer pendingChatAutoClosureTimeSec) {
        this.pendingChatAutoClosureTimeSec = pendingChatAutoClosureTimeSec;
    }

    @JsonProperty("routingType")
    public String getRoutingType() {
        return routingType;
    }

    @JsonProperty("routingType")
    public void setRoutingType(String routingType) {
        this.routingType = routingType;
    }

    @JsonProperty("supportEmail")
    public String getSupportEmail() {
        return supportEmail;
    }

    @JsonProperty("supportEmail")
    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    @JsonProperty("tenantMode")
    public String getTenantMode() {
        return tenantMode;
    }

    @JsonProperty("tenantMode")
    public void setTenantMode(String tenantMode) {
        this.tenantMode = tenantMode;
    }

    @JsonProperty("tenantName")
    public String getTenantName() {
        return tenantName;
    }

    @JsonProperty("tenantName")
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    @JsonProperty("ticketTimeoutHours")
    public Integer getTicketTimeoutHours() {
        return ticketTimeoutHours;
    }

    @JsonProperty("ticketTimeoutHours")
    public void setTicketTimeoutHours(Integer ticketTimeoutHours) {
        this.ticketTimeoutHours = ticketTimeoutHours;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonProperty("transferTimeoutSec")
    public Integer getTransferTimeoutSec() {
        return transferTimeoutSec;
    }

    @JsonProperty("transferTimeoutSec")
    public void setTransferTimeoutSec(Integer transferTimeoutSec) {
        this.transferTimeoutSec = transferTimeoutSec;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "agentFeedback=" + agentFeedback +
                ", agentInactivityTimeoutSec=" + agentInactivityTimeoutSec +
                ", attachmentLifeTimeDays=" + attachmentLifeTimeDays +
                ", autoTicketScheduling=" + autoTicketScheduling +
                ", category='" + category + '\'' +
                ", chatTranscriptMode='" + chatTranscriptMode + '\'' +
                ", city='" + city + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", country='" + country + '\'' +
                ", departmentPrimaryStatus=" + departmentPrimaryStatus +
                ", globalInactivityTimeoutSec=" + globalInactivityTimeoutSec +
                ", lastAgentMode=" + lastAgentMode +
                ", maxChatsPerAgent=" + maxChatsPerAgent +
                ", maxOnlineAgentLimit=" + maxOnlineAgentLimit +
                ", mc2AccountId='" + mc2AccountId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", pendingChatAutoClosureTimeSec=" + pendingChatAutoClosureTimeSec +
                ", routingType='" + routingType + '\'' +
                ", supportEmail='" + supportEmail + '\'' +
                ", tenantMode='" + tenantMode + '\'' +
                ", tenantName='" + tenantName + '\'' +
                ", ticketTimeoutHours=" + ticketTimeoutHours +
                ", timezone='" + timezone + '\'' +
                ", transferTimeoutSec=" + transferTimeoutSec +
                '}';
    }
}