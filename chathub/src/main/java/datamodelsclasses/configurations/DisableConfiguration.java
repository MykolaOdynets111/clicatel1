package datamodelsclasses.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisableConfiguration {
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
    private String modifiedDate;

    public DisableConfiguration(Map<String,String> parameters) {
        this.id = parameters.get("o.id");
        this.providerId = parameters.get("o.providerId");
        this.type = parameters.get("o.type");
        this.name = parameters.get("o.name");
        this.status = parameters.get("o.status");
        this.host = parameters.get("o.host");
    }
}
