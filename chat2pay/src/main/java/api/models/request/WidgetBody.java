package api.models.request;

import api.models.response.widgetresponse.ConfigStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "name",
        "configStatus",
        "environment"
})

@Builder
public class WidgetBody {

    @JsonProperty("type")
    private final String type;
    @JsonProperty("configStatus")
    private final ConfigStatus configStatus;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("environment")
    private final String environment;
}
