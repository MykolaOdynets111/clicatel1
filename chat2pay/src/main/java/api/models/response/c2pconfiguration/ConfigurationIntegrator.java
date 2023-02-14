package api.models.response.c2pconfiguration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "applicationId",
        "name",
        "status",
        "type"
})
@Getter
public class ConfigurationIntegrator {

    @JsonProperty("applicationId")
    public String applicationId;
    @JsonProperty("name")
    public String name;
    @JsonProperty("status")
    public String status;
    @JsonProperty("type")
    public String type;
}
