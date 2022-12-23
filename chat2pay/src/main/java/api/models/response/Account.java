package api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "active",
        "accountState"  ,
        "changedStateReason",
        "featureToggles"
})

@Data
public class Account {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("accountState")
    private String accountState;

    @JsonProperty("changedStateReason")
    private String changedStateReason;

    @JsonProperty("featureToggles")
    public List<FeatureToggles> featureToggles = Arrays.asList(new FeatureToggles());

}
