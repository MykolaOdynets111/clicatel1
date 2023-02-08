package api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "receiptLinkRef",
        "orderNumber",
        "timestamp",
        "transactionStatus",
        "transactionStatusId"
})

@Getter
public class ReceiptOrderResponse {
    @JsonProperty("receiptLinkRef")
    public String receiptLinkRef;

    @JsonProperty("orderNumber")
    public String orderNumber;

    @JsonProperty("timestamp")
    public String timestamp;

    @JsonProperty("transactionStatus")
    public String transactionStatus;

    @JsonProperty("transactionStatusId")
    public int transactionStatusId;

}
