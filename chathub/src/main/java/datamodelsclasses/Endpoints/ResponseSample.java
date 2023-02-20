package datamodelsclasses.Endpoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Properties;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseSample {

    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("properties")
    private List<Properties> properties;

}