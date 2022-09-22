package datamanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@ToString
@AllArgsConstructor
public enum TenantInfoValues {

    MAX_CHATS_PER_AGENT("maxChatsPerAgent"),
    AUTO_TICKET_SCHEDULING("autoTicketScheduling"),
    AGENT_FEEDBACK("agentFeedback"),
    TENANT_MODE("tenantMode"),
    LAST_AGENT_MODE("lastAgentMode"),
    ROUTING_TYPE("routingType"),
    DEPARTMENT_PRIMARY_STATUS("departmentPrimaryStatus"),
    CHAT_TRANSCRIPT_MODE("chatTranscriptMode"),
    TICKET_TIMEOUT_HOURS("ticketTimeoutHours"),
    AGENT_INACTIVITY_SEC("agentInactivityTimeoutSec"),
    ATTACHMENT_LIFETIME_DAYS("attachmentLifeTimeDays"),
    GLOBAL_INACTIVITY_TIMEOUT_SEC("globalInactivityTimeoutSec"),
    PENDING_CHAT_AUTO_CLOSURE_TIME("pendingChatAutoClosureTimeSec");

    private final String value;

    public static String getAllBooleanValues(String title){
        return Arrays.stream(TenantInfoValues.values()).filter(m -> m.getValue().equals(title))
                .findFirst().orElseThrow(NoSuchElementException::new).getValue();
    }
}