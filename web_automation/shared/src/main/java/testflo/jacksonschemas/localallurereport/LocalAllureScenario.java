
package testflo.jacksonschemas.localallurereport;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;
import testflo.jacksonschemas.AllureScenarioInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(value = { "intValue" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "uid",
    "name",
    "title",
    "time",
    "summary",
    "failure",
    "description",
    "severity",
    "status",
    "testId",
    "suite",
    "steps",
    "attachments",
    "issues",
    "labels",
    "parameters"
})
public class LocalAllureScenario implements AllureScenarioInterface {

    @JsonProperty("uid")
    private String uid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("title")
    private String title;
    @JsonProperty("time")
    private Time time;
    @JsonProperty("summary")
    private Summary summary;
    @JsonProperty("failure")
    private Failure failure;
    @JsonProperty("description")
    private Description description;
    @JsonProperty("severity")
    private String severity;
    @JsonProperty("status")
    private String status;
    @JsonProperty("testId")
    private TestId testId;
    @JsonProperty("suite")
    private Suite suite;
    @JsonProperty("steps")
    private List<Step> steps = null;
    @JsonProperty("attachments")
    private List<Attachment> attachments = null;
    @JsonProperty("issues")
    private List<Object> issues = null;
    @JsonProperty("labels")
    private List<Label> labels = null;
    @JsonProperty("parameters")
    private List<Object> parameters = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("uid")
    public String getUid() {
        return uid;
    }

    @JsonProperty("uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

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
    public Time getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(Time time) {
        this.time = time;
    }

    @JsonProperty("summary")
    public Summary getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    @JsonProperty("failure")
    public Failure getFailure() {
        return failure;
    }

    @JsonProperty("failure")
    public void setFailure(Failure failure) {
        this.failure = failure;
    }


    @JsonProperty("description")
    public String getDescription() {
        if(description.getValue() == null){
            return "";
        }else {
            return (String) description.getValue();
        }
    }

    @Override
    public String getFailureMessage() {
        if(failure!=null){
            return failure.getMessage();
        }
        return "";
    }

    @JsonProperty("severity")
    public String getSeverity() {
        return severity;
    }

    @JsonProperty("severity")
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("testId")
    public String getTestId() {
        if(testId!=null) return testId.getName();
        else return "";
    }

    @Override
    public List<Map<String, String>> getStepsWithStatuses() {
        List<Map<String, String>> steps = new ArrayList<>();

        getSteps().forEach(e ->
        {
            Map<String, String> map = new HashMap<>();
            map.put("name", e.getName());
            map.put("status", e.getStatus());
            steps.add(map);
        });
        return steps;
    }

    @JsonProperty("testId")
    public void setTestId(TestId testId) {
        this.testId = testId;
    }

    @JsonProperty("suite")
    public Suite getSuite() {
        return suite;
    }

    @JsonProperty("suite")
    public void setSuite(Suite suite) {
        this.suite = suite;
    }

    @JsonProperty("steps")
    public List<Step> getSteps() {
        return steps;
    }

    @JsonProperty("steps")
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @JsonProperty("attachments")
    public List<Attachment> getAttachments() {
        return attachments;
    }

    @JsonProperty("attachments")
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @JsonProperty("issues")
    public List<Object> getIssues() {
        return issues;
    }

    @JsonProperty("issues")
    public void setIssues(List<Object> issues) {
        this.issues = issues;
    }

    @JsonProperty("labels")
    public List<Label> getLabels() {
        return labels;
    }

    @JsonProperty("labels")
    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    @JsonProperty("parameters")
    public List<Object> getParameters() {
        return parameters;
    }

    @JsonProperty("parameters")
    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
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
