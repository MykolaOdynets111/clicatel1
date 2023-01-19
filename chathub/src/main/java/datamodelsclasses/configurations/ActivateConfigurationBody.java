package datamodelsclasses.configurations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "clientSecret",
        "clientId",
        "host",
        "providerId",
        "type"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivateConfigurationBody {
    @JsonProperty("name")
    public String name;

    @JsonProperty("clientSecret")
    public String clientSecret;

    @JsonProperty("clientId")
    public String clientId;

    @JsonProperty("host")
    public String host;

    @JsonProperty("providerId")
    public String providerId;

    @JsonProperty("type")
    public String type;
}
