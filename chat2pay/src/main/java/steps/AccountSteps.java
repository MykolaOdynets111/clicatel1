package steps;

import api.clients.ApiHelperAccounts;
import api.models.request.AccountSettingsPropertyBody;
import api.models.response.accountresponse.AccountSettingsResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.Map;

import static api.clients.ApiHelperAccounts.getAccountSettingsResponse;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.verifyUnauthorisedResponse;

public class AccountSteps extends GeneralSteps {

    @Then("^User gets account settings")
    public void getAccountSettings(Map<String, String> valuesMap) {
        Response response = getAccountSettingsResponse(getActivationKey(valuesMap));
        int statusCode = response.getStatusCode();
        int expectedResponseCode = parseInt(valuesMap.get("o.responseCode"));

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                AccountSettingsResponse settings = response.as(AccountSettingsResponse.class);

                softly.assertThat(valuesMap.get("o.accountId")).isEqualTo(settings.getAccountId());
                softly.assertThat(Boolean.valueOf(valuesMap.get("o.showTutorial"))).isEqualTo(settings.isShowTutorial());

            } else if (expectedResponseCode == 401) {
                verifyUnauthorisedResponse(valuesMap, response);
            }
            softly.assertAll();
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }

    @Then("^User updates account settings")
    public void putAccountSettings(Map<String, String> valuesMap) {
        AccountSettingsPropertyBody body = AccountSettingsPropertyBody.builder()
                .showTutorial(Boolean.parseBoolean(valuesMap.get("i.showTutorial")))
                .build();

        Response response = ApiHelperAccounts.putAccountSettings(body, getActivationKey(valuesMap));
        int statusCode = response.getStatusCode();
        int expectedResponseCode = parseInt(valuesMap.get("o.responseCode"));

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                AccountSettingsResponse settings = getAccountSettingsResponse(getActivationKey(valuesMap))
                        .as(AccountSettingsResponse.class);

                assertThat(Boolean.valueOf(valuesMap.get("o.updatedShowTutorial")))
                        .isEqualTo(settings.isShowTutorial());

            } else if (expectedResponseCode == 401) {
                verifyUnauthorisedResponse(valuesMap, response);
            }
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }
}
