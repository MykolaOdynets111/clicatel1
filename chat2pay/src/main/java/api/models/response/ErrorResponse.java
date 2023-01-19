package api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

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

    @JsonProperty("path")
    public String path;
}
