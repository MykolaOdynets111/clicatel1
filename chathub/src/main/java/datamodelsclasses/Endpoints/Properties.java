package datamodelsclasses.Endpoints;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sourceRef",
        "label",
        "type",
        "isArray"
})
@Data
@NoArgsConstructor
public class Properties {

    @JsonProperty("sourceRef")
    private String sourceRef;

    @JsonProperty("label")
    private String label;

    @JsonProperty("type")
    private String type;

    @JsonProperty("isArray")
    private Boolean isArray;

    public Properties (String sourceRef, String label,String type,Boolean isArray)
    {    this.setSourceRef(sourceRef);
        this.setLabel(label);
        this.setType(type);
        this.setIsArray(isArray);
    }
}