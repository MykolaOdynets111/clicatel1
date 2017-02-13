package com.touch.tests;

import com.touch.models.ErrorMessage;
import com.touch.models.touch.analytics.AnalyticsChatSessionsResponse;
import com.touch.models.touch.analytics.ConversationCountStatsResponseV5;
import com.touch.models.touch.analytics.ConversationTimeStatsResponseV5;
import com.touch.models.touch.auth.TokenBase;
import com.touch.models.touch.auth.TokenDto;
import com.touch.models.touch.auth.TokenResponseV1;
import com.touch.models.touch.tenant.TenantResponseV5;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AnalyticsTests extends BaseTestClass {

    @Test(dataProvider = "getAnalytics")
    public void getAnalyticsForDifferentData(String tenantId, String date,String timeZone, int statusCode) {
        if (tenantId!=null&&tenantId.equals("correct")) {
            tenantId = testTenant.getId();
        }
        if (date!=null&&date.equals("correct")) {
            date = "2016-12-25";
        }
        if (timeZone!=null&&timeZone.equals("correct")) {
            timeZone = "UTC, GMT+3";
        }
         Response response = analyticsActions.getAnalyticsData(tenantId,date,timeZone,testToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            response.as(AnalyticsChatSessionsResponse.class);
        }
    }
    @Test(dataProvider = "conversationCount")
    public void getConversationCount(String tenantId, String year, String month, String day, int statusCode) {
        if (tenantId!=null&&tenantId.equals("correct")) {
            tenantId = testTenant.getId();
        }
        Response response = analyticsActions.getConversationCount(tenantId, year, month, day, testToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            response.as(ConversationCountStatsResponseV5.class);
        }
    }
    @Test(dataProvider = "conversationCount")
    public void getConversationTime(String tenantId, String year, String month, String day, int statusCode) {
        if (tenantId!=null&&tenantId.equals("correct")) {
            tenantId = testTenant.getId();
        }
        Response response = analyticsActions.getConversationTime(tenantId, year, month, day, testToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            response.as(ConversationTimeStatsResponseV5.class);
        }
    }
    @DataProvider
    private static Object[][] getAnalytics() {
        return new Object[][]{
                {"", "","", 401},
                {"correct", "correct","correct", 200},
                {"correct", "","correct", 400},
                {"correct", "correct","", 200},
                {"correct", "correct","test", 400},
                {"correct", "test","correct", 400},
                {"correct", "22/11/2016","correct", 400},
                {null, null,null, 400},
                {"correct", null,"correct", 400},
                {"correct", "correct",null, 200}


        };
    }
    @DataProvider
    private static Object[][] conversationCount() {
        return new Object[][]{
                {"", "","","", 400},
                {"correct", "2017","01","10", 200},
                {"correct", "","01","10", 400},
                {"correct", "2017","","", 400},
                {null, null,null,null, 400},
                {null, null,null,"", 400},
                {"correct", null,"01","10", 400},
                {"correct", "2017",null,"10", 400},
                {"correct", "20171","01","10", 400},
                {"correct", "2017","111","10", 400},
                {"correct", "2017","01","100", 400},
                {"correct", "2017","test","10", 400},
                {"correct", "test","01","10", 400},
                {"correct", "2017","01","test", 400},
        };
    }

}
