
package testflo.jacksonschemas.allurescenario;

import java.util.HashMap;
import java.util.Map;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "failed",
    "broken",
    "skipped",
    "passed",
    "unknown",
    "total"
})
public class Statistic {

    @JsonProperty("failed")
    private Integer failed;
    @JsonProperty("broken")
    private Integer broken;
    @JsonProperty("skipped")
    private Integer skipped;
    @JsonProperty("passed")
    private Integer passed;
    @JsonProperty("unknown")
    private Integer unknown;
    @JsonProperty("total")
    private Integer total;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("failed")
    public Integer getFailed() {
        return failed;
    }

    @JsonProperty("failed")
    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    @JsonProperty("broken")
    public Integer getBroken() {
        return broken;
    }

    @JsonProperty("broken")
    public void setBroken(Integer broken) {
        this.broken = broken;
    }

    @JsonProperty("skipped")
    public Integer getSkipped() {
        return skipped;
    }

    @JsonProperty("skipped")
    public void setSkipped(Integer skipped) {
        this.skipped = skipped;
    }

    @JsonProperty("passed")
    public Integer getPassed() {
        return passed;
    }

    @JsonProperty("passed")
    public void setPassed(Integer passed) {
        this.passed = passed;
    }

    @JsonProperty("unknown")
    public Integer getUnknown() {
        return unknown;
    }

    @JsonProperty("unknown")
    public void setUnknown(Integer unknown) {
        this.unknown = unknown;
    }

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
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
