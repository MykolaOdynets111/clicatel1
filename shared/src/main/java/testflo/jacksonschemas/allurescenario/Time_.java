
package testflo.jacksonschemas.allurescenario;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "start",
    "stop",
    "duration"
})
public class Time_ {

    @JsonProperty("start")
    private Long start;
    @JsonProperty("stop")
    private Long stop;
    @JsonProperty("duration")
    private Long duration;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("start")
    public Long getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(Long start) {
        this.start = start;
    }

    @JsonProperty("stop")
    public Long getStop() {
        return stop;
    }

    @JsonProperty("stop")
    public void setStop(Long stop) {
        this.stop = stop;
    }

    @JsonProperty("duration")
    public Long getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(Long duration) {
        this.duration = duration;
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
