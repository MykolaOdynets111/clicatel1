package data.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "paymentLinkRef",
        "timestamp",
        "receiptLink"
})

@Setter
public class ReceiptBody {

    @JsonProperty("paymentLinkRef")
    private String paymentLinkRef;

    @JsonProperty("timestamp")
    private String timestamp = "2021-04-27T17:35:58.000+0000";

    @JsonProperty("receiptLink")
    private String receiptLink = "https://your.domain/receipt";

}