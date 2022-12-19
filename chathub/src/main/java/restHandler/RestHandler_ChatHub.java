package restHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.APIHelper;
import io.restassured.response.Response;
import org.testng.Assert;
import pojoClasses.Providers.GetProviders;

import static io.restassured.RestAssured.given;

public class RestHandler_ChatHub implements APIHelper {
    public void getProviders(String authorization, String endpoint) {

        GetProviders[] getProviders = null;
        Response response = given().get(endpoint);
                response.then().assertThat().statusCode(200);
        // Performing deserialization
        getProviders = response.getBody().as(GetProviders[].class);

         Assert.assertNotNull(getProviders[0].getId());
         Assert.assertEquals(getProviders[0].getName(),"Zendesk Support");
    }
}
