package api.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.Map;


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
    private String subTotalAmount;

    @JsonProperty("taxAmount")
    private String taxAmount;

    @JsonProperty("totalAmount")
    private String totalAmount;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("additionalData")
    private AdditionalData additionalData;

    @JsonProperty("paymentGatewaySettingsId")
    private String paymentGatewaySettingsId;

    @JsonProperty("returnPaymentLink")
    private String returnPaymentLink;

    @JsonProperty("paymentReviewAutoReversal")
    private String paymentReviewAutoReversal;

    @JsonProperty("applicationId")
    private String applicationId;

    @JsonProperty("transactionType")
    private String transactionType;

    public PaymentBody(Map<String, String> parameters, String paymentGatewaySettingsId, String applicationID) {
        this.channel = (parameters.get("channel"));
        this.to = (parameters.get("to"));
        this.currency = (parameters.get("currency"));
        this.orderNumber = (parameters.get("orderNumber"));
        this.subTotalAmount = (parameters.get("subTotalAmount"));
        this.taxAmount = (parameters.get("taxAmount"));
        this.totalAmount = (parameters.get("totalAmount"));
        this.timestamp = (parameters.get("timestamp"));
        this.additionalData = (AdditionalData.builder()
                .departmentId(parameters.get("departmentId"))
                .departmentName(parameters.get("departmentName")).build());
        this.paymentGatewaySettingsId = (paymentGatewaySettingsId);
        this.returnPaymentLink = (parameters.get("returnPaymentLink"));
        this.paymentReviewAutoReversal = (parameters.get("paymentReviewAutoReversal"));
        this.applicationId = (applicationID);
        this.transactionType = (parameters.get("transactionType"));

    }

}
