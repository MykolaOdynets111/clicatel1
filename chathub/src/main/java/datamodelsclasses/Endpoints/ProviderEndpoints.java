package datamodelsclasses.Endpoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProviderEndpoints {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    public ProviderEndpoints(String id, String name) {
        this.setId(id);
        this.setName(name);
    }
}