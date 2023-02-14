package api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonPropertyOrder({
        "waPaymentTemplateId",
        "waPaymentTemplateName",
        "waReceiptTemplateId",
        "waReceiptTemplateName",
        "smsPaymentTemplate",
        "smsReceiptTemplate",
        "smsOmniIntegrationId",
        "waOmniIntegrationId",
        "waMsgConfigComplete",
        "smsMsgConfigComplete"
})

@Getter
public class MessageResponse {

    @JsonProperty("waPaymentTemplateId")
    public Object waPaymentTemplateId;

    @JsonProperty("waPaymentTemplateName")
    public Object waPaymentTemplateName;

    @JsonProperty("waReceiptTemplateId")
    public Object waReceiptTemplateId;

    @JsonProperty("waReceiptTemplateName")
    public Object waReceiptTemplateName;

    @JsonProperty("smsPaymentTemplate")
    public String smsPaymentTemplate;

    @JsonProperty("smsReceiptTemplate")
    public String smsReceiptTemplate;

    @JsonProperty("smsOmniIntegrationId")
    public String smsOmniIntegrationId;

    @JsonProperty("waOmniIntegrationId")
    public Object waOmniIntegrationId;

    @JsonProperty("waMsgConfigComplete")
    public boolean waMsgConfigComplete;

    @JsonProperty("smsMsgConfigComplete")
    public boolean smsMsgConfigComplete;
}