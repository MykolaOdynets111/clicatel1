package api.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "to",
        "currency",
        "orderNumber",
        "subTotalAmount",
        "taxAmount",
        "totalAmount",
        "timestamp",
        "additionalData",
        "paymentGatewaySettingsId",
        "returnPaymentLink",
        "paymentReviewAutoReversal",
        "applicationId",
        "transactionType"
})

@Data
@Builder
public class PaymentBody {

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("to")
    private String to;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("subTotalAmount")
    private int subTotalAmount;

    @JsonProperty("taxAmount")
    private double taxAmount;

    @JsonProperty("totalAmount")
    private double totalAmount;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("additionalData")
    private AdditionalData additionalData;

    @JsonProperty("paymentGatewaySettingsId")
    private String paymentGatewaySettingsId;

    @JsonProperty("returnPaymentLink")
    private boolean returnPaymentLink;

    @JsonProperty("paymentReviewAutoReversal")
    private boolean paymentReviewAutoReversal;

    @JsonProperty("applicationId")
    private String applicationId;

    @JsonProperty("transactionType")
    private String transactionType;

}
