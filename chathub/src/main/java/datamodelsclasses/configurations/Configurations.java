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
@NoArgsConstructor

public class Configurations {
    @JsonProperty("id")
    private String id;

    @JsonProperty("providerId")
    private String providerId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("status")
    private String status;

    @JsonProperty("host")
    private String host;

    @JsonProperty("createdDate")
    private String createdDate;

    @JsonProperty("modifiedDate")
    public String modifiedDate;

    public Configurations (String id, String providerId, String type, String name, String status, String host, String createdDate, String modifiedDate){
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
