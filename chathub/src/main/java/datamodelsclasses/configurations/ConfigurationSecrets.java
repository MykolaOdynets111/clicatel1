package datamodelsclasses.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationSecrets {
    @JsonProperty("id")
    public String id;

    @JsonProperty("providerId")
    public String providerId;

    @JsonProperty("accountProviderConfigStatusId")
    public String accountProviderConfigStatusId;

    @JsonProperty("configurationEnvironmentTypeId")
    public String configurationEnvironmentTypeId;

    @JsonProperty("displayName")
    public String displayName;

    @JsonProperty("clientId")
    public String clientId;

    @JsonProperty("clientSecret")
    public String clientSecret;

    @JsonProperty("hostUrl")
    public String hostUrl;

    @JsonProperty("createdDate")
    public String createdDate;

    @JsonProperty("modifiedDate")
    public String modifiedDate;

    public ConfigurationSecrets(Map<String, String> parameters) {
        this.id = parameters.get("o.id");
        this.providerId = parameters.get("o.providerId");
        this.accountProviderConfigStatusId = parameters.get("o.accountProviderConfigStatusId");
        this.configurationEnvironmentTypeId = parameters.get("o.configurationEnvironmentTypeId");
        this.displayName = parameters.get("o.displayName");
        this.clientId = parameters.get("o.clientId");
        this.clientSecret = parameters.get("o.clientSecret");
        this.hostUrl = parameters.get("o.hostUrl");
    }
}
