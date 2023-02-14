package api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "paymentLinkRef",
        "transactionStatus",
        "transactionStatusId",
        "additionalData",
        "receiptLinkRef",
        "orderNumber",
        "timestamp",
        "paymentLink",
        "paymentLinkTTL",
        "paymentPageTTL"
})

@Getter
public class PaymentLinkResponse {
    @JsonProperty("paymentLinkRef")
    public String paymentLinkRef;

    @JsonProperty("transactionStatus")
    public String transactionStatus;

    @JsonProperty("transactionStatusId")
    public int transactionStatusId;

    @JsonProperty("additionalData")
    public String additionalData;

    @JsonProperty("receiptLinkRef")
    public String receiptLinkRef;

    @JsonProperty("orderNumber")
    public String orderNumber;

    @JsonProperty("timestamp")
    public String timestamp;

    @JsonProperty("paymentLink")
    public String paymentLink;

    @JsonProperty("paymentLinkTTL")
    public int paymentLinkTTL;

    @JsonProperty("paymentPageTTL")
    public int paymentPageTTL;
}
