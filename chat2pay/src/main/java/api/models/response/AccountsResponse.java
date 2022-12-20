package api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "token",
        "accounts"
})

@Data
public class AccountsResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("accounts")
    private List<Account> accounts;

}
