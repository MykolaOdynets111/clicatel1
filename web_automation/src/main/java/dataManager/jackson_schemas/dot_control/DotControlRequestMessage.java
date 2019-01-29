package dataManager.jackson_schemas.dot_control;

import java.util.HashMap;
import java.util.Map;

import com.github.javafaker.Faker;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "apiToken",
        "conversationId",
        "clientId",
        "referenceId",
        "messageId",
        "messageType",
        "message",
        "context"
})
public class DotControlRequestMessage {

    Faker faker = new Faker();

    @JsonProperty("apiToken")
    private String apiToken;
    @JsonProperty("conversationId")
    private String conversationId;
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("referenceId")
    private String referenceId;
    @JsonProperty("messageId")
    private String messageId;
    @JsonProperty("messageType")
    private String messageType;
    @JsonProperty("message")
    private String message;
    @JsonProperty("context")
    private DotControlRequestMessageContext context;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public DotControlRequestMessage(String apiToken, String message){
        this.apiToken = apiToken;
        this.conversationId = "conv_" + faker.lorem().word();
        this.clientId = "testing_dotc" +  faker.number().randomNumber(7, false);
        this.referenceId = "" + faker.number().randomNumber(6, false);
        this.messageId = faker.code().ean8();
        this.messageType = "PLAIN";
        this.message = message;
        this.context = new DotControlRequestMessageContext();
    }



    @JsonProperty("apiToken")
    public String getApiToken() {
        return apiToken;
    }

    @JsonProperty("apiToken")
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    @JsonProperty("conversationId")
    public String getConversationId() {
        return conversationId;
    }

    @JsonProperty("conversationId")
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
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

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("context")
    public DotControlRequestMessageContext getContext() {
        return context;
    }

    @JsonProperty("context")
    public void setContext(DotControlRequestMessageContext context) {
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
    public String toString(){
        return "apiToken: " + getApiToken() + ", conversationId: " + getConversationId() + ",\n" +
                "clientId: " + getClientId() + ", referenceId: " +getReferenceId() + ",\n" +
                "messageId: " +getMessageId() + ", messageType: " + getMessageType() + ",\n" +
                "message: " +getMessage();
    }
}
