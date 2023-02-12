package datamodelsclasses.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReActivateConfiguration {
    @JsonProperty("id")
    public String id;
    @JsonProperty("type")
    public String type;
    @JsonProperty("setupName")
    public String setupName;
    @JsonProperty("createdDate")
    public String createdDate;
    @JsonProperty("modifiedDate")
    public String modifiedDate;
    @JsonProperty("authenticationLink")
    public String authenticationLink;
    @JsonProperty("timeToExpire")
    public String timeToExpire;

    public ReActivateConfiguration(Map<String, String> parameters) {
        this.id = parameters.get("o.id");
        this.type = parameters.get("o.type");
        this.setupName = parameters.get("o.setupName");
        this.timeToExpire = parameters.get("o.timeToExpire");
    }
}
