package com.touch.tests;

import com.touch.models.touch.internal.SupportHoursResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by oshcherbatyy on 20-07-17.
 */
public class InternalTests extends BaseTestClass {

    @Test
    public void getTenantSupportHours(){
        Response response = interanlActions.getTenantSupportHours(getTestTenant1().getTenantOrgName());
        Assert.assertEquals(response.getStatusCode(), 200);
        //since there is no way to set support hours now, just chech whether valuse returned is true or false
        System.out.print("#######################################" + response.getBody().print());
        Assert.assertTrue((response.getBody().asString().equals("true") || response.getBody().asString().equals("false")));
    }
}
