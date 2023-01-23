package datamodelsclasses.configurations;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "displayName",
        "configurationEnvironmentTypeId",
        "accountProviderConfigStatusId"
})
@Data
@NoArgsConstructor
public class InternalConfigurations {
    @JsonProperty("id")
    private String id;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("configurationEnvironmentTypeId")
    private String configurationEnvironmentTypeId;

    @JsonProperty("accountProviderConfigStatusId")
    private String accountProviderConfigStatusId;


    public InternalConfigurations (String id, String displayName, String configurationEnvironmentTypeId, String accountProviderConfigStatusId){
        this.setId(id);
        this.setDisplayName(displayName);
        this.setConfigurationEnvironmentTypeId(configurationEnvironmentTypeId);
        this.setAccountProviderConfigStatusId(accountProviderConfigStatusId);
    }
}
