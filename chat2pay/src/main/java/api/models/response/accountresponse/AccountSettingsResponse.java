package api.models.response.accountresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountId",
        "showTutorial"
})

@Data
public class AccountSettingsResponse {
    @JsonProperty("accountId")
    private String accountId;
    @JsonProperty("showTutorial")
    private boolean showTutorial;
}
