
package testflo.jacksonschemas.localallurereport;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "attachments",
    "steps"
})
public class Summary {

    @JsonProperty("attachments")
    private Integer attachments;
    @JsonProperty("steps")
    private Integer steps;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("attachments")
    public Integer getAttachments() {
        return attachments;
    }

    @JsonProperty("attachments")
    public void setAttachments(Integer attachments) {
        this.attachments = attachments;
    }

    @JsonProperty("steps")
    public Integer getSteps() {
        return steps;
    }

    @JsonProperty("steps")
    public void setSteps(Integer steps) {
        this.steps = steps;
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
