package data.models.response.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountId",
        "showTutorial"
})

@Getter
public class AccountSettingsResponse {

    @JsonProperty("accountId")
    public String accountId;
    @JsonProperty("showTutorial")
    public boolean showTutorial;
}
