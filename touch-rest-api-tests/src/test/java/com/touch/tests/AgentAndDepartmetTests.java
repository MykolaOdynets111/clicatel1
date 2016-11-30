package com.touch.tests;

import com.clickatell.models.user_profiles.UserProfile;
import com.touch.models.ErrorMessage;
import com.touch.models.touch.agent.AgentCredentialsDto;
import com.touch.models.touch.agent.AgentResponse;
import com.touch.models.touch.agent.ListAgentResponse;
import com.touch.models.touch.department.DepartmentDto;
import com.touch.models.touch.department.DepartmentResponse;
import com.touch.models.touch.tenant.TenantRequest;
import com.touch.models.touch.tenant.TenantResponseV4;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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
    String agentId="wrong_agentId";
    String expectedMessage = "Agent with id "+agentId+" not found";
    String fileName = "tenant_logo.jpg";
    String file = getFullPathToFile("TenantResources/" + fileName);

    @BeforeClass
    public void beforeClass() {
    }

    @Test
    public void createAndDeleteNewAgent() {
        int agentListSizeBefore = agentActions.getListOfAgents(null,null,ListAgentResponse.class).getAgents().size();
//            create new user profile and sign in with it
        UserProfile userProfile = userActions.createNewUser();
        String token = userActions.loginWithNewUserAndReturnToken(userProfile);
//            create new tenant
        TenantRequest tenantRequest = new TenantRequest();
        tenantRequest.setAccountId(userProfile.getAccounts().get(0).getId());
        TenantResponseV4 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponseV4.class);
//            get agent credential
        AgentCredentialsDto credentials = agentActions.getCredentials(token, AgentCredentialsDto.class);
        List<AgentResponse> agentsList = agentActions.getListOfAgents(null,null,ListAgentResponse.class).getAgents();
//            verify that we have one more a new agent in agents list
        Assert.assertEquals(agentListSizeBefore + 1, agentsList.size());
        String agentId = "";
        for (AgentResponse agent : agentsList) {
            if (agent.getAgentJid().equals(credentials.getJid()))
                agentId = agent.getId();
        }

//            delete agent
        Assert.assertEquals(agentActions.deleteAgent(agentId).getStatusCode(), 200);
//        verify that agent have been deleted successful;
        Assert.assertEquals(agentListSizeBefore, agentActions.getListOfAgents(null,null,ListAgentResponse.class).getAgents().size());
//            delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);

    }

    // TODO here we have bug with departments we can't get info
    //    @Test
    public void verifyMaxChatsRoomForAgent() {
        UserProfile userProfile = userActions.createNewUser();
        String token = userActions.loginWithNewUserAndReturnToken(userProfile);
//            create new tenant
        TenantRequest tenantRequest = new TenantRequest();
        tenantRequest.setAccountId(userProfile.getAccounts().get(0).getId());
        TenantResponseV4 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponseV4.class);
//            get agent credential
        AgentCredentialsDto credentials = agentActions.getCredentials(token, AgentCredentialsDto.class);
        List<AgentResponse> agentsList = agentActions.getListOfAgents(null,null,ListAgentResponse.class).getAgents();
//        create new department and connect it with new tenant
        DepartmentDto department = new DepartmentDto();
        department.setTenantId(newTenant.getId());
        DepartmentResponse newDepartment = departmentActions.addDepartment(department);

        String agentId = "";
        for (AgentResponse agent : agentsList) {
            if (agent.getAgentJid().equals(credentials.getJid()))
                agentId = agent.getId();
        }
        //        Assign agent to new department and verify that it returns correct status code
        Assert.assertEquals(departmentActions.putAgentInDepartment(newDepartment.getId(), agentId), 201);
//        get max chart value and verify it's correctness
        Assert.assertEquals(agentActions.getAgentMaxChats(agentId, newDepartment.getId()), 5);

//            delete agent
        Assert.assertEquals(agentActions.deleteAgent(agentId).getStatusCode(), 200);
//            delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);

    }

    @Test
    public void getAgentsListWithNotExistingAgentId() {
        Assert.assertTrue(agentActions.getListOfAgents(agentId,null,ErrorMessage.class).getErrorMessage().matches(expectedMessage));
    }
//TODO
//    @Test
    public void getMaxChartForNotExistingAgentAndDepartment() {

    }

    @Test
    public void deleteNotExistingAgent() {

        Assert.assertTrue(agentActions.deleteAgent(agentId).as(ErrorMessage.class).getErrorMessage().matches(expectedMessage));
    }

    @Test
    public void getImageForNotExistingAgent() {
        Assert.assertTrue(agentActions.getAgentImage(agentId).as(ErrorMessage.class).getErrorMessage().matches(expectedMessage));
    }

    @Test
    public void addAndDeleteImageForNotExistingAgent() {
        Assert.assertTrue(agentActions.updateAgentImage(agentId, new File(file)).as(ErrorMessage.class).getErrorMessage().matches(expectedMessage));
        Assert.assertTrue(agentActions.deleteAgentImage(agentId).as(ErrorMessage.class).getErrorMessage().matches(expectedMessage));

    }

    @Test
    public void addAndDeleteImageForAgent() throws IOException {
//            create new user profile and sign in with it
        UserProfile userProfile = userActions.createNewUser();
        String token = userActions.loginWithNewUserAndReturnToken(userProfile);
//            create new tenant
        TenantRequest tenantRequest = new TenantRequest();
        tenantRequest.setAccountId(userProfile.getAccounts().get(0).getId());
        TenantResponseV4 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponseV4.class);
//            get agent credential
        AgentCredentialsDto credentials = agentActions.getCredentials(token, AgentCredentialsDto.class);
        List<AgentResponse> agentsList = agentActions.getListOfAgents(null, null, ListAgentResponse.class).getAgents();
//            verify that we have one more a new agent in agents list
        String jid = credentials.getJid();
        String agentId = "";
        for (AgentResponse agent : agentsList) {
            if (agent.getAgentJid().equals(credentials.getJid()))
                agentId = agent.getId();
        }
//            add new image and very that it is correct
        Assert.assertEquals(agentActions.updateAgentImage(agentId, new File(file)).getStatusCode(), 200);
        Assert.assertTrue(isEqualInputStreams(agentActions.getAgentImage(agentId).asInputStream(), new FileInputStream(new File(file))));
//        delete image and verify that status code was return correct
        Assert.assertEquals(agentActions.deleteAgentImage(agentId).getStatusCode(), 200);
//            delete agent
        Assert.assertEquals(agentActions.deleteAgent(agentId).getStatusCode(), 200);
//            delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);
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

}
