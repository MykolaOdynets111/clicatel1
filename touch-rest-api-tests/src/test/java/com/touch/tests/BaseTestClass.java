package com.touch.tests;

import com.touch.actions.*;
import com.touch.models.EndPointsClass;
import com.touch.models.touch.integration.IntegrationUserLoginMC2Response;
import com.touch.models.touch.tenant.TenantResponseV5;
import com.touch.utils.TestingEnvProperties;
import com.touch.utils.reporter.CustomReport;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import com.touch.engines.RequestEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Listeners(value = CustomReport.class)
public class BaseTestClass {
    RequestEngine requestEngine = new RequestEngine();
    com.clickatell.engines.RequestEngine requestEngineMC2 = new com.clickatell.engines.RequestEngine();
    UserMC2Actions userActions = new UserMC2Actions(requestEngineMC2);
    UserProfileActions userProfileActions = new UserProfileActions(requestEngine);
    TenantActions tenantActions = new TenantActions(requestEngine);
    AgentActions agentActions = new AgentActions(requestEngine);
    DepartmentActions departmentActions = new DepartmentActions(requestEngine);
    ChatsActions chatsActions = new ChatsActions(requestEngine);
    IntegrationActions integrationActions = new IntegrationActions(requestEngine);



//    @BeforeSuite(alwaysRun = true)
//
//    public void beforeSuite() {
//        token = getToken();
//
//    }
    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        String token = getToken();
        Response response = requestEngine.getRequest(EndPointsClass.APP_CONFIG_PROFILE, new Header("Authorization", token));
        List<String> lines = new ArrayList<String>();
        lines.add(response.getBody().jsonPath().getString("version"));
        // lines.add("   " + response.getBody().jsonPath().getString("profile"));
        Path file = Paths.get(".VERSION");
        try {
            Files.write(file, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        removeAllTestTenants(token);
    }
    public String getToken(){
//        IntegrationUserLoginMC2Response response = integrationActions.callGivenAction(TestingEnvProperties.getPropertyByName("integration.mc2.name"), "doMC2Login", "--context_param parameters={\"password\": \"" + TestingEnvProperties.getPropertyByName("integration.mc2.password") + "\", \"email\": \"" + TestingEnvProperties.getPropertyByName("integration.mc2.login") + "\"}").as(IntegrationUserLoginMC2Response.class);
//        Response signInResponse = integrationActions.callGivenAction(TestingEnvProperties.getPropertyByName("integration.mc2.name"), "doMC2SignIn", "--context_param parameters={\"token\":\"" + response.getResponseJson().getToken() + "\",\"accountId\":\"" + response.getResponseJson().getAccounts().get(0).getId() + "\"}");
//        return signInResponse.jsonPath().getString("responseJson.token");
        return userActions.loginAsAdminUserAndReturnToken();
    }

    public void removeAllTestTenants(String token) {
        List<TenantResponseV5> tenantsList = tenantActions.getTenantsList(token);
        for (TenantResponseV5 tenant : tenantsList) {
            if (tenant.getTenantOrgName().contains("Test")) {
                tenantActions.deleteTenant(tenant.getId(), token);
            }
        }
    }
}
