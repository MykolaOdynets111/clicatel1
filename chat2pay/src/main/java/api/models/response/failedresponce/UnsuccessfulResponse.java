package api.models.response.failedresponce;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.Arrays;

import static datetimeutils.DateTimeHelper.getYYYY_MM_DD_HH_MM_SS;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "timestamp",
        "status",
        "error",
        "path"
})
public class UnsuccessfulResponse {

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("status")
    public Integer status;
    @JsonProperty("error")
    public String error;
    @JsonProperty("path")
    public String path;

    public LocalDateTime getTimestamp() {
        String localDateTime = Arrays.stream(timestamp.split("\\+")).findFirst().get();
        return LocalDateTime.parse(localDateTime, getYYYY_MM_DD_HH_MM_SS());
    }
}
