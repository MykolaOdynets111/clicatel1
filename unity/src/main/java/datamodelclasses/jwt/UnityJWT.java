package datamodelclasses.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnityJWT {
    @JsonProperty("token")
    private String token;
}
