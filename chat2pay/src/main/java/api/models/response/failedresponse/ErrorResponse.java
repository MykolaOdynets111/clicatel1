package api.models.response.failedresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static datetimeutils.DateTimeHelper.getYYYY_MM_DD_HH_MM_SS;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message",
        "reasonCode",
        "reason",
        "status",
        "timestamp",
        "errors",
        "path"
})

@Data
public class ErrorResponse {
    @JsonProperty("message")
    public String message;

    @JsonProperty("reasonCode")
    public int reasonCode;

    @JsonProperty("reason")
    public String reason;

    @JsonProperty("status")
    public String status;

    @JsonProperty("timestamp")
    public String timestamp;

    @JsonProperty("errors")
    public List<String> errors;

    @JsonProperty("error")
    public String error;

    @JsonProperty("path")
    public String path;

    public LocalDate getTimestamp() {
        String localDateTime = Arrays.stream(timestamp.split("\\+")).findFirst()
                .orElseThrow(NoSuchElementException::new);

        return LocalDateTime.parse(localDateTime, getYYYY_MM_DD_HH_MM_SS()).toLocalDate();
    }
}
