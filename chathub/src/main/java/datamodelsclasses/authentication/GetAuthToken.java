package datamodelsclasses.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class GetAuthToken {
    @JsonProperty("token")
    public String token;
    @JsonProperty("accounts")
    public List<Accounts> accounts = Arrays.asList(new Accounts());
}
