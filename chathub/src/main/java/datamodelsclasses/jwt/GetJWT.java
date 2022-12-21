package datamodelsclasses.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetJWT {
    @JsonProperty("token")
    public String token;
}
