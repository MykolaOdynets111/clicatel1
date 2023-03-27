package data.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

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

@Getter
public class PaymentBody {

    @JsonProperty("channel")
    private String channel = "sms";

    @JsonProperty("to")
    private String to = "447938556403";

    @JsonProperty("currency")
    private String currency = "ZAR";

    @JsonProperty("orderNumber")
    private String orderNumber = "001";

    @JsonProperty("subTotalAmount")
    private String subTotalAmount = "100";

    @JsonProperty("taxAmount")
    private String taxAmount = "0.0";

    @JsonProperty("totalAmount")
    private String totalAmount = "100.0";

    @JsonProperty("timestamp")
    private String timestamp = "2021-04-27T17:35:58.000+0000";

    @JsonProperty("additionalData")
    private AdditionalData additionalData = AdditionalData.builder().departmentId("567").departmentName("Sales").build();

    @JsonProperty("paymentGatewaySettingsId")
    private String paymentGatewaySettingsId;

    @JsonProperty("returnPaymentLink")
    private String returnPaymentLink = "true";

    @JsonProperty("paymentReviewAutoReversal")
    private String paymentReviewAutoReversal = "false";

    @JsonProperty("applicationId")
    private String applicationId;

    @JsonProperty("transactionType")
    private String transactionType = "authorization";

    public PaymentBody(Map<String, String> parameters, String paymentGatewaySettingsId, String applicationID) {
        this.channel = (parameters.get("i.channel"));
        this.to = (parameters.get("i.to"));
        this.currency = (parameters.get("i.currency"));
        this.orderNumber = (parameters.get("i.orderNumber"));
        this.subTotalAmount = (parameters.get("i.subTotalAmount"));
        this.taxAmount = (parameters.get("i.taxAmount"));
        this.totalAmount = (parameters.get("i.totalAmount"));
        this.timestamp = (parameters.get("i.timestamp"));
        this.additionalData = AdditionalData.builder()
                .departmentId(parameters.get("i.departmentId"))
                .departmentName(parameters.get("i.departmentName")).build();
        this.paymentGatewaySettingsId = (paymentGatewaySettingsId);
        this.returnPaymentLink = (parameters.get("i.returnPaymentLink"));
        this.paymentReviewAutoReversal = (parameters.get("i.paymentReviewAutoReversal"));
        this.applicationId = (applicationID);
        this.transactionType = (parameters.get("i.transactionType"));
    }

    public PaymentBody(String paymentGatewaySettingsId, String applicationID) {
        this.paymentGatewaySettingsId = (paymentGatewaySettingsId);
        this.applicationId = (applicationID);
    }
}
