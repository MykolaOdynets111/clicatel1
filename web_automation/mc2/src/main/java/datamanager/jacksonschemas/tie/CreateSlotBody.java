package datamanager.jacksonschemas.tie;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "intent",
        "name",
        "entity_type",
        "prompt",
        "confirm",
        "tenant"
})
public class CreateSlotBody {

    @JsonProperty("intent")
    private String intent;
    @JsonProperty("name")
    private String name;
    @JsonProperty("entity_type")
    private String entityType;
    @JsonProperty("prompt")
    private String prompt;
    @JsonProperty("confirm")
    private String confirm;
    @JsonProperty("tenant")
    private String tenant;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("intent")
    public String getIntent() {
        return intent;
    }

    @JsonProperty("intent")
    public CreateSlotBody setIntent(String intent) {
        this.intent = intent;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public CreateSlotBody setName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("entity_type")
    public String getEntityType() {
        return entityType;
    }

    @JsonProperty("entity_type")
    public CreateSlotBody setEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    @JsonProperty("prompt")
    public String getPrompt() {
        return prompt;
    }

    @JsonProperty("prompt")
    public CreateSlotBody setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    @JsonProperty("confirm")
    public String getConfirm() {
        return confirm;
    }

    @JsonProperty("confirm")
    public CreateSlotBody setConfirm(String confirm) {
        this.confirm = confirm;
        return this;
    }

    @JsonProperty("tenant")
    public String getTenant() {
        return tenant;
    }

    @JsonProperty("tenant")
    public CreateSlotBody setTenant(String tenant) {
        this.tenant = tenant;
        return this;
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
        return "CreateSlotBody{" +
                "intent='" + intent + '\'' +
                ", name='" + name + '\'' +
                ", entityType='" + entityType + '\'' +
                ", prompt='" + prompt + '\'' +
                ", confirm='" + confirm + '\'' +
                ", tenant='" + tenant + '\'' +
                '}';
    }
}