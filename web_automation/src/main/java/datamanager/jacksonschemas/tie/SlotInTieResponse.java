package datamanager.jacksonschemas.tie;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "prompt",
        "name",
        "value",
        "confirm"
})
public class SlotInTieResponse {

    @JsonProperty("prompt")
    private String prompt;
    @JsonProperty("name")
    private String name;
    @JsonProperty("value")
    private String value;
    @JsonProperty("confirm")
    private String confirm;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("prompt")
    public String getPrompt() {
        return prompt;
    }

    @JsonProperty("prompt")
    public SlotInTieResponse setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public SlotInTieResponse setName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public SlotInTieResponse setValue(String value) {
        this.value = value;
        return this;
    }

    @JsonProperty("confirm")
    public String getConfirm() {
        return confirm;
    }

    @JsonProperty("confirm")
    public SlotInTieResponse setConfirm(String confirm) {
        this.confirm = confirm;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlotInTieResponse that = (SlotInTieResponse) o;
        return Objects.equals(prompt, that.prompt) &&
                Objects.equals(name, that.name) &&
                Objects.equals(value, that.value) &&
                Objects.equals(confirm, that.confirm);
    }

    @Override
    public int hashCode() {

        return Objects.hash(prompt, name, value, confirm);
    }
}
