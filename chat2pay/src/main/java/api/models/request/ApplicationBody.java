package api.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "applicationId"
})

@Setter
public class ApplicationBody {

    @JsonProperty("status")
    private String status = "ACTIVATED";

    @JsonProperty("applicationId")
    private String applicationId;
}
