package com.touch.tests;

import com.touch.actions.*;
import com.touch.models.EndPointsClass;
import com.touch.models.touch.agent.AgentCredentialsDto;
import com.touch.models.touch.agent.AgentResponse;
import com.touch.models.touch.tenant.Mc2AccountRequest;
import com.touch.models.touch.tenant.TenantRequest;
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
    AuthActions authActions = new AuthActions(requestEngine);
    CardsActions cardsActions = new CardsActions(requestEngine);
    public String token;
    public String testToken;
    public TenantResponseV5 testTenant;

    @BeforeClass
    public void beforeClass() {
        token = getToken();
        testTenant = getTestTenant1();
        testToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.email"), TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.password"));

    }

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
//        removeAllTestTenants(token);
    }

    public String getToken() {
        return userActions.loginAsAdminUserAndReturnToken();
    }

    public TenantResponseV5 getTestTenant1() {
        for (TenantResponseV5 tenant : tenantActions.getTenantsList(token)) {
            if (tenant.getTenantOrgName().equals("AutoVerificationTenant"))
                return tenant;
        }
        return addAutoTestTenant1();
    }

    public TenantResponseV5 getTestTenant2() {
        for (TenantResponseV5 tenant : tenantActions.getTenantsList(token)) {
            if (tenant.getTenantOrgName().equals("AutoVerTenant2"))
                return tenant;
        }
        return addAutoTestTenant1();
    }

    public AgentResponse getTestAgent(String testToken) {
        String jid = agentActions.getCredentials(testToken, AgentCredentialsDto.class).getJid();
        return agentActions.getListOfAgents(jid, token, AgentResponse.class);
    }

    public String getToken(String login, String password) {
        return userActions.loginUserToMC2AndReturnToken(login, password);
    }

    public void removeAllTestTenants(String token) {
        List<TenantResponseV5> tenantsList = tenantActions.getTenantsList(token);
        for (TenantResponseV5 tenant : tenantsList) {
            if (tenant.getTenantOrgName().contains("Test") || tenant.getTenantOrgName().contains("test")) {
                tenantActions.deleteTenant(tenant.getId(), token);
            }
        }
    }

    public TenantResponseV5 addAutoTestTenant2() {
        TenantRequest testTenantRequest = new TenantRequest();
        testTenantRequest.setAccountId(null);
        testTenantRequest.setTenantOrgName("AutoVerTenant2");
        testTenantRequest.setContactEmail("automationTenant2@sink.sendgrid.net");
        testTenantRequest.setDescription("automation2");
        testTenantRequest.setShortDescription("auto verification2");
        testTenantRequest.setTenantName("AutoVerificationTenant2");
        Mc2AccountRequest mc2Account = new Mc2AccountRequest();
        mc2Account.setFirstName(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.firstName"));
        mc2Account.setLastName(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.lastName"));
        mc2Account.setEmail(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.email"));
        mc2Account.setPassword(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.password"));
        testTenantRequest.setMc2AccountRequest(mc2Account);
        TenantResponseV5 tenant = tenantActions.createNewTenantInTouchSide(testTenantRequest, token, TenantResponseV5.class);
        String accountId = tenant.getAccountId();
        String accountName = tenant.getTenantOrgName();
        String email = testTenantRequest.getMc2AccountRequest().getEmail();
        String firstName = testTenantRequest.getMc2AccountRequest().getFirstName();
        String lastName = testTenantRequest.getMc2AccountRequest().getLastName();
        String password = testTenantRequest.getMc2AccountRequest().getPassword();
        userActions.signUpAndLoginWithNewUser(accountId, accountName, email, firstName, lastName, password);
        return tenant;
    }

    public TenantResponseV5 addAutoTestTenant1() {
        TenantRequest testTenantRequest = new TenantRequest();
        testTenantRequest.setAccountId(null);
        testTenantRequest.setTenantOrgName("AutoVerificationTenant");
        testTenantRequest.setContactEmail("automationTenant@sink.sendgrid.net");
        testTenantRequest.setDescription("automation");
        testTenantRequest.setShortDescription("auto verification");
        testTenantRequest.setTenantName("AutoVerificationTenant");
        Mc2AccountRequest mc2Account = new Mc2AccountRequest();
        mc2Account.setFirstName(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.firstName"));
        mc2Account.setLastName(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.lastName"));
        mc2Account.setEmail(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.email"));
        mc2Account.setPassword(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.password"));
        testTenantRequest.setMc2AccountRequest(mc2Account);
        TenantResponseV5 tenant = tenantActions.createNewTenantInTouchSide(testTenantRequest, token, TenantResponseV5.class);
        String accountId = tenant.getAccountId();
        String accountName = tenant.getTenantOrgName();
        String email = testTenantRequest.getMc2AccountRequest().getEmail();
        String firstName = testTenantRequest.getMc2AccountRequest().getFirstName();
        String lastName = testTenantRequest.getMc2AccountRequest().getLastName();
        String password = testTenantRequest.getMc2AccountRequest().getPassword();
        userActions.signUpAndLoginWithNewUser(accountId, accountName, email, firstName, lastName, password);
        return tenant;
    }

    public String getTokenForNewTenantWithNewAccount(TenantResponseV5 tenantResponse, TenantRequest tenantRequest) {
        String accountId = tenantResponse.getAccountId();
        String accountName = tenantResponse.getTenantOrgName();
        String email = tenantRequest.getMc2AccountRequest().getEmail();
        String firstName = tenantRequest.getMc2AccountRequest().getFirstName();
        String lastName = tenantRequest.getMc2AccountRequest().getLastName();
        String password = tenantRequest.getMc2AccountRequest().getPassword();
        return userActions.signUpAndLoginWithNewUser(accountId, accountName, email, firstName, lastName, password);
    }
}
