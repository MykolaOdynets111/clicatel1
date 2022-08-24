
package testflo.jacksonschemas.allurescenario;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "uid",
    "reportUrl",
    "status",
    "time"
})
public class Item {

    @JsonProperty("uid")
    private String uid;
    @JsonProperty("reportUrl")
    private String reportUrl;
    @JsonProperty("status")
    private String status;
    @JsonProperty("time")
    private Time__ time;
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

    @JsonProperty("reportUrl")
    public String getReportUrl() {
        return reportUrl;
    }

    @JsonProperty("reportUrl")
    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("time")
    public Time__ getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(Time__ time) {
        this.time = time;
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
