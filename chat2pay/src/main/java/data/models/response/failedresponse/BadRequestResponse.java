package data.models.response.failedresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "message",
        "errors"
})
public class BadRequestResponse {

    @JsonProperty("status")
    public String status;
    @JsonProperty("message")
    public String message;
    @JsonProperty("errors")
    public List<String> errors;
}
