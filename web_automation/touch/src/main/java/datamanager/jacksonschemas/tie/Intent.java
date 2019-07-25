
package datamanager.jacksonschemas.tie;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "category",
        "raw_intent",
        "intent",
        "type"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Intent {

    @JsonProperty("category")
    private Object category;
    @JsonProperty("raw_intent")
    private String rawIntent;
    @JsonProperty("intent")
    private String intent;
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("category")
    public Object getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(Object category) {
        this.category = category;
    }

    @JsonProperty("raw_intent")
    public String getRawIntent() {
        return rawIntent;
    }

    @JsonProperty("raw_intent")
    public void setRawIntent(String rawIntent) {
        this.rawIntent = rawIntent;
    }

    @JsonProperty("intent")
    public String getIntent() {
        return intent;
    }

    @JsonProperty("intent")
    public void setIntent(String intent) {
        this.intent = intent;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
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