package apitests;

import datamanager.MC2Account;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import mc2api.EndpointsPlatform;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;


@Test(testName = "POST /auth/accounts")
public class FirstApiTest extends BaseApiTest{


    @Step("Verify POST /auth/accounts 200 and token not null ")
    @Test
    public void authAccounts(){
        RestAssured
                .given()
                    .filter(new AllureRestAssured())
                    .contentType(ContentType.JSON)
                    .body("{\n" +
                        "  \"email\": \"" + MC2Account.DEV_BILLING_ADMIN.getEmail() + "\",\n" +
                        "  \"password\": \"" +  MC2Account.DEV_BILLING_ADMIN.getPass() + "\"\n" +
                        "}")
                .when()
                    .post(EndpointsPlatform.PLATFORM_ACCOUNTS)
                .then()
                    .statusCode(300)
                    .assertThat()
                    .body("token", notNullValue());
    }
}
