package steps;


import api.ApiHelperMessagesConfigurations;
import data.models.request.message.MessageBody;
import data.models.response.message.Message;
import data.models.response.widget.Widget;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.Validator;

import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.lang.String.format;
import static utils.Validator.validateErrorResponse;

public class MessageSteps extends GeneralSteps {

    private Response response;

    @Then("^User gets configuration for newly created widget$")
    public void getMessageConfiguration(Map<String, String> dataMap) {
        switch (getWidgetId(dataMap)) {
            case "valid":
                response = ApiHelperMessagesConfigurations.getMessageConfigurationResponse(createdWidgetId.get());
                Validator.checkResponseCode(response, getResponseCode(dataMap));
                break;
            case "non_existed":
                response = ApiHelperMessagesConfigurations.getMessageConfigurationResponse(getWidgetId(dataMap));
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", getWidgetId(dataMap)));
        }
    }

    @Then("^User updates configuration for newly created widget$")
    public void putMessageConfiguration(Map<String, String> dataMap) {
        MessageBody requestBody = MessageBody.builder()
                .waPaymentTemplateId(dataMap.get("i.waPaymentTemplateId"))
                .waPaymentTemplateName(dataMap.get("i.waPaymentTemplateName"))
                .waReceiptTemplateId(dataMap.get("i.waReceiptTemplateId"))
                .waReceiptTemplateName(dataMap.get("i.waReceiptTemplateName"))
                .smsPaymentTemplate(dataMap.get("i.smsPaymentTemplate"))
                .smsReceiptTemplate(dataMap.get("i.smsReceiptTemplate"))
                .build();
        switch (getWidgetId(dataMap)) {
            case "valid":
                response = ApiHelperMessagesConfigurations.putMessageConfiguration(createdWidgetId.get(), requestBody);
                Validator.checkResponseCode(response, getResponseCode(dataMap));
                Message updateResponse = response.as(Message.class);
                softly.assertThat(updateResponse.getUpdateTime())
                        .as(format("widget update date is not equals to %s", LocalDate.now()))
                        .isEqualTo(LocalDate.now());
                softly.assertThat(Boolean.valueOf(dataMap.get("o.waMsgConfigComplete"))).isEqualTo(updateResponse.waMsgConfigComplete);
                softly.assertThat(Boolean.valueOf(dataMap.get("o.smsMsgConfigComplete"))).isEqualTo(updateResponse.smsMsgConfigComplete);

                Message getResponse = ApiHelperMessagesConfigurations.getMessageConfigurationResponse(createdWidgetId.get()).as(Message.class);
                softly.assertThat(Boolean.valueOf(dataMap.get("o.waMsgConfigComplete"))).isEqualTo(getResponse.waMsgConfigComplete);
                softly.assertThat(Boolean.valueOf(dataMap.get("o.smsMsgConfigComplete"))).isEqualTo(getResponse.smsMsgConfigComplete);
                softly.assertThat(dataMap.get("i.waPaymentTemplateId")).isEqualTo(getResponse.waPaymentTemplateId);
                softly.assertThat(dataMap.get("i.waPaymentTemplateName")).isEqualTo(getResponse.waPaymentTemplateName);
                softly.assertThat(dataMap.get("i.waReceiptTemplateId")).isEqualTo(getResponse.waReceiptTemplateId);
                softly.assertThat(dataMap.get("i.waReceiptTemplateName")).isEqualTo(getResponse.waReceiptTemplateName);
                softly.assertThat(dataMap.get("i.smsPaymentTemplate")).isEqualTo(getResponse.smsPaymentTemplate);
                softly.assertThat(dataMap.get("i.smsReceiptTemplate")).isEqualTo(getResponse.smsPaymentTemplate);
                softly.assertAll();
                break;
            case "non_existed":
                response = ApiHelperMessagesConfigurations.putMessageConfiguration(getWidgetId(dataMap), requestBody);
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", getWidgetId(dataMap)));
        }
    }

    @Then("^User gets template usage for templateId$")
    public void getTemplateUsage(Map<String, String> dataMap) {
        response = ApiHelperMessagesConfigurations.getTemplateUsageResponse(dataMap.get("i.templateId"));
        Validator.checkResponseCode(response, getResponseCode(dataMap));
        Widget widget = response.getBody().jsonPath().getList("", Widget.class)
                .stream().filter(w -> w.id.equals(dataMap.get("o.id")))
                .findFirst().orElseThrow(NoSuchElementException::new);
        softly.assertThat(widget.getStatus()).isEqualTo(dataMap.get("o.status"));
        softly.assertThat(widget.getType()).isEqualTo(dataMap.get("o.type"));
        softly.assertThat(widget.getId()).isEqualTo(dataMap.get("o.id"));
        softly.assertThat(widget.getAccountId()).isEqualTo(dataMap.get("o.accountId"));
        softly.assertThat(widget.getEnvironment()).isEqualTo(dataMap.get("o.environment"));
        softly.assertAll();
    }
}