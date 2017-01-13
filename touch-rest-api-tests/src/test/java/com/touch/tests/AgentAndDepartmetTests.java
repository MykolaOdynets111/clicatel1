package com.touch.tests;

import com.clickatell.models.user_profiles.UserProfile;
import com.touch.models.ErrorMessage;
import com.touch.models.touch.agent.*;
import com.touch.models.touch.department.DepartmentDto;
import com.touch.models.touch.department.DepartmentResponse;
import com.touch.models.touch.integration.IntegrationUserLoginMC2Response;
import com.touch.models.touch.tenant.TenantRequest;
import com.touch.models.touch.tenant.TenantResponseV5;
import com.touch.utils.TestingEnvProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class AgentAndDepartmetTests extends BaseTestClass {
    String agentId = "wrong_agentId";
    String expectedMessage = "Agent with id " + agentId + " not found";
    String fileName = "tenant_logo.jpg";
    String file = getFullPathToFile("TenantResources/" + fileName);
    String token;
    TenantResponseV5 testTenant;

    @BeforeClass
    public void beforeClass() {
        token = getToken();
        testTenant = tenantActions.createNewTenantInTouchSide(new TenantRequest(), token, TenantResponseV5.class);
    }

    @Test
    public void createAndDeleteNewAgent() {
//            create new user profile and sign in with it
        UserProfile userProfile = userActions.createNewUser();
        String token = userActions.loginWithNewUserAndReturnToken(userProfile);
//            create new tenant
        TenantRequest tenantRequest = new TenantRequest();
        tenantRequest.setAccountId(userProfile.getAccounts().get(0).getId());
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
//            get agent credential
        AgentCredentialsDto credentials = agentActions.getCredentials(token, AgentCredentialsDto.class);
        //fetch new agent and if it exist we get if not we get error message
        AgentResponse agent = agentActions.getListOfAgents(credentials.getJid(), token, AgentResponse.class);
//            delete agent
        Assert.assertEquals(agentActions.deleteAgent(agent.getId(), token).getStatusCode(), 200);
//        verify that agent have been deleted successful;
//            delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);

    }

    @Test
    public void verifyMaxChatsRoomForAgent() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setTenantId(testTenant.getId());
        String departmentId = departmentActions.addDepartment(departmentDto, token).as(DepartmentResponse.class).getId();
        String jid = agentActions.getCredentials(token, AgentCredentialsDto.class).getJid();
        AgentResponse agent = agentActions.getListOfAgents(jid, token, AgentResponse.class);
        String agentId = agent.getId();
        departmentActions.putAgentInDepartment(departmentId, agentId, token);
        Response agentMaxChatsResponse = agentActions.getAgentMaxChats(agentId, departmentId, token);
        Assert.assertEquals(agentMaxChatsResponse.getStatusCode(), 200);
        Assert.assertEquals(agentMaxChatsResponse.as(AgentMaxChatsResponse.class).getMaxChats(), departmentDto.getSessionsCapacity().toString());

//        delete department
        Assert.assertEquals(departmentActions.deleteDepartment(departmentId, token).getStatusCode(), 200);
    }

    @Test
    public void getAgentWithNotExistingJid() {
        Assert.assertTrue(agentActions.getListOfAgents("not_existing_jid", token, ErrorMessage.class).getErrorMessage().matches("Agent with jabber id not_existing_jid not found"));
    }
    @Test(dataProvider = "maxChatsWrongData")
    public void getMaxChatWithWrongData(String departmentId, String agentId, int statusCode) {
        if(departmentId.equals("test")) {
            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setTenantId(testTenant.getId());
            departmentId = departmentActions.addDepartment(departmentDto, token).as(DepartmentResponse.class).getId();
        }
        if(agentId.equals("test")){
            String jid = agentActions.getCredentials(token, AgentCredentialsDto.class).getJid();
            AgentResponse agent = agentActions.getListOfAgents(jid, token, AgentResponse.class);
            agentId = agent.getId();
        }
        if(departmentId.equals("test")&&agentId.equals("test")){
            departmentActions.putAgentInDepartment(departmentId, agentId, token);
        }
        Response agentMaxChatsResponse = agentActions.getAgentMaxChats(agentId, departmentId, token);
        Assert.assertEquals(agentMaxChatsResponse.getStatusCode(),statusCode);
    }

    @Test
    public void deleteNotExistingAgent() {

        Assert.assertTrue(agentActions.deleteAgent(agentId, token).as(ErrorMessage.class).getErrorMessage().matches(expectedMessage));
    }

    @Test
    public void getImageForNotExistingAgent() {
        Assert.assertTrue(agentActions.getAgentImage(agentId, token).as(ErrorMessage.class).getErrorMessage().matches(expectedMessage));
    }

    @Test
    public void addAndDeleteImageForNotExistingAgent() {
        Assert.assertTrue(agentActions.updateAgentImage(agentId, new File(file), token).as(ErrorMessage.class).getErrorMessage().matches(expectedMessage));
        Assert.assertTrue(agentActions.deleteAgentImage(agentId, token).as(ErrorMessage.class).getErrorMessage().matches(expectedMessage));

    }

    @Test
    public void addAndDeleteImageForAgent() throws IOException {
//            create new user profile and sign in with it
        UserProfile userProfile = userActions.createNewUser();
        String token = userActions.loginWithNewUserAndReturnToken(userProfile);
//            create new tenant
        TenantRequest tenantRequest = new TenantRequest();
        tenantRequest.setAccountId(userProfile.getAccounts().get(0).getId());
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
//            get agent credential
        AgentCredentialsDto credentials = agentActions.getCredentials(token, AgentCredentialsDto.class);
        AgentResponse agent = agentActions.getListOfAgents(credentials.getJid(), token, AgentResponse.class);
        String agentId = agent.getId();
//            add new image and very that it is correct
        Assert.assertEquals(agentActions.updateAgentImage(agentId, new File(file), token).getStatusCode(), 200);
        Assert.assertTrue(isEqualInputStreams(agentActions.getAgentImage(agentId, token).asInputStream(), new FileInputStream(new File(file))));
//        delete image and verify that status code was return correct
        Assert.assertEquals(agentActions.deleteAgentImage(agentId, token).getStatusCode(), 200);
//            delete agent
        Assert.assertEquals(agentActions.deleteAgent(agentId, token).getStatusCode(), 200);
//            delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
    }
    @DataProvider
    private static Object[][] maxChatsWrongData() {
        return new Object[][]{
                {"test","", 404},
                {"test","not_existing", 404},
                {"","test", 404},
                {"not_existing","test", 404},
                {"","", 404}
        };
    }
    private String getFullPathToFile(String pathToFile) {
        return TenantTests.class.getClassLoader().getResource(pathToFile).getPath();
    }

    private boolean isEqualInputStreams(InputStream i1, InputStream i2) throws IOException {

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
    @AfterClass
    public void afterClass() {
        token = getToken();
        removeAllTestTenants(token);
    }

}
