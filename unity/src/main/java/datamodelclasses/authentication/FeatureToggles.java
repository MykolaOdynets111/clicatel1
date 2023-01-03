package datamodelclasses.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeatureToggles {
    @JsonProperty("milliseconds_sms_status_timestamp")
    private String milliseconds_sms_status_timestamp;
}
