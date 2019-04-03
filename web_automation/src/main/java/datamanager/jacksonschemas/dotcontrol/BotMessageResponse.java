
package datamanager.jacksonschemas.dotcontrol;

import java.util.HashMap;
import java.util.Map;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "apiToken",
        "clientId",
        "referenceId",
        "conversationId",
        "messageType",
        "message",
        "timestamp",
        "source",
        "context"
})
public class BotMessageResponse {

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
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("source")
    private Object source;
    @JsonProperty("context")
    private BotMessageResponseContext context;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("source")
    public Object getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(Object source) {
        this.source = source;
    }

    @JsonProperty("context")
    public BotMessageResponseContext getContext() {
        return context;
    }

    @JsonProperty("context")
    public void setContext(BotMessageResponseContext context) {
        this.context = context;
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
        return "BotMessageResponse{" +
                "apiToken='" + apiToken + '\'' +
                ", clientId='" + clientId + '\'' +
                ", referenceId='" + referenceId + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", source=" + source +
                ", context=" + context +
                ", additionalProperties=" + additionalProperties +
                "}\n";
    }
}