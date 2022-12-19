package pojoClasses.Authentication;

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
    public String id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("active")
    public boolean active;

    @JsonProperty("accountState")
    public String accountState;

    @JsonProperty("changedStateReason")
    public Object changedStateReason;

    @JsonProperty("featureToggles")
    public List<FeatureToggles> featureToggles = Arrays.asList(new FeatureToggles());
}
