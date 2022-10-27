package datamanager.jacksonschemas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.NoSuchElementException;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantChatPreferences {

    @JsonProperty("maxChatsPerAgent")
    private int maxChatsPerAgent = 50;
    @JsonProperty("autoTicketScheduling")
    private boolean autoTicketScheduling = true;
    @JsonProperty("agentFeedback")
    private String agentFeedback = "true";
    @JsonProperty("tenantMode")
    private String tenantMode = "BOT";
    @JsonProperty("lastAgentMode")
    private boolean lastAgentMode = true;
    @JsonProperty("routingType")
    private String routingType = "RANDOM";
    @JsonProperty("departmentPrimaryStatus")
    private boolean departmentPrimaryStatus = false;
    @JsonProperty("chatTranscriptMode")
    private String chatTranscriptMode = "ALL";
    @JsonProperty("ticketTimeoutHours")
    private int ticketTimeoutHours = 120;
    @JsonProperty("agentInactivityTimeoutSec")
    private int agentInactivityTimeoutSec = 86400;
    @JsonProperty("attachmentLifeTimeDays")
    private int attachmentLifeTimeDays = 90;
    @JsonProperty("globalInactivityTimeoutSec")
    private int globalInactivityTimeoutSec = 86400;
    @JsonProperty("pendingChatAutoClosureTimeSec")
    private int pendingChatAutoClosureTimeSec = 86400;

    public static TenantChatPreferences getDefaultTenantChatPreferences(){
        return new TenantChatPreferences();
    }

    public void setValueForChatPreferencesParameter(String feature, String value) {
        switch (feature) {
            case "autoTicketScheduling":
                setAutoTicketScheduling(Boolean.getBoolean(value));
                break;
            case "maxChatsPerAgent":
                setMaxChatsPerAgent(Integer.parseInt(value));
                break;
            case "agentFeedback":
                setAgentFeedback(value);
                break;
            case "tenantMode":
                setTenantMode(value);
                break;
            case "lastAgentMode":
                setLastAgentMode(Boolean.getBoolean(value));
                break;
            case "routingType":
                setRoutingType(value);
                break;
            case "departmentPrimaryStatus":
                setDepartmentPrimaryStatus(Boolean.getBoolean(value));
                break;
            case "chatTranscriptMode":
                setChatTranscriptMode(value);
                break;
            case "ticketTimeoutHours":
                setTicketTimeoutHours(Integer.getInteger(value));
                break;
            case "agentInactivityTimeoutSec":
                setAgentInactivityTimeoutSec(Integer.getInteger(value));
                break;
            case "attachmentLifeTimeDays":
                setAttachmentLifeTimeDays(Integer.getInteger(value));
                break;
            case "globalInactivityTimeoutSec":
                setGlobalInactivityTimeoutSec(Integer.getInteger(value));
                break;
            case "pendingChatAutoClosureTimeSec":
                setPendingChatAutoClosureTimeSec(Integer.getInteger(value));
                break;
            default:
                throw new NoSuchElementException("Json element: '" + feature + "' wasn't found");
        }
    }
}