package api.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "paymentLinkRef",
        "timestamp",
        "receiptLink"
})

public class ReceiptBody {

    @JsonProperty("paymentLinkRef")
    private final String paymentLinkRef;

    @JsonProperty("timestamp")
    private final String timestamp = "2021-04-27T17:35:58.000+0000";

    @JsonProperty("receiptLink")
    private final String receiptLink = "https://your.domain/receipt";

    public ReceiptBody(String paymentLinkRef) {
        this.paymentLinkRef = paymentLinkRef;
    }
}
