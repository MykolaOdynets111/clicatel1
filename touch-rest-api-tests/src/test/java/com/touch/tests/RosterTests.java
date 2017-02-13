package com.touch.tests;

import com.touch.models.touch.agent.ListAgentResponse;
import com.touch.models.touch.analytics.AnalyticsChatSessionsResponse;
import com.touch.models.touch.roster.ListRosterResponse;
import com.touch.models.touch.roster.RosterResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class RosterTests extends BaseTestClass {

    @Test(dataProvider = "roster")
    public void getAgentList(String rosterJid, String state, String canHandleMoreChats, String agentList, int statusCode) {

        Response response = rosterActions.getAgentsList(rosterJid, state, canHandleMoreChats, testToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            ListAgentResponse agents = response.as(ListAgentResponse.class);
//            if(agentList.equals("notEmpty"))
//                Assert.assertTrue(!agents.getAgents().isEmpty());
//            else
//                Assert.assertTrue(agents.getAgents().isEmpty());
        }
    }

    @Test(dataProvider = "getRosterOptions")
    public void getRoster(String rosterJid, int statusCode) {
        Response response = rosterActions.getRoster(rosterJid, testToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            RosterResponse roster = response.as(RosterResponse.class);
            Assert.assertEquals(roster.getJid(), rosterJid);
        }
    }

    @Test(dataProvider = "updateRosterOptions")
    public void updateRoster(String rosterJid, String algorithm, int statusCode) {
        Response response = rosterActions.putRoster(rosterJid, algorithm, testToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            String alg = algorithm;
            if (!algorithm.equals("random") && !algorithm.equals("round_robin") && !algorithm.equals("less_busy") && !algorithm.equals("multicast"))
                alg = "random";
            RosterResponse roster = response.as(RosterResponse.class);
            RosterResponse expectedRoster = rosterActions.getRoster(rosterJid, testToken).as(RosterResponse.class);
            Assert.assertEquals(roster.getAlgorithm(), alg);
            Assert.assertEquals(roster, expectedRoster);
        }
    }

    @Test
    public void getListOfRosters() {
        Response response = rosterActions.getRosterList(testToken);
        Assert.assertEquals(response.getStatusCode(), 200);
        RosterResponse expRoster = rosterActions.getRoster("genbank@department.clickatelllabs.com", testToken).as(RosterResponse.class);
        List<RosterResponse> actualRostersList = response.as(ListRosterResponse.class).getRosters();
        Assert.assertTrue(actualRostersList.contains(expRoster));
    }

    @DataProvider
    private static Object[][] getRosterOptions() {
        return new Object[][]{
                {"genbank@department.clickatelllabs.com", 200},
                {"genbank1@department.clickatelllabs.com", 404},
                {"test", 400},
                {"", 400}
        };
    }

    @DataProvider
    private static Object[][] updateRosterOptions() {
        return new Object[][]{
                {"genbank@department.clickatelllabs.com", "random", 200},
                {"genbank@department.clickatelllabs.com", "round_robin", 200},
                {"genbank@department.clickatelllabs.com", "less_busy", 200},
                {"genbank@department.clickatelllabs.com", "111", 200},
                {"genbank@department.clickatelllabs.com", "test", 200},
                {"genbank@department.clickatelllabs.com", "", 200},
                {"test", "random", 400},
                {"test", "round_robin", 400},
                {"test", "less_busy", 400},
                {"test", "", 400},
                {"", "", 400},
                {"genbank@department.clickatelllabs.com", "multicast", 200}
        };
    }

    @DataProvider
    private static Object[][] roster() {
        return new Object[][]{
                {"genbank@department.clickatelllabs.com", "chat", "true", "notEmpty", 200},
                {"genbank@department.clickatelllabs.com", "chat,away", "true", "notEmpty", 200},
                {"genbank@department.clickatelllabs.com", "chat,away", "false", "", 200},
                {"genbank@department.clickatelllabs.com", "test", "true", "notEmpty", 200},
                {"genbank@department.clickatelllabs.com", "", "true", "notEmpty", 200},
                {"test", "", "true", "", 400},
                {"", "", "true", "", 400},
                {"", "", "", "", 400},
                {"", "test", "false", "", 400}
        };
    }

}
