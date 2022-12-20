package apihelper;

import datamanager.Tenants;
import datamanager.jacksonschemas.departments.Department;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

public class ApiHelperDepartments extends ApiHelper {

    public static List<Department> getDepartments(String tenantOrgName) {
        return getTouchQuery(tenantOrgName, Endpoints.DEPARTMENTS)
                .jsonPath().getList("", Department.class);
    }

    public static void deleteDepartmentsById(String tenantOrgName) {
        List<Department> departments = getDepartments(tenantOrgName);
        for (Department department : departments) {
            if (department.getName().contains("Auto")) {
                RestAssured.given()
                        .header("Authorization", getAccessToken(tenantOrgName, ""))
                        .delete(Endpoints.DEPARTMENTS + "/" + department.getId());
            }
        }
    }

    public static void createDepartment(String name, String description, String agent) {
        String agentId = getAgentInfo(Tenants.getTenantUnderTestOrgName(), agent).get("id");
        Response resp;
        resp = RestAssured.given().log().all()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{ " +
                        "\"name\": \"" + name + "\"," +
                        "\"description\": \"" + description + "\"," +
                        "\"agentIds\": [\"" + agentId +
                        "\"]" +
                        "}")
                .post(Endpoints.DEPARTMENTS);
        Assert.assertEquals(resp.statusCode(), 200,
                "Creating of department was not successful\n" +
                        "resp body: " + resp.getBody().asString());
    }
}
