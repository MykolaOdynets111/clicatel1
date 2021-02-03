package datamanager.jacksonschemas;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
        "startWorkTime",
        "endWorkTime",
        "dayOfWeek"
})
public class AgentMapping {

    @JsonProperty("startWorkTime")
    private String startWorkTime;
    @JsonProperty("endWorkTime")
    private String endWorkTime;
    @JsonProperty("dayOfWeek")
    private String dayOfWeek;

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

    @Override
    public String toString() {
        return "AgentMapping{" +
                "startWorkTime='" + startWorkTime + '\'' +
                ", endWorkTime='" + endWorkTime + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                '}';
    }
}