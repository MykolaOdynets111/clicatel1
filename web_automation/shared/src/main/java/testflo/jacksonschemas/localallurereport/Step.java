
package testflo.jacksonschemas.localallurereport;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "title",
    "time",
    "summary",
    "status",
    "attachments",
    "steps"
})
public class Step {

    @JsonProperty("name")
    private String name;
    @JsonProperty("title")
    private String title;
    @JsonProperty("time")
    private Time_ time;
    @JsonProperty("summary")
    private Summary_ summary;
    @JsonProperty("status")
    private String status;
    @JsonProperty("attachments")
    private List<Object> attachments = null;
    @JsonProperty("steps")
    private List<Object> steps = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("time")
    public Time_ getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(Time_ time) {
        this.time = time;
    }

    @JsonProperty("summary")
    public Summary_ getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(Summary_ summary) {
        this.summary = summary;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("attachments")
    public List<Object> getAttachments() {
        return attachments;
    }

    @JsonProperty("attachments")
    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    @JsonProperty("steps")
    public List<Object> getSteps() {
        return steps;
    }

    @JsonProperty("steps")
    public void setSteps(List<Object> steps) {
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
