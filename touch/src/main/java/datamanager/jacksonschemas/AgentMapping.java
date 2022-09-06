package datamanager.jacksonschemas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import datamanager.enums.Days;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "startWorkTime",
        "endWorkTime",
        "days"
})
public class AgentMapping {

    @JsonProperty("startWorkTime")
    private String startWorkTime;
    @JsonProperty("endWorkTime")
    private String endWorkTime;
    @JsonProperty("days")
    private List<Days> days;

    @Override
    public String toString() {
        return "AgentSupportHours {" +
                "startWorkTime='" + startWorkTime + '\'' +
                ", endWorkTime='" + endWorkTime + '\'' +
                ", days ='" + days + '\'' + '}';
    }
}