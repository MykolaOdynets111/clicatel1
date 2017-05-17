package com.touch.tests;

import com.clickatell.models.user_profiles.UserProfile;
import com.touch.actions.*;
import com.touch.models.EndPointsClass;
import com.touch.models.touch.agent.AgentCredentialsDto;
import com.touch.models.touch.agent.AgentResponse;
import com.touch.models.touch.tenant.Mc2AccountRequest;
import com.touch.models.touch.tenant.TenantRequest;
import com.touch.models.touch.tenant.TenantResponseV5;
import com.touch.utils.StringUtils;
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
import java.io.InputStream;
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
    AnalyticsActions analyticsActions = new AnalyticsActions(requestEngine);
    AppConfigActions appConfigActions = new AppConfigActions(requestEngine);
    RosterActions rosterActions = new RosterActions(requestEngine);
    public String token;
    public String testToken;
    public TenantResponseV5 testTenant;

    @BeforeClass
    public void beforeClass() {
        token = getToken(TestingEnvProperties.getPropertyByName("touch.user.admin.login"), TestingEnvProperties.getPropertyByName("touch.user.admin.password"));
        testToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.email"), TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.password"));
        testTenant = getTestTenant1();


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
        String environment = System.getProperty("tests.env", "testing").toLowerCase();
        if(environment.equals("demo")){
            tenantActions.deleteTenant(testTenant.getId(),testToken);
            TenantResponseV5 testTenant2 = getTestTenant2();
            tenantActions.deleteTenant(testTenant2.getId(),getToken(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.email"), TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.password")));
        }
    }

    public String getToken() {
        return userActions.loginAsAdminUserAndReturnToken();
    }

    public TenantResponseV5 getTestTenant1() {
        for (TenantResponseV5 tenant : tenantActions.getTenantsList(getToken(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.email"), TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.password")))) {
            if (tenant.getTenantOrgName().equalsIgnoreCase("AutoVerificationTenant"))
                return tenant;
        }
        return addAutoTestTenant1();
    }

    public TenantResponseV5 getTestTenant2() {
        for (TenantResponseV5 tenant : tenantActions.getTenantsList(getToken(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.email"), TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.password")))) {
            if (tenant.getTenantOrgName().equals("AutoVerTenant2"))
                return tenant;
        }
        return addAutoTestTenant2();
    }

    public AgentResponse getTestAgent(String testToken) {
        String jid = agentActions.getCredentials(testToken, AgentCredentialsDto.class).getJid();
        return agentActions.getListOfAgents(jid, testToken, AgentResponse.class);
    }

    public String getToken(String login, String password) {
        return userActions.loginUserToMC2AndReturnToken(login, password);
    }

    public void removeAllTestTenants(String token) {
        List<TenantResponseV5> tenantsList = tenantActions.getTenantsList(token);
        for (TenantResponseV5 tenant : tenantsList) {
            if (tenant.getTenantOrgName().contains("MC2RatingTest") || tenant.getTenantOrgName().contains("test")) {
                tenantActions.deleteTenant(tenant.getId(), token);
            }
        }
    }

    public TenantResponseV5 addAutoTestTenant2() {
        String userEmail = TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.email");
        String userPassword = TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.password");
        String userFirstName = TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.firstName");
        String userLastName = TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.lastName");
        UserProfile userProfile;
        try{
            userProfile = userActions.getUserProfileInMC2(userEmail, userPassword);
        }catch(Exception e){

            userProfile = userActions.createNewUser("accountname_" + StringUtils.generateRandomString(10),userEmail,userFirstName, userLastName, userPassword);
        }
        TenantRequest testTenantRequest = new TenantRequest();
        testTenantRequest.setAccountId(userProfile.getAccounts().get(0).getId());
        testTenantRequest.setTenantOrgName("AutoVerTenant2");
        testTenantRequest.setContactEmail(userEmail);
        testTenantRequest.setDescription("automation2");
        testTenantRequest.setShortDescription("auto verification2");
        testTenantRequest.setTenantName("AutoVerificationTenant2");
        TenantResponseV5 tenant = tenantActions.createNewTenantInTouchSide(testTenantRequest, getToken(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.email"), TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user2.password")), TenantResponseV5.class);
        return tenant;
    }

    public TenantResponseV5 addAutoTestTenant1() {
        String userEmail = TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.email");
        String userPassword = TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.password");
        String userFirstName = TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.firstName");
        String userLastName = TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.lastName");
        UserProfile userProfile;
        try{
            userProfile = userActions.getUserProfileInMC2(userEmail, userPassword);
        }catch(Exception e){

            userProfile = userActions.createNewUser("accountname_" + StringUtils.generateRandomString(10),userEmail,userFirstName, userLastName, userPassword);
        }
        TenantRequest testTenantRequest = new TenantRequest();
        testTenantRequest.setAccountId(userProfile.getAccounts().get(0).getId());
        testTenantRequest.setTenantOrgName("AutoVerificationTenant");
        testTenantRequest.setContactEmail(userEmail);
        testTenantRequest.setDescription("automation");
        testTenantRequest.setShortDescription("auto verification");
        testTenantRequest.setTenantName("AutoVerificationTenant");
        TenantResponseV5 tenant = tenantActions.createNewTenantInTouchSide(testTenantRequest, getToken(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.email"), TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.password")), TenantResponseV5.class);
        return tenant;
    }


//    public String getTockenForTenant(String tenantId){
//        TenantResponseV5 tenant = tenantActions.getTenant(tenantId, this.token).as(TenantResponseV5.class);
//        String token= getToken(tenant.getContactEmail(),"passw0rd");
//        return token;
//    }
    public boolean isEqualInputStreams(InputStream i1, InputStream i2) throws IOException {

        try {
            // do the compare
            while (true) {
                int fr = i1.read();
                int tr = i2.read();

                if (fr != tr)
                    return false;

                if (fr == -1)
                    return true;
            }

        } finally {
            if (i1 != null)
                i1.close();
            if (i2 != null)
                i2.close();
        }
    }
}
