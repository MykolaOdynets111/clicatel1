package steps;

import api.ApiHelperAccounts;
import data.models.request.widgetconfigurations.AccountSettingsPropertyBody;
import data.models.response.account.AccountSettingsResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.Map;

import static api.ApiHelperAccounts.getAccountSettingsResponse;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.verifyUnauthorisedResponse;

public class AccountSteps extends GeneralSteps {

    @Then("^User gets account settings")
    public void getAccountSettings(Map<String, String> dataMap) {
        Response response = getAccountSettingsResponse(getActivationKey(dataMap));
        int statusCode = response.getStatusCode();
        int expectedResponseCode = getResponseCode(dataMap);

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                AccountSettingsResponse settings = response.as(AccountSettingsResponse.class);

                softly.assertThat(dataMap.get("o.accountId")).isEqualTo(settings.getAccountId());
                softly.assertThat(Boolean.valueOf(dataMap.get("o.showTutorial"))).isEqualTo(settings.isShowTutorial());

            } else if (expectedResponseCode == 401) {
                verifyUnauthorisedResponse(dataMap, response);
            }
            softly.assertAll();
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }

    @Then("^User updates account settings")
    public void putAccountSettings(Map<String, String> dataMap) {
        AccountSettingsPropertyBody body = AccountSettingsPropertyBody.builder()
                .showTutorial(Boolean.parseBoolean(dataMap.get("i.showTutorial")))
                .build();

        Response response = ApiHelperAccounts.putAccountSettings(body, getActivationKey(dataMap));
        int statusCode = response.getStatusCode();
        int expectedResponseCode = getResponseCode(dataMap);

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                AccountSettingsResponse settings = getAccountSettingsResponse(getActivationKey(dataMap))
                        .as(AccountSettingsResponse.class);

                assertThat(Boolean.valueOf(dataMap.get("o.updatedShowTutorial")))
                        .isEqualTo(settings.isShowTutorial());

            } else if (expectedResponseCode == 401) {
                verifyUnauthorisedResponse(dataMap, response);
            }
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }
}
