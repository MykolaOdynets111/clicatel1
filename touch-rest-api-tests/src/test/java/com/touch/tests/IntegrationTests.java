package com.touch.tests;

import com.touch.models.mc2.userprofile.User;
import com.touch.models.touch.integration.IntegrationUserLoginMC2Response;
import com.touch.models.touch.tenant.Mc2AccountRequest;
import com.touch.models.touch.tenant.TenantRequest;
import com.touch.models.touch.tenant.TenantResponseV5;
import com.touch.utils.TestingEnvProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class IntegrationTests extends BaseTestClass {
    String token;
    IntegrationUserLoginMC2Response response;
    String signInToken;
    String env = TestingEnvProperties.getPropertyByName("integration.mc2.name");
    String login = TestingEnvProperties.getPropertyByName("integration.mc2.login");
    String password = TestingEnvProperties.getPropertyByName("integration.mc2.password");

    @BeforeClass
    public void beforeClass() {
        token = getToken();

//        TenantRequest testTenantRequest = new TenantRequest();
//        testTenantRequest.setAccountId(null);
//        testTenantRequest.setTenantOrgName("AutoVerificationTenant2");
//        testTenantRequest.setContactEmail("automationTenant2@sink.sendgrid.net");
//        testTenantRequest.setDescription("automation2");
//        testTenantRequest.setShortDescription("auto verification2");
//        testTenantRequest.setTenantName("AutoVerificationTenant2");
//        Mc2AccountRequest mc2Account = new Mc2AccountRequest();
//        mc2Account.setFirstName("automationfirstName2");
//        mc2Account.setLastName("automationlastName2");
//        mc2Account.setEmail("automationToucTenant2@sink.sendgrid.net");
//        mc2Account.setPassword("passwordluxcnfv4");
//        testTenantRequest.setMc2AccountRequest(mc2Account);
//        TenantResponseV5 tenant = tenantActions.createNewTenantInTouchSide(testTenantRequest, token, TenantResponseV5.class);
//        String accountId = tenant.getAccountId();
//        String accountName = tenant.getTenantOrgName();
//        String email = testTenantRequest.getMc2AccountRequest().getEmail();
//        String firstName = testTenantRequest.getMc2AccountRequest().getFirstName();
//        String lastName = testTenantRequest.getMc2AccountRequest().getLastName();
//        String password = testTenantRequest.getMc2AccountRequest().getPassword();
//        userActions.signUpAndLoginWithNewUser(accountId, accountName, email, firstName, lastName, password);

    }

    @Test
    public void verifyThatWeAllowToGetUserProfileFromMC2() {
        Response userProfileResponse = userActions.getUserProfile(token);
        Assert.assertEquals(userProfileResponse.getStatusCode(), 200);
        User userProfile = userProfileResponse.as(User.class);
        Assert.assertEquals(userProfile.getEmail(), TestingEnvProperties.getPropertyByName("touch.user.admin.login"));
    }

    @Test
    public void getSupportTicketsStatusesTestForSalesForce() {
        Assert.assertEquals(integrationActions.callGivenAction("salesforce", "getSupportTicketsStatuses", "{\"userNumber\" : \"2c9f830756e0e99f0156e13ed4ed003d\"}").getStatusCode(), 200);
    }

    @Test
    public void doSalesforceNewCaseTestForSalesForce() {
        Assert.assertEquals(integrationActions.callGivenAction("salesforce", "doSalesforceNewCase", "{\"Subject\" : \"Case12377\", \"Priority\" : \"hign\", \"Status\" : \"New\", \"Description\" : \"Case123 description\", \"SuppliedEmail\" : \"test@example.com\", \"SuppliedPhone\" : \"1-230-000-1609\" }").getStatusCode(), 200);
    }

    @Test
    public void getSupportTicketStatusTestForSalesForce() {
        Assert.assertEquals(integrationActions.callGivenAction("salesforce", "getSupportTicketStatus", "{\"Id\":\"50019000002GrsGAAS\"}").getStatusCode(), 200);
    }

    @Test
    public void echoTestForEcho() {
        Assert.assertEquals(integrationActions.callGivenAction("echo", "echo", "{}").getStatusCode(), 200);
    }

    @Test
    public void doMC2LoginTestForMC2() {
        Response loginResponse = integrationActions.callGivenAction(env, "doMC2Login", "--context_param parameters={\"password\": \"" + password + "\", \"email\": \"" + login + "\"}");
        Assert.assertEquals(loginResponse.getStatusCode(), 200);
        response = loginResponse.as(IntegrationUserLoginMC2Response.class);
        Assert.assertTrue(response.getResponseJson().getToken() != null && !response.getResponseJson().getToken().equals("null"), "Token == null");
    }

    @Test(dependsOnMethods = {"doMC2LoginTestForMC2"})
    public void doMC2SignInTestForMC2() {
        Response signInResponse = integrationActions.callGivenAction(env, "doMC2SignIn", "--context_param parameters={\"token\":\"" + response.getResponseJson().getToken() + "\",\"accountId\":\"" + response.getResponseJson().getAccounts().get(0).getId() + "\"}");
        Assert.assertEquals(signInResponse.getStatusCode(), 200);
        signInToken = signInResponse.jsonPath().getString("responseJson.token");
        Assert.assertTrue(signInToken != null && !signInToken.equals("null"), "Token == null");
    }

    @Test(dependsOnMethods = {"doMC2SignInTestForMC2"})
    public void doMC2GetBalanceTestForMC2() {
        Response getBalanceResponse = integrationActions.callGivenAction(env, "doMC2GetBalance", "--context_param parameters={\"token\":\"" + signInToken + "\"}");
        Assert.assertEquals(getBalanceResponse.getStatusCode(), 200);
        Assert.assertTrue(getBalanceResponse.jsonPath().getString("responseJson.balance") != null && !getBalanceResponse.jsonPath().getString("responseJson.balance").equals("null"), "Balance == null");
    }
}
