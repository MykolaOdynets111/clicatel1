package datamodelsclasses.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivateConfiguration {
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
}
