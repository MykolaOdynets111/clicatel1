package datamanager.jacksonschemas;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "createdDate",
        "modifiedDate",
        "clientProfileId",
        "conversationId",
        "sessionId",
        "link",
        "ticketNumber",
        "agentNote"
})
public class CRMTicket {

    @JsonProperty("id")
    private String id;
    @JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("modifiedDate")
    private String modifiedDate;
    @JsonProperty("clientProfileId")
    private String clientProfileId;
    @JsonProperty("conversationId")
    private String conversationId;
    @JsonProperty("sessionId")
    private String sessionId;
    @JsonProperty("link")
    private String link;
    @JsonProperty("ticketNumber")
    private String ticketNumber;
    @JsonProperty("agentNote")
    private String agentNote;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("createdDate")
    public String getCreatedDate() {
        return createdDate;
    }

    @JsonProperty("createdDate")
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @JsonProperty("modifiedDate")
    public String getModifiedDate() {
        return modifiedDate;
    }

    @JsonProperty("modifiedDate")
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @JsonProperty("clientProfileId")
    public String getClientProfileId() {
        return clientProfileId;
    }

    @JsonProperty("clientProfileId")
    public void setClientProfileId(String clientProfileId) {
        this.clientProfileId = clientProfileId;
    }

    @JsonProperty("conversationId")
    public String getConversationId() {
        return conversationId;
    }

    @JsonProperty("conversationId")
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    @JsonProperty("sessionId")
    public String getSessionId() {
        return sessionId;
    }

    @JsonProperty("sessionId")
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    @JsonProperty("ticketNumber")
    public String getTicketNumber() {
        return ticketNumber;
    }

    @JsonProperty("ticketNumber")
    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    @JsonProperty("agentNote")
    public String getAgentNote() {
        return agentNote;
    }

    @JsonProperty("agentNote")
    public void setAgentNote(String agentNote) {
        this.agentNote = agentNote;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "CRMTicket{" +
                "id='" + id + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", modifiedDate='" + modifiedDate + '\'' +
                ", clientProfileId='" + clientProfileId + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", link='" + link + '\'' +
                ", ticketNumber='" + ticketNumber + '\'' +
                ", agentNote='" + agentNote + '\'' +
                "}\n\n";
    }
}