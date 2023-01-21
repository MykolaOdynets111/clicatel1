package datamodelsclasses.Endpoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EndpointDetail {
    @JsonProperty("id")
    private String id;

    @JsonProperty("operationName")
    private String operationName;

    @JsonProperty("requestParameters")
    private List<RequestParameters> requestParameters;

    @JsonProperty("responseSample")
    private List<ResponseSample> responseSample;
}