package datamodelsclasses.configurations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "providerId",
        "type",
        "name",
        "status",
        "host",
        "createDate",
        "modifiedDate"
})
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Configurations {
    @JsonProperty("id")
    public String id;

    @JsonProperty("providerId")
    public String providerId;

    @JsonProperty("type")
    public String type;

    @JsonProperty("name")
    public String name;

    @JsonProperty("status")
    public String status;

    @JsonProperty("host")
    public String host;

    @JsonProperty("createdDate")
    public String createdDate;

    @JsonProperty("modifiedDate")
    public String modifiedDate;

    public Configurations (String id, String providerId, String type, String name, String status, String host){
        this.setId(id);
        this.setProviderId(providerId);
        this.setType(type);
        this.setName(name);
        this.setStatus(status);
        this.setHost(host);
        this.setCreatedDate(createdDate);
        this.setModifiedDate(modifiedDate);
    }
}
