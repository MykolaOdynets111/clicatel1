package com.touch.tests;

import com.touch.models.touch.agent.ListAgentResponse;
import com.touch.models.touch.analytics.AnalyticsChatSessionsResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RosterTests extends BaseTestClass {

    @Test(dataProvider = "roster")
    public void getAnalyticsForDifferentData(String rosterJid, String state,String canHandleMoreChats, String agentList, int statusCode) {

         Response response = rosterActions.getAgentsList(rosterJid,state,canHandleMoreChats,testToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            ListAgentResponse agents = response.as(ListAgentResponse.class);
            if(agentList.equals("notEmpty"))
                Assert.assertTrue(!agents.getAgents().isEmpty());
            else
                Assert.assertTrue(agents.getAgents().isEmpty());
        }
    }


    @DataProvider
    private static Object[][] roster() {
        return new Object[][]{
                {"genbank@department.clickatelllabs.com", "chat","true", "notEmpty",200},
                {"genbank@department.clickatelllabs.com", "chat,away","true","notEmpty", 200},
                {"genbank@department.clickatelllabs.com", "chat,away","false","", 200},
                {"genbank@department.clickatelllabs.com", "test","true","notEmpty", 200},
                {"genbank@department.clickatelllabs.com", "","true","notEmpty", 200},
                {"test", "","true","", 400},
                {"", "","true","", 400},
                {"", "","","", 400},
                {"", "test","false","", 400}
        };
    }

}
