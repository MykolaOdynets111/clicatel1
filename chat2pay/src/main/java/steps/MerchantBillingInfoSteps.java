package steps;

import api.clients.ApiHelperMerchantBillingInfo;
import api.models.request.MerchantBillingInfoBody;
import api.models.response.merchantbillinginfo.MerchantBillingInfoResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.Map;

import static java.lang.String.format;
import static utils.Validator.checkResponseCode;
import static utils.Validator.validateErrorResponse;

public class MerchantBillingInfoSteps extends GeneralSteps {

    private Response response;

    @Then("^User updates Merchant's Billing Info for newly created widget$")
    public void postMerchantBillingInfoCreatedWidget(Map<String, String> dataMap) {
        MerchantBillingInfoBody body = new MerchantBillingInfoBody();
        switch (getWidgetId(dataMap)) {
            case "valid":
                response = ApiHelperMerchantBillingInfo.postMerchantBillingInfoCreatedWidget(createdWidgetId.get(), body);
                checkResponseCode(response, getResponseCode(dataMap));
                compareResponseWithExpectedData(dataMap, response.as(MerchantBillingInfoResponse.class));
                MerchantBillingInfoResponse getMerchantBillingInfoResponse = ApiHelperMerchantBillingInfo
                        .getMerchantBillingInfoCreatedWidget(createdWidgetId.get())
                        .as(MerchantBillingInfoResponse.class);
                compareResponseWithExpectedData(dataMap, getMerchantBillingInfoResponse);
                break;
            case "non_existed":
                response = ApiHelperMerchantBillingInfo.postMerchantBillingInfoCreatedWidget(getWidgetId(dataMap), body);
                checkResponseCode(response, getResponseCode(dataMap));
                validateErrorResponse(response, dataMap);
                break;
            case "skip_posting":
                //this case is added for checking negative tests for GET and DELETE endpoint
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", getWidgetId(dataMap)));
        }
    }

    @Then("^User gets Merchant's Billing Info for newly created widget$")
    public void getMerchantBillingInfoCreatedWidget(Map<String, String> dataMap) {
        switch (getWidgetId(dataMap)) {
            case "valid":
                response = getMerchantBillingInfoResponse(dataMap, createdWidgetId.get());
                compareResponseWithExpectedData(dataMap, response.as(MerchantBillingInfoResponse.class));
                break;
            case "skip_posting":
                response = getMerchantBillingInfoResponse(dataMap, createdWidgetId.get());
                validateErrorResponse(response, dataMap);
                break;
            case "non_existed":
                response = getMerchantBillingInfoResponse(dataMap, getWidgetId(dataMap));
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", getWidgetId(dataMap)));
        }
    }

    @Then("^User deletes Merchant's Billing Info for newly created widget$")
    public void deleteMerchantBillingInfoCreatedWidget(Map<String, String> dataMap) {
        switch (getWidgetId(dataMap)) {
            case "valid":
                response = deleteMerchantBillingInfoResponse(dataMap, createdWidgetId.get());
                Response getResponse = ApiHelperMerchantBillingInfo.getMerchantBillingInfoCreatedWidget(createdWidgetId.get());
                checkResponseCode(getResponse, 404);
                break;
            case "skip_posting":
                response = deleteMerchantBillingInfoResponse(dataMap, createdWidgetId.get());
                validateErrorResponse(response, dataMap);
                break;
            case "non_existed":
                response = deleteMerchantBillingInfoResponse(dataMap, getWidgetId(dataMap));
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", getWidgetId(dataMap)));
        }
    }

    private void compareResponseWithExpectedData(Map<String, String> dataMap, MerchantBillingInfoResponse getResponse) {
        softly.assertThat(dataMap.get("o.email")).isEqualTo(getResponse.email);
        softly.assertThat(dataMap.get("o.addressLine1")).isEqualTo(getResponse.addressLine1);
        softly.assertThat(dataMap.get("o.addressLine2")).isEqualTo(getResponse.addressLine2);
        softly.assertThat(Integer.valueOf(dataMap.get("o.state"))).isEqualTo(getResponse.state.id);
        softly.assertThat(Integer.valueOf(dataMap.get("o.country"))).isEqualTo(getResponse.country.id);
        softly.assertThat(dataMap.get("o.postalCode")).isEqualTo(getResponse.postalCode);
        softly.assertThat(dataMap.get("o.city")).isEqualTo(getResponse.city);
        softly.assertThat(dataMap.get("o.companyName")).isEqualTo(getResponse.companyName);
        softly.assertThat(dataMap.get("o.phone")).isEqualTo(getResponse.phone);
        softly.assertAll();
    }

    private Response getMerchantBillingInfoResponse(Map<String, String> dataMap, String widgetId) {
        Response getResponse = ApiHelperMerchantBillingInfo.getMerchantBillingInfoCreatedWidget(widgetId);
        checkResponseCode(getResponse, getResponseCode(dataMap));
        return getResponse;
    }

    private Response deleteMerchantBillingInfoResponse(Map<String, String> dataMap, String widgetId) {
        Response deleteResponse = ApiHelperMerchantBillingInfo.deleteMerchantBillingInfoCreatedWidget(widgetId);
        checkResponseCode(deleteResponse, getResponseCode(dataMap));
        return deleteResponse;
    }
}
