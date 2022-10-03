package datamanager.jacksonschemas.supportHours;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import datamanager.enums.Days;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "startWorkTime",
        "endWorkTime",
        "days"
})
public class SupportHoursMapping {

    @JsonProperty("startWorkTime")
    private String startWorkTime;
    @JsonProperty("endWorkTime")
    private String endWorkTime;
    @JsonProperty("days")
    private List<String> days;

    public SupportHoursMapping() {
        this.setStartWorkTime(LocalTime.of(0, 0).toString());
        this.setEndWorkTime(LocalTime.of(23, 59).toString());
        this.setDays(Days.getDaysValue());
    }

    public SupportHoursMapping(List<String> days) {
        this.setStartWorkTime(LocalTime.of(0, 0).toString());
        this.setEndWorkTime(LocalTime.of(23, 59).toString());
        this.setDays(days);
    }

    public SupportHoursMapping(String startWorkTime, String endWorkTime, List<String> days) {
        this.setStartWorkTime((startWorkTime));
        this.setEndWorkTime(endWorkTime);
        this.setDays(days);
    }
}