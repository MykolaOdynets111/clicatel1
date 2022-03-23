package datamanager.jacksonschemas.orca;
import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"userName"
})
public class UserInfo {

    public UserInfo(String userName) {
        this.userName = userName;
    }

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("userName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
