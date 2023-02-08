package api.models.response.widgetresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name"
})

@Getter
@NoArgsConstructor
public class ConfigStatus {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    public ConfigStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
