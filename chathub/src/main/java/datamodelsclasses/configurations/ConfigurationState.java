package datamodelsclasses.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationState {
    @JsonProperty("id")
    public String id;
    @JsonProperty("accountProviderConfigStatusId")
    public String accountProviderConfigStatusId;

}
