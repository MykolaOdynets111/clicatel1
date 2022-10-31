package apihelper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static java.lang.String.format;
import static org.junit.Assert.fail;

public class ApiHelperTenant extends ApiHelper {

    public static Response getTenantConfig(String tenantOrgName) {
        return getQuery(tenantOrgName, Endpoints.TENANT_CURRENT, "main");
    }

    public static void deleteTenantLogo(String tenantOrgName) {
        Response response = RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .delete(format(Endpoints.DELETE_TENANT_LOGO));
        if (response.getStatusCode() != 200) {
            fail("Couldn't get the value \n"
                    + "Status code: " + response.statusCode() + "\n"
                    + "TenantOrgName: " + tenantOrgName + "\n"
                    + "Error message: " + response.getBody().asString());
        }
    }
}
