
package testflo.jacksonschemas.allurescenario;

import java.util.*;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;
import testflo.jacksonschemas.AllureScenarioInterface;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "uid",
    "name",
    "fullName",
    "historyId",
    "time",
    "description",
    "descriptionHtml",
    "status",
    "statusMessage",
    "flaky",
    "beforeStages",
    "testStage",
    "afterStages",
    "labels",
    "parameters",
    "links",
    "hidden",
    "retry",
    "extra",
    "source",
    "parameterValues"
})
public class AllureScenario  implements AllureScenarioInterface {

    @JsonProperty("uid")
    private String uid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("historyId")
    private String historyId;
    @JsonProperty("time")
    private Time time;
    @JsonProperty("description")
    private String description;
    @JsonProperty("descriptionHtml")
    private String descriptionHtml;
    @JsonProperty("status")
    private String status;
    @JsonProperty("statusMessage")
    private String statusMessage;
    @JsonProperty("flaky")
    private Boolean flaky;
    @JsonProperty("beforeStages")
    private List<Object> beforeStages = null;
    @JsonProperty("testStage")
    private TestStage testStage;
    @JsonProperty("afterStages")
    private List<Object> afterStages = null;
    @JsonProperty("labels")
    private List<Label> labels = null;
    @JsonProperty("parameters")
    private List<Object> parameters = null;
    @JsonProperty("links")
    private List<Object> links = null;
    @JsonProperty("hidden")
    private Boolean hidden;
    @JsonProperty("retry")
    private Boolean retry;
    @JsonProperty("extra")
    private Extra extra;
    @JsonProperty("source")
    private String source;
    @JsonProperty("parameterValues")
    private List<Object> parameterValues = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

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
        if(!name.trim().contains(" "))
            return description; // for mc2 tests as we save TCT name in description filed
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("fullName")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("fullName")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("historyId")
    public String getHistoryId() {
        return historyId;
    }

    @JsonProperty("historyId")
    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    @JsonProperty("time")
    public Time getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(Time time) {
        this.time = time;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }


    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("descriptionHtml")
    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    @JsonProperty("descriptionHtml")
    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("statusMessage")
    public String getStatusMessage() {
        return statusMessage;
    }

    @JsonProperty("flaky")
    public Boolean getFlaky() {
        return flaky;
    }

    @JsonProperty("flaky")
    public void setFlaky(Boolean flaky) {
        this.flaky = flaky;
    }

    @JsonProperty("beforeStages")
    public List<Object> getBeforeStages() {
        return beforeStages;
    }

    @JsonProperty("beforeStages")
    public void setBeforeStages(List<Object> beforeStages) {
        this.beforeStages = beforeStages;
    }

    @JsonProperty("testStage")
    public TestStage getTestStage() {
        return testStage;
    }

    @JsonProperty("testStage")
    public void setTestStage(TestStage testStage) {
        this.testStage = testStage;
    }

    @JsonProperty("afterStages")
    public List<Object> getAfterStages() {
        return afterStages;
    }

    @JsonProperty("afterStages")
    public void setAfterStages(List<Object> afterStages) {
        this.afterStages = afterStages;
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

    @JsonProperty("links")
    public List<Object> getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(List<Object> links) {
        this.links = links;
    }

    @JsonProperty("hidden")
    public Boolean getHidden() {
        return hidden;
    }

    @JsonProperty("hidden")
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    @JsonProperty("retry")
    public Boolean getRetry() {
        return retry;
    }

    @JsonProperty("retry")
    public void setRetry(Boolean retry) {
        this.retry = retry;
    }

    @JsonProperty("extra")
    public Extra getExtra() {
        return extra;
    }

    @JsonProperty("extra")
    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("parameterValues")
    public List<Object> getParameterValues() {
        return parameterValues;
    }

    @JsonProperty("parameterValues")
    public void setParameterValues(List<Object> parameterValues) {
        this.parameterValues = parameterValues;
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
    public String getTestId() {
        try {
            return getLabels().stream().filter(e -> e.getName().equalsIgnoreCase("testId"))
                    .findFirst().get().getValue();
        }catch (NoSuchElementException e){
            return "";
        }
    }

    @Override
    public List<Map<String, String>> getStepsWithStatuses() {
        List<Map<String, String>> steps = new ArrayList<>();

        getTestStage().getSteps().forEach(e ->
                            {
                                Map<String, String> map = new HashMap<>();
                                map.put("name", e.getName());
                                map.put("status", e.getStatus());
                                steps.add(map);
                            });
        return steps;
    }

    @Override
    public String getFailureMessage() {
        if(getStatusMessage()!=null) {
            return getStatusMessage().replaceAll("null", "").trim();
        }else{
            return "";
        }
    }

}
