package datamodelsclasses.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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

    @JsonProperty("clientSecretid")
    public String clientSecret;

    @JsonProperty("hostUrl")
    public String hostUrl;

    @JsonProperty("createdDate")
    public String createdDate;

    @JsonProperty("modifiedDate")
    public String modifiedDate;
}
