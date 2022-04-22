package com.touch.tests;

import com.touch.models.touch.appconfig.AppProfileResponse;
import com.touch.models.touch.appconfig.XmppConfigResponseV5;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppConfigTests extends BaseTestClass {

    @Test
    public void getProfileData() {
        Response response = appConfigActions.getProfileData(testToken);
        Assert.assertEquals(response.getStatusCode(), 200);
        response.as(AppProfileResponse.class);
    }

    @Test
    public void getXmppData() {
        Response response = appConfigActions.getXmppData(testToken);
        Assert.assertEquals(response.getStatusCode(), 200);
        response.as(XmppConfigResponseV5.class);
    }


}
