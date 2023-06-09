package data.models.response.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "TRANSACTION_STATUS_NOTIFICATION",
        "PAYMENT_STATUS_NOTIFICATION",
        "CONFIG_CHANGE_NOTIFICATION"
})

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationUrls {

    @JsonProperty("TRANSACTION_STATUS_NOTIFICATION")
    private String transactionStatusNotification;
    @JsonProperty("PAYMENT_STATUS_NOTIFICATION")
    private String paymentStatusNotification;
    @JsonProperty("CONFIG_CHANGE_NOTIFICATION")
    private String configChangeNotification;
}
