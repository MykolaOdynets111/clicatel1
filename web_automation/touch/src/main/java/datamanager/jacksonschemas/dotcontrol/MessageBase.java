
package datamanager.jacksonschemas.dotcontrol;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "apiToken",
        "clientId",
        "referenceId",
        "conversationId",
        "messageType",
        "message"
})
public class MessageBase {

    @JsonProperty("apiToken")
    private String apiToken;
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("referenceId")
    private String referenceId;
    @JsonProperty("conversationId")
    private String conversationId;
    @JsonProperty("messageType")
    private String messageType;
    @JsonProperty("message")
    private String message;

    @JsonProperty("apiToken")
    public String getApiToken() {
        return apiToken;
    }

    @JsonProperty("apiToken")
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    @JsonProperty("clientId")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("clientId")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("referenceId")
    public String getReferenceId() {
        return referenceId;
    }

    @JsonProperty("referenceId")
    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    @JsonProperty("conversationId")
    public String getConversationId() {
        return conversationId;
    }

    @JsonProperty("conversationId")
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    @JsonProperty("messageType")
    public String getMessageType() {
        return messageType;
    }

    @JsonProperty("messageType")
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }


}