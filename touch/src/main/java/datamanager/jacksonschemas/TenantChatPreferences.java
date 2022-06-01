package datamanager.jacksonschemas;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "maxChatsPerAgent",
        "autoTicketScheduling",
        "agentFeedback",
        "tenantMode",
        "lastAgentMode",
        "routingType",
        "departmentPrimaryStatus",
        "chatTranscriptMode",
        "ticketTimeoutHours",
        "agentInactivityTimeoutSec",
        "attachmentLifeTimeDays",
        "globalInactivityTimeoutSec",
        "pendingChatAutoClosureTimeSec"
})
@Generated("jsonschema2pojo")
public class TenantChatPreferences {

    @JsonProperty("maxChatsPerAgent")
    private Integer maxChatsPerAgent;
    @JsonProperty("autoTicketScheduling")
    private Boolean autoTicketScheduling;
    @JsonProperty("agentFeedback")
    private Boolean agentFeedback;
    @JsonProperty("tenantMode")
    private String tenantMode;
    @JsonProperty("lastAgentMode")
    private Boolean lastAgentMode;
    @JsonProperty("routingType")
    private String routingType;
    @JsonProperty("departmentPrimaryStatus")
    private Boolean departmentPrimaryStatus;
    @JsonProperty("chatTranscriptMode")
    private String chatTranscriptMode;
    @JsonProperty("ticketTimeoutHours")
    private Integer ticketTimeoutHours;
    @JsonProperty("agentInactivityTimeoutSec")
    private Integer agentInactivityTimeoutSec;
    @JsonProperty("attachmentLifeTimeDays")
    private Integer attachmentLifeTimeDays;
    @JsonProperty("globalInactivityTimeoutSec")
    private Integer globalInactivityTimeoutSec;
    @JsonProperty("pendingChatAutoClosureTimeSec")
    private Integer pendingChatAutoClosureTimeSec;

    @JsonProperty("maxChatsPerAgent")
    public Integer getMaxChatsPerAgent() {
        return maxChatsPerAgent;
    }

    @JsonProperty("maxChatsPerAgent")
    public void setMaxChatsPerAgent(Integer maxChatsPerAgent) {
        this.maxChatsPerAgent = maxChatsPerAgent;
    }

    @JsonProperty("autoTicketScheduling")
    public Boolean getAutoTicketScheduling() {
        return autoTicketScheduling;
    }

    @JsonProperty("autoTicketScheduling")
    public void setAutoTicketScheduling(Boolean autoTicketScheduling) {
        this.autoTicketScheduling = autoTicketScheduling;
    }

    @JsonProperty("agentFeedback")
    public Boolean getAgentFeedback() {
        return agentFeedback;
    }

    @JsonProperty("agentFeedback")
    public void setAgentFeedback(Boolean agentFeedback) {
        this.agentFeedback = agentFeedback;
    }

    @JsonProperty("tenantMode")
    public String getTenantMode() {
        return tenantMode;
    }

    @JsonProperty("tenantMode")
    public void setTenantMode(String tenantMode) {
        this.tenantMode = tenantMode;
    }

    @JsonProperty("lastAgentMode")
    public Boolean getLastAgentMode() {
        return lastAgentMode;
    }

    @JsonProperty("lastAgentMode")
    public void setLastAgentMode(Boolean lastAgentMode) {
        this.lastAgentMode = lastAgentMode;
    }

    @JsonProperty("routingType")
    public String getRoutingType() {
        return routingType;
    }

    @JsonProperty("routingType")
    public void setRoutingType(String routingType) {
        this.routingType = routingType;
    }

    @JsonProperty("departmentPrimaryStatus")
    public Boolean getDepartmentPrimaryStatus() {
        return departmentPrimaryStatus;
    }

    @JsonProperty("departmentPrimaryStatus")
    public void setDepartmentPrimaryStatus(Boolean departmentPrimaryStatus) {
        this.departmentPrimaryStatus = departmentPrimaryStatus;
    }

    @JsonProperty("chatTranscriptMode")
    public String getChatTranscriptMode() {
        return chatTranscriptMode;
    }

    @JsonProperty("chatTranscriptMode")
    public void setChatTranscriptMode(String chatTranscriptMode) {
        this.chatTranscriptMode = chatTranscriptMode;
    }

    @JsonProperty("ticketTimeoutHours")
    public Integer getTicketTimeoutHours() {
        return ticketTimeoutHours;
    }

    @JsonProperty("ticketTimeoutHours")
    public void setTicketTimeoutHours(Integer ticketTimeoutHours) {
        this.ticketTimeoutHours = ticketTimeoutHours;
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

    @JsonProperty("globalInactivityTimeoutSec")
    public Integer getGlobalInactivityTimeoutSec() {
        return globalInactivityTimeoutSec;
    }

    @JsonProperty("globalInactivityTimeoutSec")
    public void setGlobalInactivityTimeoutSec(Integer globalInactivityTimeoutSec) {
        this.globalInactivityTimeoutSec = globalInactivityTimeoutSec;
    }

    @JsonProperty("pendingChatAutoClosureTimeSec")
    public Integer getPendingChatAutoClosureTimeSec() {
        return pendingChatAutoClosureTimeSec;
    }

    @JsonProperty("pendingChatAutoClosureTimeSec")
    public void setPendingChatAutoClosureTimeSec(Integer pendingChatAutoClosureTimeSec) {
        this.pendingChatAutoClosureTimeSec = pendingChatAutoClosureTimeSec;
    }

}