package com.touch.tests;

import com.touch.actions.IntegrationActions;
import com.touch.actions.UserMC2Actions;
import com.touch.engines.RequestEngine;
import com.touch.models.EndPointsClass;
import com.touch.models.mc2.userprofile.User;
import com.touch.models.touch.integration.IntegrationUserLoginMC2Response;
import com.touch.utils.TestingEnvProperties;
import com.touch.utils.reporter.CustomReport;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
@Listeners(value = CustomReport.class)
public class IntegrationTests {
    RequestEngine requestEngine = new RequestEngine();
    com.clickatell.engines.RequestEngine requestEngineMC2 = new com.clickatell.engines.RequestEngine();
    UserMC2Actions userActions = new UserMC2Actions(requestEngineMC2);
    IntegrationActions integrationActions = new IntegrationActions(requestEngine);
    IntegrationUserLoginMC2Response response;
    String signInToken;
    String env = TestingEnvProperties.getPropertyByName("integration.mc2.name");
    String login = TestingEnvProperties.getPropertyByName("integration.mc2.login");
    String password = TestingEnvProperties.getPropertyByName("integration.mc2.password");



    @Test(dependsOnMethods = {"doMC2LoginTestForMC2"})
    public void verifyThatWeAllowToGetUserProfileFromMC2() {
        Response userProfileResponse = userActions.getUserProfile(signInToken);
        Assert.assertEquals(userProfileResponse.getStatusCode(), 200);
        User userProfile = userProfileResponse.as(User.class);
        Assert.assertEquals(userProfile.getEmail(), login);
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

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        Response response = requestEngine.getRequest(EndPointsClass.APP_CONFIG_PROFILE, new Header("Authorization", signInToken));
        List<String> lines = new ArrayList<String>();
        lines.add(response.getBody().jsonPath().getString("version"));
        // lines.add("   " + response.getBody().jsonPath().getString("profile"));
        Path file = Paths.get(".VERSION");
        try {
            Files.write(file, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
