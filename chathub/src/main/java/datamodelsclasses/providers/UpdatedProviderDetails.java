package datamodelsclasses.providers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UpdatedProviderDetails {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("logoUrl")
    private String logoUrl;

    @JsonProperty("description")
    private String description;

    @JsonProperty("moreInfoUrl")
    private String moreInfoUrl;

    public UpdatedProviderDetails(Map<String,String> parameters){
        this.id = parameters.get("o.id");
        this.name = parameters.get("o.name");
        this.logoUrl = parameters.get("o.logoUrl");
        this.description = parameters.get("o.description");
        this.moreInfoUrl = parameters.get("o.moreInfoUrl");
    }
}

