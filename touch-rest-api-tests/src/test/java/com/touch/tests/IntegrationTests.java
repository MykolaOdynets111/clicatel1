package com.touch.tests;

import com.touch.models.touch.integration.IntegrationUserLoginMC2Response;
import com.touch.utils.TestingEnvProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class IntegrationTests extends BaseTestClass {

    IntegrationUserLoginMC2Response response;
    String signInToken;
    String env = TestingEnvProperties.getPropertyByName("integration.mc2.name");
    String login = TestingEnvProperties.getPropertyByName("integration.mc2.login");
    String password = TestingEnvProperties.getPropertyByName("integration.mc2.password");

    @BeforeClass
    public void beforeClass() {
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
        Response loginResponse = integrationActions.callGivenAction(env, "doMC2Login", "--context_param parameters={\"password\": \""+password+"\", \"email\": \""+login+"\"}");
        Assert.assertEquals(loginResponse.getStatusCode(), 200);
        String s = loginResponse.asString();
        response = loginResponse.as(IntegrationUserLoginMC2Response.class);
        Assert.assertTrue(response.getResponseJson().getToken()!=null&&!response.getResponseJson().getToken().equals("null"),"Token == null");
    }

    @Test(dependsOnMethods = {"doMC2LoginTestForMC2"})
    public void doMC2SignInTestForMC2() {
        Response signInResponse = integrationActions.callGivenAction(env, "doMC2SignIn", "--context_param parameters={\"token\":\"" + response.getResponseJson().getToken() + "\",\"accountId\":\"" + response.getResponseJson().getAccounts().get(0).getId() + "\"}");
        Assert.assertEquals(signInResponse.getStatusCode(), 200);
        signInToken = signInResponse.jsonPath().getString("responseJson.token");
        Assert.assertTrue(signInToken!=null&&!signInToken.equals("null"),"Token == null");
    }

    @Test(dependsOnMethods = {"doMC2SignInTestForMC2"})
    public void doMC2GetBalanceTestForMC2() {
        Response getBalanceResponse = integrationActions.callGivenAction(env, "doMC2GetBalance", "--context_param parameters={\"token\":\"" + signInToken + "\"}");
        Assert.assertEquals(getBalanceResponse.getStatusCode(), 200);
        Assert.assertTrue(getBalanceResponse.jsonPath().getString("responseJson.balance")!=null&&!getBalanceResponse.jsonPath().getString("responseJson.balance").equals("null"),"Balance == null");
    }
}
