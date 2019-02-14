package dataManager.jackson_schemas;


import java.util.HashMap;
import java.util.Map;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "startWorkTime",
        "endWorkTime",
        "dayOfWeek"
})
public class SupportHoursItem {

    @JsonProperty("startWorkTime")
    private String startWorkTime;
    @JsonProperty("endWorkTime")
    private String endWorkTime;
    @JsonProperty("dayOfWeek")
    private String dayOfWeek;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonProperty("dayOfWeek")
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    @JsonProperty("dayOfWeek")
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
    public boolean equals(Object o){
        SupportHoursItem other = (SupportHoursItem) o;
        if(this.dayOfWeek.equals(other.dayOfWeek)&&
           this.endWorkTime.equals(other.endWorkTime) &&
           this.startWorkTime.equals(other.startWorkTime)){
            return true;
        } else {
            return false;
        }
    }
}
