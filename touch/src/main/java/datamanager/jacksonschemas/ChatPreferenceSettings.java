package datamanager.jacksonschemas;

import com.fasterxml.jackson.annotation.*;
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
public class ChatPreferenceSettings {

    @JsonProperty("maxChatsPerAgent")
    private String maxChatsPerAgent = "50";
    @JsonProperty("autoTicketScheduling")
    private String autoTicketScheduling = "true";
    @JsonProperty("agentFeedback")
    private String agentFeedback = "true";
    @JsonProperty("tenantMode")
    private String tenantMode = "BOT";
    @JsonProperty("lastAgentMode")
    private String lastAgentMode = "true";
    @JsonProperty("routingType")
    private String routingType = "RANDOM";
    @JsonProperty("departmentPrimaryStatus")
    private String departmentPrimaryStatus = "true";
    @JsonProperty("chatTranscriptMode")
    private String chatTranscriptMode = "ALL";
    @JsonProperty("ticketTimeoutHours")
    private String ticketTimeoutHours = "120";
    @JsonProperty("agentInactivityTimeoutSec")
    private String agentInactivityTimeoutSec = "86400";
    @JsonProperty("attachmentLifeTimeDays")
    private String attachmentLifeTimeDays = "90";
    @JsonProperty("globalInactivityTimeoutSec")
    private String globalInactivityTimeoutSec = "86400";
    @JsonProperty("pendingChatAutoClosureTimeSec")
    private String pendingChatAutoClosureTimeSec = "86400";

    public void setFeatureStatus(String feature, String value) {
        switch (feature) {
            case "autoTicketScheduling":
                setAutoTicketScheduling(value);
                break;
            case "maxChatsPerAgent":
                setMaxChatsPerAgent(value);
                break;
            case "agentFeedback":
                setAgentFeedback(value);
                break;
            case "tenantMode":
                setTenantMode(value);
                break;
            case "lastAgentMode":
                setLastAgentMode(value);
                break;
            case "routingType":
                setRoutingType(value);
                break;
            case "departmentPrimaryStatus":
                setDepartmentPrimaryStatus(value);
                break;
            case "chatTranscriptMode":
                setChatTranscriptMode(value);
                break;
            case "ticketTimeoutHours":
                setTicketTimeoutHours(value);
                break;
            case "agentInactivityTimeoutSec":
                setAgentInactivityTimeoutSec(value);
                break;
            case "attachmentLifeTimeDays":
                setAttachmentLifeTimeDays(value);
                break;
            case "globalInactivityTimeoutSec":
                setGlobalInactivityTimeoutSec(value);
                break;
            case "pendingChatAutoClosureTimeSec":
                setPendingChatAutoClosureTimeSec(value);
                break;
            default:
                throw new NoSuchElementException("Json element: '" + feature + "' wasn't found");
        }
    }

    @Override
    public String toString() {
        return "{\n" +
                "            \"maxChatsPerAgent\": "+maxChatsPerAgent+",\n" +
                "                \"autoTicketScheduling\": "+autoTicketScheduling+",\n" +
                "                \"agentFeedback\": "+agentFeedback+",\n" +
                "                \"tenantMode\": \""+tenantMode+"\",\n" +
                "                \"lastAgentMode\": "+lastAgentMode+",\n" +
                "                \"routingType\": \""+routingType+"\",\n" +
                "                \"departmentPrimaryStatus\": "+departmentPrimaryStatus+",\n" +
                "                \"chatTranscriptMode\": \""+chatTranscriptMode+"\",\n" +
                "                \"ticketTimeoutHours\": "+ticketTimeoutHours+",\n" +
                "                \"agentInactivityTimeoutSec\": "+agentInactivityTimeoutSec+",\n" +
                "                \"attachmentLifeTimeDays\": "+attachmentLifeTimeDays+",\n" +
                "                \"globalInactivityTimeoutSec\": "+globalInactivityTimeoutSec+",\n" +
                "                \"pendingChatAutoClosureTimeSec\": "+pendingChatAutoClosureTimeSec+"\n" +
                "        }"
                ;
    }
}