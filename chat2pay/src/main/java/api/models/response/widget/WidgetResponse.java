package api.models.response.widget;

import api.models.response.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "paymentGatewaySettingsId",
        "accounts",
})

@Data
public class WidgetResponse {

    @JsonProperty("paymentGatewaySettingsId")
    private String paymentGatewaySettingsId;

    @JsonProperty("accounts")
    private List<Account> accounts;

}
