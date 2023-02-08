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

    @JsonProperty("type")
    private final String type;

    @JsonProperty("environment")
    private final String environment;

    public WidgetBody(String type, String environment) {
        this.type = type;
        this.environment = environment;
    }
}
