package datamodelsclasses.Endpoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestParameters {

    @JsonProperty("id")
    private String id;

    @JsonProperty("label")
    private String label;

    @JsonProperty("placeholder")
    private String placeholder;

    @JsonProperty("default")
    private String default_Value;

    @JsonProperty("required")
    private Boolean required;

    @JsonProperty("constraints")
    @NotNull
    private List<String> constraints;

    @JsonProperty("parameterType")
    private String parameterType;

    @JsonProperty("availableOptions")
    private String availableOptions;

    @JsonProperty("isArray")
    private Boolean isArray;

    @JsonProperty("presentationType")
    private String presentationType;

    @JsonProperty("repeatableGroupId")
    private String repeatableGroupId;

    @JsonProperty("repeatableGroupName")
    private String repeatableGroupName;

    @JsonProperty("placementType")
    private String placementType;

    @JsonProperty("destinationPath")
    private String destinationPath;
}