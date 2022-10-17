package datamanager.jacksonschemas.chatextension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "duration",
        "startTime",
        "identifier",
        "description"
})
@Data @AllArgsConstructor @NoArgsConstructor
public class TimeSlots {

    @JsonProperty("duration")
    private int duration = 3600;
    @JsonProperty("startTime")
    private int startTime = 1672252200;
    @JsonProperty("identifier")
    private String identifier = "Wednesday, 28 December 2022, 6:30 PM UTC";
    @JsonProperty("description")
    private String  description = "Some description";

}