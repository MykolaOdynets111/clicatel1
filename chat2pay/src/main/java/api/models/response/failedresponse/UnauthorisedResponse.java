package api.models.response.failedresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;

import static datetimeutils.DateTimeHelper.parseToLocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "timestamp",
        "status",
        "error",
        "path"
})
public class UnauthorisedResponse {

    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("status")
    public Integer status;
    @JsonProperty("error")
    public String error;
    @JsonProperty("path")
    public String path;

    public LocalDate getTimestamp() {
        return parseToLocalDate(timestamp);
    }
}
