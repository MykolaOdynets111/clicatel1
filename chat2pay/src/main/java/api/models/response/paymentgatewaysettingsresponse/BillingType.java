package api.models.response.paymentgatewaysettingsresponse;

import api.models.response.PaymentsConfigurationDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name"
})
@Getter
public class BillingType implements PaymentsConfigurationDetail {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;
}