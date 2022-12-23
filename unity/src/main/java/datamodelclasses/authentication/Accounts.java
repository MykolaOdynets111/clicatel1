package datamodelclasses.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Accounts {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("accountState")
    private String accountState;

    @JsonProperty("changedStateReason")
    private Object changedStateReason;

    @JsonProperty("featureToggles")
    private List<FeatureToggles> featureToggles = Arrays.asList(new FeatureToggles());
}
