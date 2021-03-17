package datamanager.jacksonschemas.dotcontrol;

import com.github.javafaker.Faker;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "messageId",
        "context"
})
public class MessageRequest extends MessageBase{

    Faker faker = new Faker();

    @JsonProperty("messageId")
    private String messageId;
    @JsonProperty("context")
    private DotControlRequestContextInterface context;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public MessageRequest(String apiToken, String message){
        this.setApiToken(apiToken);
        this.setConversationId("conv_" + faker.lorem().word());
        this.setClientId( "testing_dotc" +  faker.number().randomNumber(7, false));
        this.setReferenceId("" + faker.number().randomNumber(6, false));
        this.setMessageType("PLAIN");
        this.setMessage(message);

        this.messageId = faker.code().ean8();
//        this.context = new DotControlRequestMessageContext();
    }

    @JsonProperty("messageId")
    public String getMessageId() {
        return messageId;
    }

    @JsonProperty("messageId")
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @JsonProperty("context")
    public DotControlRequestContextInterface getContext() {
        return context;
    }

    @JsonProperty("context")
    public void setContext(DotControlRequestContextInterface context) {
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
