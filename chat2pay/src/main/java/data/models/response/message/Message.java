package data.models.response.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;


import java.time.LocalDate;

import static datetimeutils.DateTimeHelper.parseToLocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
        "smsMsgConfigComplete",
        "updateTime"
})

@Getter
public class Message {

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

    @JsonProperty("updateTime")
    public String updateTime;

    @JsonIgnore
    public LocalDate getUpdateTime() {
        return parseToLocalDate(this.updateTime);
    }
}

