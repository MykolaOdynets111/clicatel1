package steps;

import api.clients.ApiHelperMerchantBillingInfo;
import api.clients.ApiHelperMessagesConfigurations;
import api.clients.ApiHelperWidgets;
import api.models.request.MerchantBillingInfoBody;
import api.models.response.message.Message;
import api.models.response.updatedresponse.UpdatedEntityResponse;
import api.models.response.widget.Widget;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.Validator;

import java.time.LocalDate;
import java.util.Map;

import static java.lang.String.format;
import static utils.Validator.checkResponseCode;
import static utils.Validator.validateErrorResponse;

public class MerchantBillingInfoSteps extends GeneralSteps {

    private Response response;


    @Then("^User updates Merchant's Billing Info for newly created widget$")
    public void updateMerchantBillingInfoCreatedWidget(Map<String, String> dataMap) {
        MerchantBillingInfoBody body = new MerchantBillingInfoBody();
        switch (getWidgetId(dataMap)) {
            case "valid":
                response = ApiHelperMerchantBillingInfo.updateMerchantBillingInfoCreatedWidget(createdWidgetId.get(), body);
                checkResponseCode(response, getResponseCode(dataMap));



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


//                UpdatedEntityResponse updatedEntityResponse = response.as(UpdatedEntityResponse.class);
//                softly.assertThat(updatedEntityResponse.getUpdateTime())
//                        .as(format("widget update date is not equals to %s", LocalDate.now()))
//                        .isEqualTo(LocalDate.now());
//                softly.assertThat(updatedEntityResponse.isShowLinkedApi())
//                        .isEqualTo(Boolean.valueOf(Boolean.parseBoolean(dataMap.get("i.showLinkedApi"))));
//                softly.assertThat(ApiHelperWidgets.getWidget(createdWidgetId.get()).as(Widget.class)
//                                .isShowLinkedApi())
//                        .isEqualTo(Boolean.valueOf(Boolean.parseBoolean(dataMap.get("i.showLinkedApi"))));
//                softly.assertAll();
                break;
            case "non_existed":
                response = ApiHelperMerchantBillingInfo.updateMerchantBillingInfoCreatedWidget(getWidgetId(dataMap), body);

                checkResponseCode(response, getResponseCode(dataMap));
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", getWidgetId(dataMap)));
        }
    }

    @Then("^User gets Merchant's Billing Info for newly created widget$")
    public void getMerchantBillingInfoCreatedWidget(Map<String, String> dataMap) {
        if (getWidgetId(dataMap).equals("valid")) {
            response = ApiHelperMerchantBillingInfo.getMerchantBillingInfoCreatedWidget(createdWidgetId.get());
            checkResponseCode(response, getResponseCode(dataMap));

        }
    }

}
