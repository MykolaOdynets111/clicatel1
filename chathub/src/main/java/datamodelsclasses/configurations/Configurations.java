package datamodelsclasses.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
