package datamodelsclasses.providers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "logoUrl",
        "description",
        "moreInfoUrl",
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewProviderBody {

    @JsonProperty("name")
    private String name;

    @JsonProperty("logoUrl")
    private String logoUrl;

    @JsonProperty("description")
    private String description;

    @JsonProperty("moreInfoUrl")
    private String moreInfoUrl;
}
