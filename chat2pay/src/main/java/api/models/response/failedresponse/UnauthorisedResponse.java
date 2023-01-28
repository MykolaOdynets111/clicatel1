package api.models.response.failedresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static datetimeutils.DateTimeHelper.getYYYY_MM_DD_HH_MM_SS;

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
        String localDateTime = Arrays.stream(timestamp.split("\\+")).findFirst()
                .orElseThrow(NoSuchElementException::new);

        return LocalDateTime.parse(localDateTime, getYYYY_MM_DD_HH_MM_SS()).toLocalDate();
    }
}
