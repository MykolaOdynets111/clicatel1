package api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "token",
        "accounts"
})
@Data
public class GetAuthToken {
    @JsonProperty("token")
    public String token;
    @JsonProperty("accounts")
    public List<Account> accounts = Arrays.asList(new Account());
}
