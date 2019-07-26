package apitests;

import datamanager.MC2Account;
import drivermanager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import mc2api.EndpointsPlatform;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class FirstApiTest {

    @BeforeMethod
    public void setUp(){
        ConfigManager.setEnv("testing");
    }

    @Test
    public void test(){
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"email\": \"" + MC2Account.TESTING_STANDARD_ACCOUNT.getEmail() + "\",\n" +
                        "  \"password\": \"" +  MC2Account.TESTING_STANDARD_ACCOUNT.getPass() + "\"\n" +
                        "}")
                .post(EndpointsPlatform.PLATFORM_ACCOUNTS)
                .then()
                    .statusCode(200);
    }
}
