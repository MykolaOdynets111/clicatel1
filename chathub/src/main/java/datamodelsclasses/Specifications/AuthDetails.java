package datamodelsclasses.Specifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDetails {

    @JsonProperty("grantType")
    private String grantType;

    @JsonProperty("authPath")
    private String authPath;

    @JsonProperty("refreshPath")
    private String refreshPath;

    @JsonProperty("tokenPath")
    private String tokenPath;

    @JsonProperty("tokenExpirationDurationSeconds")
    private String tokenExpirationDurationSeconds;

    @JsonProperty("scopes")
    private String[] scopes;

    @JsonProperty("authorizationHeaderValuePrefix")
    private String authorizationHeaderValuePrefix;

    @JsonProperty("authType")
    private String authType;
}