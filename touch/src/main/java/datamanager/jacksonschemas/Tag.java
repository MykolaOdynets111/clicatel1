package datamanager.jacksonschemas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "value",
        "numberOfUse",
        "enabled",
        "createdBy",
        "createdDate",
        "lastUsedDate"
})
public class Tag {

    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private String value;
    @JsonProperty("numberOfUse")
    private String numberOfUse;
    @JsonProperty("enabled")
    private String enabled;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("lastUsedDate")
    private String lastUsedDate;
}