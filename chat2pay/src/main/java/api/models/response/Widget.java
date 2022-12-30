package api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "configStatus",
        "type",
        "name",
        "id",
        "accountId",
        "environment",
        "createdTime",
        "modifiedTime",
})

@Data
public class Widget {

// ToDo add more fields, according to existed model from swagger

    @JsonProperty("status")
    private String status;

    @JsonProperty("configStatus")
    private String configStatus;

    @JsonProperty("type")
    private String type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("environment")
    private String environment;

    @JsonProperty("createdTime")
    private String createdTime;

    @JsonProperty("modifiedTime")
    private String modifiedTime;

}
