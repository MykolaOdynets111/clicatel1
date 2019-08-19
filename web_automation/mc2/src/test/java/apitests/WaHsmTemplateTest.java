package apitests;

import apitests.data.language.enums.Language;
import apitests.data.language.enums.LanguageLocale;
import apitests.data.wa.template.*;
import apitests.data.wa.template.enums.WaHsmTemplateTag;
import io.restassured.RestAssured;
import mc2api.auth.AdminAccessTokenProvider;
import mc2api.auth.UserAccessTokenProvider;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static apitests.utils.TestUtils.*;
import static datamanager.MC2Account.TESTING_USER_WITH_WA;
import static datamanager.MC2Account.TESTING_ADMIN;
import static mc2api.endpoints.WaHsmTemplateApi.*;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.http.HttpStatus.SC_ACCEPTED;


public class WaHsmTemplateTest extends BaseApiTest {

    private final static String HSM_TEMPLATE_NAME = "test_hsm_template";
    private final static String HSM_TEMPLATE = "template with one parameter: {{1}}";
    private final static int HSM_TAG_ID = WaHsmTemplateTag.PAYMENT_UPDATE.getId();
    private final static String HSM_LANGUAGE = LanguageLocale.ENGLISH_BRITAIN.getShortName();
    private final static String HSM_LANGUAGE_UPDATE = Language.FRENCH.getShortName();

    private String simpleUserAuthToken;
    private String adminUserAuthToken;
    private String hsmTemplateId;

    @BeforeClass
    public void authAccounts() {
        simpleUserAuthToken = UserAccessTokenProvider.getThreadLocalToken(TESTING_USER_WITH_WA.getAccountName(), TESTING_USER_WITH_WA.getEmail(), TESTING_USER_WITH_WA.getPass());
        adminUserAuthToken = AdminAccessTokenProvider.getThreadLocalToken(TESTING_ADMIN.getEmail(), TESTING_ADMIN.getPass());

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void createHsmTemplate() {
        CreateHsmTemplateRequest createHsmTemplateRequest = new CreateHsmTemplateRequest(addDateTime(HSM_TEMPLATE_NAME), HSM_TEMPLATE, HSM_TAG_ID, HSM_LANGUAGE);

        CreateHsmTemplateResponse response = jsonRequestBuilder()
                .header(AUTHORIZATION, simpleUserAuthToken + "asd")
                .body(createHsmTemplateRequest)
                .when()
                .post(PLATFORM_WA_HSM_TEMPLATE)
                .then().assertThat().statusCode(SC_ACCEPTED)
                .extract().as(CreateHsmTemplateResponse.class);

        hsmTemplateId = response.getId();
    }

    @Test(dependsOnMethods = "createHsmTemplate")
    public void adminRejectHsmTemplate() {
        String urlWithTemplateId = String.format(PLATFORM_WA_HSM_TEMPLATE_DECLINE, hsmTemplateId);

        jsonRequestBuilder()
                .header(AUTHORIZATION, adminUserAuthToken)
                .when()
                .post(urlWithTemplateId)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "adminRejectHsmTemplate")
    public void adminUpdateHsmTemplateLanguage() {
        UpdateHsmTemplateRequest request = new UpdateHsmTemplateRequest(null, addDateTime(HSM_TEMPLATE_NAME),
                HSM_TAG_ID, HSM_LANGUAGE_UPDATE);

        String urlWithTemplateId = String.format(PLATFORM_ADMIN_WA_HSM_TEMPLATE_UPDATE, hsmTemplateId);

        jsonRequestBuilder()
                .header(AUTHORIZATION, adminUserAuthToken)
                .body(request.toJsonRequest())
                .when()
                .put(urlWithTemplateId)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "adminRejectHsmTemplate")
    public void userGetHsmTemplate() {
        UpdateHsmTemplateRequest request = new UpdateHsmTemplateRequest(null, addDateTime(HSM_TEMPLATE_NAME),
                HSM_TAG_ID, HSM_LANGUAGE_UPDATE);

        String urlWithTemplateId = String.format(PLATFORM_WA_HSM_TEMPLATE_GET_BY_ID, hsmTemplateId);

        WaHsmTemplateResponse authorization = jsonRequestBuilder()
                .header(AUTHORIZATION, simpleUserAuthToken)
                .body(request.toJsonRequest())
                .when()
                .get(urlWithTemplateId)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(WaHsmTemplateResponse.class);

        Assert.assertEquals(HSM_LANGUAGE_UPDATE, authorization.getLanguage().getShortName());
    }

    @Test(dependsOnMethods = "userGetHsmTemplate")
    public void getHsmTemplateForAdmin() {
        UpdateHsmTemplateRequest request = new UpdateHsmTemplateRequest(null, addDateTime(HSM_TEMPLATE_NAME),
                HSM_TAG_ID, HSM_LANGUAGE_UPDATE);

        String urlWithTemplateId = String.format(PLATFORM_WA_HSM_TEMPLATE_GET_BY_ID, hsmTemplateId);

        WaHsmTemplateResponse authorization = jsonRequestBuilder()
                .header(AUTHORIZATION, adminUserAuthToken)
                .body(request.toJsonRequest())
                .when()
                .get(urlWithTemplateId)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(WaHsmTemplateResponse.class);

        Assert.assertEquals(HSM_LANGUAGE_UPDATE, authorization.getLanguage().getShortName());
    }

    @Test(dependsOnMethods = "getHsmTemplateForAdmin")
    public void adminApproveHsmTemplate() {
        String urlWithTemplateId = String.format(PLATFORM_WA_HSM_TEMPLATE_APPROVE, hsmTemplateId);

        jsonRequestBuilder()
                .header(AUTHORIZATION, adminUserAuthToken)
                .when()
                .post(urlWithTemplateId)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);
    }
}
