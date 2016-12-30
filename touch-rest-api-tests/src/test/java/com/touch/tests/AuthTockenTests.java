package com.touch.tests;

import com.touch.models.ErrorMessage;
import com.touch.models.touch.auth.ListTokenResponseV1;
import com.touch.models.touch.auth.TokenResponseV1;
import com.touch.models.touch.tenant.TenantRequest;
import com.touch.models.touch.tenant.TenantResponseV5;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class AuthTockenTests extends BaseTestClass {
    String token;
    TenantResponseV5 testTenant;
    @BeforeClass
    public void beforeClass() {
        token = getToken();
        testTenant = tenantActions.createNewTenantInTouchSide(new TenantRequest(), token, TenantResponseV5.class);
    }
    @Test
    public void getTockenList(){
        Response response = authActions.getListOfTockens(token);
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertFalse(response.as(ListTokenResponseV1.class).getTokens().isEmpty());

    }
    @Test(dataProvider = "correctTokenRequests")
    public void addNewTokenForDifferentDevicesAndTenant(String device, String tenantId, int statusCode){
        if(tenantId.equals("correct")) {
            tenantId = testTenant.getId();
        }
        Response response = authActions.addTocken(device, tenantId, token);
        Assert.assertEquals(response.getStatusCode(),statusCode);
        Assert.assertEquals(response.as(TokenResponseV1.class).getDevice(),device);
        if(tenantId.equals("correct")) {
            Assert.assertEquals(response.as(TokenResponseV1.class).getTenantId(),tenantId);
        }
        if(tenantId.equals("")) {
            Assert.assertEquals(response.as(TokenResponseV1.class).getTenantId(),"");
        }

    }
    @Test
    public void addTokenForSpecialTenantAndVerifyPermition(){
        TenantResponseV5 tenant2 = tenantActions.createNewTenantInTouchSide(new TenantRequest(), token, TenantResponseV5.class);
        TokenResponseV1 androidToken = authActions.addTocken("ANDROID", testTenant.getId(), token).as(TokenResponseV1.class);
        Response tenantResponse1 = tenantActions.getTenant(testTenant.getId(), androidToken.getToken());
        Assert.assertEquals(tenantResponse1.getStatusCode(),200);
        Response tenantResponse2 = tenantActions.getTenant(tenant2.getId(), androidToken.getToken());
        Assert.assertEquals(tenantResponse2.getStatusCode(),404);
        Assert.assertTrue(tenantResponse2.as(ErrorMessage.class).getErrorMessage().matches("Tenant with id .+ not found"));

    }
    @Test (dataProvider = "wrongTokenRequests")
    public void addTokenWithWrongData(String device, String tenantId, int statusCode){
        if(tenantId.equals("correct")) {
            tenantId = testTenant.getId();
        }
        Response response = authActions.addTocken(device, tenantId, token);
        Assert.assertEquals(response.getStatusCode(),statusCode);
    }

    @DataProvider
    private static Object[][] correctTokenRequests() {
        return new Object[][]{
                {"IOS", "", 200},
                {"ANDROID", "", 200},
                {"WEB", "", 200},
                {"JENKINS", "", 200},
                {"IOS", "correct", 200},
                {"ANDROID", "correct", 200},
                {"WEB", "correct", 200},
                {"JENKINS", "correct", 200},
        };
    }
    @DataProvider
    private static Object[][] wrongTokenRequests() {
        return new Object[][]{
                {"test", "", 400},
                {"test", "correct", 400},
                {"ANDROID", "test", 200}

        };
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

    @DataProvider
    private static Object[][] resourcesList() {
        return new Object[][]{
                {"TenantResources/tenant_logo.jpg", "new_logo"},
                {"TenantResources/bg_chat_image.jpg", "new_bg_chat_image"}
        };
    }
}
