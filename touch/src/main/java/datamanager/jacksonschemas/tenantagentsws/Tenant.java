package datamanager.jacksonschemas.tenantagentsws;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public Tenant(String tenantOrgName, String tenantName, String mail) {
        Faker fake = new Faker();
        setOrgName(tenantOrgName);
        setTenantName(tenantName);
        setContactEmail(mail);
        setCategory("Automotive");
        setMaxOnlineAgentLimit(100);
        setMc2AccountId(fake.numerify("ff#######fba###d###fc##fe3a#####"));
        setTenantMode("AGENT");
        setRoutingType("RANDOM");
        setPendingChatAutoClosureTimeSec(28800);
        setAttachmentLifeTimeDays(1);
        setAgentInactivityTimeoutSec(1200);
        setGlobalInactivityTimeoutSec(3600);
        setMaxChatsPerAgent(15);
    }
}