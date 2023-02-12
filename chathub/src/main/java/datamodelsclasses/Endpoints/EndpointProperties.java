package datamodelsclasses.Endpoints;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isArray",
        "label",
        "type",
        "sourceRef"
})
@Data
@NoArgsConstructor
public class EndpointProperties {
    @JsonProperty("isArray")
    private String isArray;

    @JsonProperty("label")
    private String label;

    @JsonProperty("type")
    private String type;

    @JsonProperty("sourceRef")
    private String sourceRef;

    public EndpointProperties(String isArray, String label, String type, String sourceRef) {
        this.setIsArray(isArray);
        this.setLabel(label);
        this.setType(type);
        this.setSourceRef(sourceRef);
        ;
    }
}