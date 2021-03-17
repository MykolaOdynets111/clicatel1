
package datamanager.jacksonschemas.dotcontrol;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "source",
        "context"
})
public class MessageResponse extends MessageBase{

    @JsonProperty("source")
    private Object source;
    @JsonProperty("context")
    private BotMessageResponseContext context;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
        return "MessageResponse{" +
                "apiToken='" + this.getApiToken() + '\'' +
                ", clientId='" + this.getClientId() + '\'' +
                ", referenceId='" + this.getReferenceId() + '\'' +
                ", conversationId='" + this.getConversationId() + '\'' +
                ", messageType='" + this.getMessageType() + '\'' +
                ", message='" + this.getMessage() + '\'' +
                ", timestamp=" + this.getTimestamp() +
                ", source=" + source +
                ", context=" + context +
                ", additionalProperties=" + additionalProperties +
                "}\n";
    }
}