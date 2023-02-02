package steps;

import api.clients.ApiHelperChat2Pay;
import api.models.response.accountresponse.AccountSettingsResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

import java.util.Map;

import static api.clients.ApiHelperAccounts.getAccountSettingsResponse;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static utils.Validator.verifyUnauthorisedResponse;

public class AccountSteps {

    @Then("^User gets account settings")
    public void getAccountSettings(Map<String, String> valuesMap) {
        SoftAssertions softly = new SoftAssertions();
        String authToken = valuesMap.get("activationKey");
        if (authToken.equals("token")) {
            authToken = ApiHelperChat2Pay.token.get();
        }
        Response response = getAccountSettingsResponse(authToken);
        int statusCode = response.getStatusCode();
        int expectedResponseCode = parseInt(valuesMap.get("responseCode"));

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                AccountSettingsResponse settings = response.as(AccountSettingsResponse.class);

                softly.assertThat(valuesMap.get("accountId")).isEqualTo(settings.getAccountId());
                softly.assertThat(Boolean.valueOf(valuesMap.get("showTutorial"))).isEqualTo(settings.isShowTutorial());

            } else if (expectedResponseCode == 401) {
                verifyUnauthorisedResponse(valuesMap, response);
            }
            softly.assertAll();
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }
}
