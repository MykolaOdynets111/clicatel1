package datamodelsclasses.providers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllProviders {

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

    @JsonProperty("versions")
    private List<Versions> versions = Arrays.asList(new Versions());

    @JsonProperty("isAdded")
    public boolean isAdded;
    public AllProviders(Map<String,String> parameters) {
        this.id = parameters.get("o.id");
        this.name = parameters.get("o.name");
        this.logoUrl = parameters.get("o.logoUrl");
        this.description = parameters.get("o.description");
        this.moreInfoUrl = parameters.get("o.moreInfoUrl");
        this.isAdded = Boolean.parseBoolean(parameters.get("o.isAdded"));
    }
}
