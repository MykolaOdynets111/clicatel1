package com.touch.tests;

import com.clickatell.models.user_profiles.UserProfile;
import com.touch.models.ErrorMessage;
import com.touch.models.mc2.AccountInfoResponse;
import com.touch.models.touch.agent.AgentCredentialsDto;
import com.touch.models.touch.agent.AgentResponse;
import com.touch.models.touch.tenant.ListTenantResponse;
import com.touch.models.touch.tenant.TenantRequest;
import com.touch.models.touch.tenant.TenantResponseV5;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by kmakohoniuk on 1/16/2017.
 */
public class IntegrationTestCasesWithMC2 extends BaseTestClass{
    @Test
    public void getTenantListFilteredByAccountId() {
        TenantResponseV5 tenant = tenantActions.createNewTenantInTouchSide(new TenantRequest(), token, TenantResponseV5.class);
        Response response = tenantActions.getTenantsList(tenant.getAccountId(), token);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.as(ListTenantResponse.class).getTenants().contains(tenant));
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
    public void createNewTenant() {
        TenantRequest tenantRequest = new TenantRequest();
        int amountTenantsBeforeAddNew = tenantActions.getTenantsList(token).size();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        List<TenantResponseV5> tenantsListAfterAddNewTenant = tenantActions.getTenantsList(token);
        int amountTenantsAfterAddNew = tenantsListAfterAddNewTenant.size();
        Assert.assertEquals(amountTenantsBeforeAddNew + 1, amountTenantsAfterAddNew);
        Assert.assertTrue(tenantsListAfterAddNewTenant.contains(newTenant), "New tenant was not added to DB or it contains wrong data");
        //Verify get tenant request
        Assert.assertEquals(tenantActions.getTenant(newTenant.getId(), token, TenantResponseV5.class), newTenant);
        /*
        post conditions
         */
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
        tenantsListAfterAddNewTenant = tenantActions.getTenantsList(token);
        // Verify that tenant was deleted after test
        Assert.assertEquals(amountTenantsBeforeAddNew, tenantsListAfterAddNewTenant.size());
    }

    @Test
    public void deleteTenant() {
        String regExpErrorMessage = "Tenant with id .* not found";
        TenantRequest tenantRequest = new TenantRequest();
        int amountTenantsBefore = tenantActions.getTenantsList(token).size();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        Assert.assertEquals(tenantActions.getTenant(newTenant.getId(), token, TenantResponseV5.class), newTenant);
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
        Assert.assertEquals(amountTenantsBefore, tenantActions.getTenantsList(token).size());
        Assert.assertTrue(tenantActions.getTenant(newTenant.getId(), token, ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));
    }
}
