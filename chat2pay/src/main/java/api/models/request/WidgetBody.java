package api.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "environment"
})

public class WidgetBody {

    public WidgetBody(String type, String environment) {
        this.type = type;
        this.environment = environment;
    }

    @JsonProperty("type")
    private String type;

    @JsonProperty("environment")
    private String environment;
}
