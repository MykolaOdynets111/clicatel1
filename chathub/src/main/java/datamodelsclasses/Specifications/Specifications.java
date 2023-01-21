package datamodelsclasses.Specifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Specifications {
    @JsonProperty("id")
    private String id;

    @JsonProperty("authDetails")
    private AuthDetails authDetails;

    @JsonProperty("version")
    private String version;

    @JsonProperty("openApiSpecS3Key")
    private String openApiSpecS3Key;

}