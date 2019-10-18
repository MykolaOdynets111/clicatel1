
package datamanager.jacksonschemas.dotcontrol;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "context",
    "message",
    "messageId",
    "messageType",
    "source",
    "timestamp"
})
public class History {

    @JsonProperty("context")
    private InitHistoryContext context;
    @JsonProperty("message")
    private String message;
    @JsonProperty("messageId")
    private String messageId;
    @JsonProperty("messageType")
    private String messageType;
    @JsonProperty("source")
    private String source;
    @JsonProperty("timestamp")
    private Integer timestamp;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public History(String messageId){
        this.message = "string";
        this.messageId = messageId;
        this.messageType = "PLAIN";
        this.timestamp = 0;
    }

    public History(String message, String source){
        this.message = message;
        this.messageType = "PLAIN";
        this.source = source;
        this.timestamp = 0;
    }

    @JsonProperty("context")
    public InitHistoryContext getContext() {
        return context;
    }

    @JsonProperty("context")
    public void setContext(InitHistoryContext context) {
        this.context = context;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("messageId")
    public String getMessageId() {
        return messageId;
    }

    @JsonProperty("messageId")
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @JsonProperty("messageType")
    public String getMessageType() {
        return messageType;
    }

    @JsonProperty("messageType")
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("timestamp")
    public Integer getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
