package api.models.response.widgetresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name"
})

@Data
public class ConfigStatus {

    public ConfigStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

}
