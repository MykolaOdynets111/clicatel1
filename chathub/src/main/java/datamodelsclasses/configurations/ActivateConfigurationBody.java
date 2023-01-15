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
    @JsonProperty("i.name")
    public String name;

    @JsonProperty("i.clientSecret")
    public String clientSecret;

    @JsonProperty("i.clientId")
    public String clientId;

    @JsonProperty("i.host")
    public String host;

    @JsonProperty("i.providerId")
    public String providerId;

    @JsonProperty("i.type")
    public String type;

    public void setActivateConfigurationBody(Map<String,String> parameters) {
        setName(parameters.get("i.name"));
        setClientSecret(parameters.get("i.clientSecret"));
        setClientId(parameters.get("i.clientId"));
        setHost(parameters.get("i.host"));
        setProviderId(parameters.get("i.providerId"));
        setType(parameters.get("i.type"));
    }
}
