package api.models.response.paymentgatewaysettingsresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "paymentLinkRef",
        "transactionStatus",
        "transactionStatusId",
        "timestamp",
        "paymentLink",
        "paymentLinkTTL",
        "paymentPageTTL"
})

@Data
public class PaymentLinkResponse {
    @JsonProperty("paymentLinkRef")
    public String paymentLinkRef;

    @JsonProperty("transactionStatus")
    public String transactionStatus;

    @JsonProperty("transactionStatusId")
    public int transactionStatusId;

    @JsonProperty("timestamp")
    public String timestamp;

    @JsonProperty("paymentLink")
    public String paymentLink;

    @JsonProperty("paymentLinkTTL")
    public int paymentLinkTTL;

    @JsonProperty("paymentPageTTL")
    public int paymentPageTTL;
}
