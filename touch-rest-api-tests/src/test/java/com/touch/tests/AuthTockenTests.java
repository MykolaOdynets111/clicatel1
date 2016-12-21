package com.touch.tests;

import com.touch.models.ErrorMessage;
import com.touch.models.touch.auth.ListTokenResponseV1;
import com.touch.models.touch.auth.TokenResponseV1;
import com.touch.models.touch.chats.ChatRoomResponse;
import com.touch.models.touch.chats.ChatSessionResponse;
import com.touch.models.touch.chats.ListChatSessionResponse;
import com.touch.models.touch.tenant.TenantRequest;
import com.touch.models.touch.tenant.TenantResponseV5;
import com.touch.utils.StringUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
}
