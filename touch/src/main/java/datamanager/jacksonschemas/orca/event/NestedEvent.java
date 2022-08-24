package datamanager.jacksonschemas.orca.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "eventType",
        "templateId",
        "parameters"
})
@Setter @Getter @ToString @Builder @AllArgsConstructor @NoArgsConstructor
public class NestedEvent {

    @JsonProperty("eventType")
    private String eventType;
    @JsonProperty("templateId")
    private String templateId;
    @JsonProperty("parameters")
    private Map<String, String> parameters;
}
