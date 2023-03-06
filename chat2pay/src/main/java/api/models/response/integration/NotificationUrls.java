package api.models.response.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "TRANSACTION_STATUS_NOTIFICATION",
        "PAYMENT_STATUS_NOTIFICATION",
        "CONFIG_CHANGE_NOTIFICATION"
})
@Getter
public class NotificationUrls {

    @JsonProperty("TRANSACTION_STATUS_NOTIFICATION")
    private String transactionStatusNotification;
    @JsonProperty("PAYMENT_STATUS_NOTIFICATION")
    private String paymentStatusNotification;
    @JsonProperty("CONFIG_CHANGE_NOTIFICATION")
    private String configChangeNotification;
}
