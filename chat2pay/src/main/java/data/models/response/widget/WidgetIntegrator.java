package data.models.response.widget;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "name",
        "status",
        "applicationId"
})

@Getter
public class WidgetIntegrator {

    @JsonProperty("applicationId")
    private String applicationId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private String status;
    @JsonProperty("type")
    private String type;
}
