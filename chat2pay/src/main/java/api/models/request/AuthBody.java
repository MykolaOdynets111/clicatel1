package api.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "token",
        "accountId"
})

@Data
@Builder
public class AuthBody {

    @JsonProperty("token")
    private String token;

    @JsonProperty("accountId")
    private String accountId;

    public AuthBody( String token, String accountId  ){
        this.token = token;
        this.accountId = accountId;
    }

}
