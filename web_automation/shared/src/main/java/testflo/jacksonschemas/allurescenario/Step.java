
package testflo.jacksonschemas.allurescenario;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "time",
    "status",
    "steps",
    "attachments",
    "parameters",
    "stepsCount",
    "attachmentsCount",
    "shouldDisplayMessage",
    "hasContent"
})
public class Step {

    @JsonProperty("name")
    private String name;
    @JsonProperty("time")
    private Time_ time;
    @JsonProperty("status")
    private String status;
    @JsonProperty("steps")
    private List<Object> steps = null;
    @JsonProperty("attachments")
    private List<Object> attachments = null;
    @JsonProperty("parameters")
    private List<Object> parameters = null;
    @JsonProperty("stepsCount")
    private Integer stepsCount;
    @JsonProperty("attachmentsCount")
    private Integer attachmentsCount;
    @JsonProperty("shouldDisplayMessage")
    private Boolean shouldDisplayMessage;
    @JsonProperty("hasContent")
    private Boolean hasContent;
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

    @JsonProperty("time")
    public Time_ getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(Time_ time) {
        this.time = time;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("steps")
    public List<Object> getSteps() {
        return steps;
    }

    @JsonProperty("steps")
    public void setSteps(List<Object> steps) {
        this.steps = steps;
    }

    @JsonProperty("attachments")
    public List<Object> getAttachments() {
        return attachments;
    }

    @JsonProperty("attachments")
    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    @JsonProperty("parameters")
    public List<Object> getParameters() {
        return parameters;
    }

    @JsonProperty("parameters")
    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    @JsonProperty("stepsCount")
    public Integer getStepsCount() {
        return stepsCount;
    }

    @JsonProperty("stepsCount")
    public void setStepsCount(Integer stepsCount) {
        this.stepsCount = stepsCount;
    }

    @JsonProperty("attachmentsCount")
    public Integer getAttachmentsCount() {
        return attachmentsCount;
    }

    @JsonProperty("attachmentsCount")
    public void setAttachmentsCount(Integer attachmentsCount) {
        this.attachmentsCount = attachmentsCount;
    }

    @JsonProperty("shouldDisplayMessage")
    public Boolean getShouldDisplayMessage() {
        return shouldDisplayMessage;
    }

    @JsonProperty("shouldDisplayMessage")
    public void setShouldDisplayMessage(Boolean shouldDisplayMessage) {
        this.shouldDisplayMessage = shouldDisplayMessage;
    }

    @JsonProperty("hasContent")
    public Boolean getHasContent() {
        return hasContent;
    }

    @JsonProperty("hasContent")
    public void setHasContent(Boolean hasContent) {
        this.hasContent = hasContent;
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
