
package testflo.jacksonschemas.allurescenario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "severity",
    "retries",
    "categories",
    "history",
    "tags"
})
public class Extra {

    @JsonProperty("severity")
    private String severity;
    @JsonProperty("retries")
    private List<Object> retries = null;
    @JsonProperty("categories")
    private List<Object> categories = null;
    @JsonProperty("history")
    private History history;
    @JsonProperty("tags")
    private List<Object> tags = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("severity")
    public String getSeverity() {
        return severity;
    }

    @JsonProperty("severity")
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @JsonProperty("retries")
    public List<Object> getRetries() {
        return retries;
    }

    @JsonProperty("retries")
    public void setRetries(List<Object> retries) {
        this.retries = retries;
    }

    @JsonProperty("categories")
    public List<Object> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    @JsonProperty("history")
    public History getHistory() {
        return history;
    }

    @JsonProperty("history")
    public void setHistory(History history) {
        this.history = history;
    }

    @JsonProperty("tags")
    public List<Object> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<Object> tags) {
        this.tags = tags;
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
