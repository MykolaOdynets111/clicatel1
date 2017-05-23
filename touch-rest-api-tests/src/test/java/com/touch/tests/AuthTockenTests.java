package com.touch.tests;

import com.touch.models.ErrorMessage;
import com.touch.models.touch.auth.ListTokenResponseV1;
import com.touch.models.touch.auth.TokenBase;
import com.touch.models.touch.auth.TokenDto;
import com.touch.models.touch.auth.TokenResponseV1;
import com.touch.models.touch.tenant.TenantResponseV5;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class AuthTockenTests extends BaseTestClass {

    @Test
    public void getTokenList() {
        Response response = authActions.getListOfTokens(token);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertFalse(response.as(ListTokenResponseV1.class).getTokens().isEmpty());

    }

    @Test(dataProvider = "tokenRequests")
    public void addNewTokenForDifferentDomainsAndTenants(String domain, String tenantId, int statusCode) {
        if (tenantId.equals("correct")) {
            tenantId = testTenant.getId();
        }
        TokenDto tokenDto = new TokenDto(tenantId, domain, "testDescritption");
        Response response = authActions.addToken(tokenDto, token);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            Assert.assertEquals(response.as(TokenResponseV1.class).getDomain(), domain);
            Assert.assertEquals(response.as(TokenResponseV1.class).getTenantId(), tenantId);
        }
    }

    @Test
    public void addTokenForSpecialTenantAndVerifyPermission() {

        TenantResponseV5 tenant2 = getTestTenant2();
        TokenDto tokenDto = new TokenDto(testTenant.getId(), "ANDROID", "testDescritption");
        TokenResponseV1 androidToken = authActions.addToken(tokenDto, token).as(TokenResponseV1.class);
        Response responseAndroidToken = authActions.authentificateToken(new TokenBase(androidToken.getToken()), token);
        TokenBase tokenBase = responseAndroidToken.as(TokenBase.class);
        Response tenantResponse1 = tenantActions.getTenant(testTenant.getId(), tokenBase.getToken());
        Assert.assertEquals(tenantResponse1.getStatusCode(), 200);
        Response tenantResponse2 = tenantActions.getTenant(tenant2.getId(), tokenBase.getToken());
        Assert.assertEquals(tenantResponse2.getStatusCode(), 401);
        Assert.assertTrue(tenantResponse2.as(ErrorMessage.class).getErrorMessage().matches("Not allowed"));

    }

    @Test(dataProvider = "tokenAuthenticate")
    public void authenticateToken(String tokenAuth, int statusCode) {
        if (tokenAuth.equals("existing")) {
            TokenDto tokenDto = new TokenDto(testTenant.getId(), "ANDROID", "testDescritption");
            tokenAuth = authActions.addToken(tokenDto, token).as(TokenResponseV1.class).getToken();
        }
        Response responseAndroidToken = authActions.authentificateToken(new TokenBase(tokenAuth), token);
        Assert.assertEquals(responseAndroidToken.getStatusCode(), statusCode);
    }

    @DataProvider
    private static Object[][] tokenRequests() {
        return new Object[][]{
                {"IOS", "", 404},
                {"ANDROID", "111", 404},
                {"WEB", "", 404},
                {"JENKINS", "test1", 404},
                {"IOS", "correct", 200},
                {"ANDROID", "correct", 200},
                {"WEB", "correct", 200},
                {"JENKINS", "correct", 200},
                {"test", "", 404},
                {"test", "correct", 200},
                {"ANDROID", "test", 404}
        };
    }

    @DataProvider
    private static Object[][] tokenAuthenticate() {
        return new Object[][]{
                {"existing", 200},
                {"test1", 500},
                {"11111", 500},
                {"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZjgwODA4MTU3ZmNiOTgwMDE1ODAxOThhZjFkMDAzNyIsImV4cCI6MTQ4Mzg2NDkxMCwiaWF0IjoxNDgzNjkyMTEwfQ._4XgpvCn4SXGRGwqg1itWGpUsWI6aUtII4vWs1ktest", 500},
                {"", 500}
        };
    }
    private String getFullPathToFile(String pathToFile) {
        return TenantTests.class.getClassLoader().getResource(pathToFile).getPath();
    }
}
