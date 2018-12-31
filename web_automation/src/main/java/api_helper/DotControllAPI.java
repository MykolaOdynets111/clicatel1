package api_helper;

import io.restassured.RestAssured;

public class DotControllAPI {

//    private ThreadLocal<>

    public void createIntegration(){
        RestAssured.given().log().all()
                .header("Content-Type", "application/json")
//                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{\n" +
                        "  \"channels\": [\n" +
                        "    {\n" +
                        "      \"name\": \"automation tes014887\",\n" +
                        "      \"enabled\": true,\n" +
                        "      \"apiToken\": \"testing1\",\n" +
                        "      \"url\": \"http://89e3ef8d.ngrok.io\",\n" +
                        "      \"config\": {}\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .post(Endpoints.WIDGET_VISIBILITY_TERRITORIES);
    }
}
