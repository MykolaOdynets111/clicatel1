package api.models.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "password"
})

@Data
public class LoginBody {

    @JsonProperty("email")
    public String email;

    @JsonProperty("password")
    public String password;

    public LoginBody( String email, String password  ){
        this.email = email;
        this.password = password;
    }
}
