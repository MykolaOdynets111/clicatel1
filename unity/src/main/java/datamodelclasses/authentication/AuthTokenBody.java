package datamodelclasses.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import datamodelclasses.authentication.Accounts;
import lombok.*;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class AuthTokenBody {
    @JsonProperty("token")
    private String token;
    @JsonProperty("accounts")
    private List<Accounts> accounts = Arrays.asList(new Accounts());
}
