package datamodelsclasses.InternalProductToken;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternalProducts {

    @JsonProperty("token")
    private String token;

    @JsonProperty("applicationName")
    private String applicationName;

    @JsonProperty("enabled")
    private String enabled;
}