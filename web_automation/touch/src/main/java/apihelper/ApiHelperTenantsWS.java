package apihelper;

import datamanager.jacksonschemas.tenantagentsws.Agent;
import datamanager.jacksonschemas.tenantagentsws.Tenant;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

public class ApiHelperTenantsWS {

    private static ThreadLocal<String>  TENANT_ID =  new ThreadLocal<>();

    public static String getTenantId() {
        return TENANT_ID.get();
    }

    public static void setTenantId(String tenantId) {
        TENANT_ID.set(tenantId);
    }

    public static void createTenantWS(Tenant tenantBody){
        Response resp = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(tenantBody)
                .post(Endpoints.CREATE_TENANT_WS);
        Assert.assertEquals(resp.getStatusCode(), 200,"Status code is not 200 and the response is: "
                + resp.getBody().asString());
        setTenantId(resp.jsonPath().getString("id"));
    }

    public static void createAgentWS(Agent agentBody){
        Response resp = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(agentBody)
                .post(String.format(Endpoints.CREATE_AGENT_WS," getTenantId()") );
        Assert.assertEquals(resp.getStatusCode(), 200,"Status code is not 200 and the response is: "
                + resp.getBody().asString());
    }

}
