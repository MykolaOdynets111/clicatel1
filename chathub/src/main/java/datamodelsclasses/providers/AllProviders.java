package datamodelsclasses.providers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Arrays;
import java.util.List;

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
}
