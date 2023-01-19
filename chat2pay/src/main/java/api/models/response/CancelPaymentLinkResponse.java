package api.models.response;

import api.models.request.AdditionalData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "paymentLinkRef",
        "transactionStatus",
        "transactionStatusId",
        "additionalData",
        "timestamp"
})

@Data
public class CancelPaymentLinkResponse {
    @JsonProperty("paymentLinkRef")
    public String paymentLinkRef;

    @JsonProperty("transactionStatus")
    public String transactionStatus;

    @JsonProperty("transactionStatusId")
    public int transactionStatusId;

    @JsonProperty("additionalData")
    public String additionalData;

    @JsonProperty("timestamp")
    public String timestamp;
}
