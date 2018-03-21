
package dataprovider.jackson_schemas.tenant_address;

import java.util.HashMap;
import java.util.Map;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dayOfWeek",
    "startWorkTime",
    "endWorkTime",
    "id"
})
public class BusinessHour {

    @JsonProperty("dayOfWeek")
    private String dayOfWeek;
    @JsonProperty("startWorkTime")
    private String startWorkTime;
    @JsonProperty("endWorkTime")
    private String endWorkTime;
    @JsonProperty("id")
    private String id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("dayOfWeek")
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    @JsonProperty("dayOfWeek")
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @JsonProperty("startWorkTime")
    public String getStartWorkTime() {
        return startWorkTime;
    }

    @JsonProperty("startWorkTime")
    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    @JsonProperty("endWorkTime")
    public String getEndWorkTime() {
        return endWorkTime;
    }

    @JsonProperty("endWorkTime")
    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
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
