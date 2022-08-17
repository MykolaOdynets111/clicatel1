package datamanager.jacksonschemas.orca;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"userName"
})
@Data
@NoArgsConstructor
public class UserInfo {

    @JsonProperty("userName")
    private String userName;

    public UserInfo(String userName) {
        this.userName = userName;
    }
}
