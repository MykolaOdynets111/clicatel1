package com.touch.tests;

import com.touch.models.ErrorMessage;
import com.touch.models.touch.agent.AgentCredentialsDto;
import com.touch.models.touch.agent.AgentNoDepartmentsResponse;
import com.touch.models.touch.agent.AgentResponse;
import com.touch.models.touch.agent.ListAgentResponse;
import com.touch.models.touch.cards.ListCardsPlatformsResponseV4;
import com.touch.models.touch.cards.PlatformDtoV4;
import com.touch.models.touch.cards.TouchCardResponseV4;
import com.touch.models.touch.department.DepartmentDto;
import com.touch.models.touch.department.DepartmentResponse;
import com.touch.models.touch.department.ListDepartmentResponse;
import com.touch.models.touch.tenant.TenantRequest;
import com.touch.models.touch.tenant.TenantResponseV5;
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
public class DepartmentsTests extends BaseTestClass {
    String token;
    TenantResponseV5 testTenant;

    @BeforeClass
    public void beforeClass() {
        token = getToken();
        testTenant = tenantActions.createNewTenantInTouchSide(new TenantRequest(), token, TenantResponseV5.class);
    }

    // here is a bug
    @Test
    public void getListOfDepartments() {
        Response response = departmentActions.getListOfDepartments(token);
//        verify that status code is correct
        Assert.assertEquals(response.getStatusCode(), 200);
//        verify that list of departments is not empty
        Assert.assertTrue(!response.as(ListDepartmentResponse.class).getDepartments().isEmpty());
    }

    //    here are bugs
    @Test(dataProvider = "addDepartmentsOptions")
    public void addNewDepartment(String tenantId, String name, String description, int sessionsCapacity, int statusCode) {
        if (tenantId.equals("correct"))
            tenantId = testTenant.getId();
        DepartmentDto department = new DepartmentDto(tenantId, name, description, sessionsCapacity);
        Response response = departmentActions.addDepartment(department, token);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 201) {
//            verify that new department has all correct added data
            Assert.assertEquals(response.as(DepartmentResponse.class), department);
        }
    }

    @Test
    public void addNewDepartmentWithWrongBody() {
        Response response = departmentActions.addDepartment("test", token);
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test(dataProvider = "departmentsIdOptions")
    public void deleteDepartment(String departmentId, int statusCode) {
        if (departmentId.equals("correct")){
            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setTenantId(testTenant.getId());
            departmentId = departmentActions.addDepartment(departmentDto, token).as(DepartmentResponse.class).getId();
        }
        Assert.assertEquals(departmentActions.deleteDepartment(departmentId, token).getStatusCode(), statusCode);
    }

    @Test(dataProvider = "departmentsIdOptions")
    public void getDepartment(String departmentId, int statusCode) {
        if (departmentId.equals("correct")){
            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setTenantId(testTenant.getId());
            departmentId = departmentActions.addDepartment(departmentDto, token).as(DepartmentResponse.class).getId();
        }
        Assert.assertEquals(departmentActions.deleteDepartment(departmentId, token).getStatusCode(), statusCode);
    }

    @Test(dataProvider = "updateDepartmentsOptions")
    public void updateDepartment(String departmentId, String name, String description, String sessionCapacity, int statusCode) {
        if (departmentId.equals("correct")){
            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setTenantId(testTenant.getId());
            departmentId = departmentActions.addDepartment(departmentDto, token).as(DepartmentResponse.class).getId();
        }
        Response response = departmentActions.updateDepartment(departmentId, name, description, sessionCapacity, token);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
//            verify that new department has all correct added data
            DepartmentResponse departmentResponse = response.as(DepartmentResponse.class);
            if(!description.isEmpty())
            Assert.assertEquals(departmentResponse.getDescription(), description);
            if(!name.isEmpty())
            Assert.assertEquals(departmentResponse.getName(), name);
            if(!sessionCapacity.isEmpty())
                Assert.assertEquals(departmentResponse.getSessionsCapacity().toString(), sessionCapacity);
        }

    }

    @Test(dataProvider = "addAgentsOptions")
    public void addAgentForDepartmet(String departmentId, String agentId, int statusCode) {
        if (departmentId.equals("correct")){
            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setTenantId(testTenant.getId());
            departmentId = departmentActions.addDepartment(departmentDto, token).as(DepartmentResponse.class).getId();
        }
        String jid = agentActions.getCredentials(token, AgentCredentialsDto.class).getJid();
        AgentResponse agent = agentActions.getListOfAgents(jid, token, AgentResponse.class);
        if (agentId.equals("correct")){
            agentId = agent.getId();
        }
        Response response = departmentActions.putAgentInDepartment(departmentId, agentId, token);
        Assert.assertEquals(response.getStatusCode(),statusCode);
        if (statusCode == 200) {
            List<AgentNoDepartmentsResponse> agents = departmentActions.getDepartment(departmentId, token).as(DepartmentResponse.class).getAgents();
            Assert.assertEquals(agents.get(0),agent);
        }
        if (departmentId.equals("correct")){
            Assert.assertEquals(departmentActions.deleteDepartment(departmentId, token).getStatusCode(),200);
        }
    }

    @Test(dataProvider = "addAgentsOptions")
    public void deleteAgentForDepartment(String departmentId, String agentId, int statusCode) {
        if (departmentId.equals("correct")){
            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setTenantId(testTenant.getId());
            departmentId = departmentActions.addDepartment(departmentDto, token).as(DepartmentResponse.class).getId();
        }
        String jid = agentActions.getCredentials(token, AgentCredentialsDto.class).getJid();
        AgentResponse agent = agentActions.getListOfAgents(jid, token, AgentResponse.class);
        if (agentId.equals("correct")){
            agentId = agent.getId();
        }
        Response response = departmentActions.deleteAgentInDepartment(departmentId, agentId, token);
        Assert.assertEquals(response.getStatusCode(),statusCode);
    }

    @DataProvider
    private static Object[][] addDepartmentsOptions() {
        return new Object[][]{
                {"correct", "test", "test", 5, 201},
                {"correct", "test", "test", 5, 400},
                {"correct", "test1", "", 5, 201},
                {"correct", "", "", 5, 201},
                {"", "", "", 5, 400},
                {"test", "test2", "test", 5, 404},
                {"test1", "2", "2", 5, 404}
        };
    }
    @DataProvider
    private static Object[][] updateDepartmentsOptions() {
        return new Object[][]{
                {"correct", "test", "test", "5", 200},
                {"correct", "test1", "", "5", 200},
                {"correct", "", "", "5", 200},
                {"correct", "", "", "", 200},
                {"correct", "", "", "test", 400},
                {"", "", "", "5", 405},
                {"", "", "", "", 405},
                {"test", "test2", "test", "5", 404},
                {"test1", "2", "2", "", 404}
        };
    }
    @DataProvider
    private static Object[][] departmentsIdOptions() {
        return new Object[][]{
                {"correct", 200},
                {"test", 404},
                {"", 405}
        };
    }
    @DataProvider
    private static Object[][] addAgentsOptions() {
        return new Object[][]{
                {"correct","correct", 200},
                {"correct","test", 404},
                {"correct","", 400},
                {"","correct", 400},
                {"test","correct", 404},
                {"test","test", 404},
                {"","", 405}
        };
    }


    @AfterClass
    public void afterClass() {
        deleteTestDepartmnets();
    }

    private void deleteTestDepartmnets() {
        List<DepartmentResponse> departmetsList = departmentActions.getListOfDepartments(token).as(ListDepartmentResponse.class).getDepartments();
        if (!departmetsList.isEmpty()) {
            for (DepartmentResponse department : departmetsList) {
                if (department.getName().contains("test")||department.getName().contains("Test"))
                    Assert.assertEquals(departmentActions.deleteDepartment(department.getId(), token).getStatusCode(), 200);
            }
        }
    }
}
