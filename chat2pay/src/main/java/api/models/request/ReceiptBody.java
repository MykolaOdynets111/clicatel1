package api.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "paymentLinkRef",
        "timestamp",
        "receiptLink"
})

@Builder
public class ReceiptBody {

    @JsonProperty("paymentLinkRef")
    private String paymentLinkRef;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("receiptLink")
    private String receiptLink;

}