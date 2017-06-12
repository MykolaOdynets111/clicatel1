package com.touch.tests;

import com.touch.models.touch.agent.ListAgentResponse;
import com.touch.models.touch.analytics.AnalyticsChatSessionsResponse;
import com.touch.models.touch.auth.AccessTokenRequest;
import com.touch.models.touch.chats.ChatRoomResponse;
import com.touch.models.touch.chats.ListChatSessionResponse;
import com.touch.models.touch.roster.ListRosterResponse;
import com.touch.models.touch.roster.RosterResponse;
import com.touch.utils.TestingEnvProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class RosterTests extends BaseTestClass {
    String chatToken;
    @BeforeClass
    public void beforeClass() {
        chatToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.login"), TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.password"));
    }

    @Test(dataProvider = "roster")
    public void getAgentList(String rosterJid, String state, String canHandleMoreChats, String agentList, int statusCode) {
if(rosterJid!=null&&rosterJid.equals("correct")){
    rosterJid = TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.rosterjid");
}
        Response response = rosterActions.getAgentsList(rosterJid, state, canHandleMoreChats, chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            ListAgentResponse agents = response.as(ListAgentResponse.class);
        }
    }

    @Test(dataProvider = "getRosterOptions")
    public void getRoster(String rosterJid, int statusCode) {
        if(rosterJid!=null&&rosterJid.equals("correct")){
            rosterJid = TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.rosterjid");
        }
        Response response = rosterActions.getRoster(rosterJid, chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            RosterResponse roster = response.as(RosterResponse.class);
            Assert.assertEquals(roster.getJid(), rosterJid);
        }
    }

    @Test(dataProvider = "updateRosterOptions")
    public void updateRoster(String rosterJid, String algorithm, int statusCode) {
        if(rosterJid!=null&&rosterJid.equals("correct")){
            rosterJid = TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.rosterjid");
        }
        Response response = rosterActions.putRoster(rosterJid, algorithm, chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            String alg = algorithm;
            if (!algorithm.equals("random") && !algorithm.equals("round_robin") && !algorithm.equals("less_busy") && !algorithm.equals("multicast"))
                alg = "random";
            RosterResponse roster = response.as(RosterResponse.class);
            RosterResponse expectedRoster = rosterActions.getRoster(rosterJid, chatToken).as(RosterResponse.class);
            Assert.assertEquals(roster.getAlgorithm(), alg);
            Assert.assertEquals(roster, expectedRoster);
        }
    }

    @Test
    public void getListOfRosters() {
        Response response = rosterActions.getRosterList(chatToken);
        Assert.assertEquals(response.getStatusCode(), 200);
        RosterResponse expRoster = rosterActions.getRoster(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.rosterjid"), chatToken).as(RosterResponse.class);
        List<RosterResponse> actualRostersList = response.as(ListRosterResponse.class).getRosters();
        Assert.assertTrue(actualRostersList.contains(expRoster));
    }

    @DataProvider
    private static Object[][] getRosterOptions() {
        return new Object[][]{
                {"correct", 200},
                {"test", 400},
                {"", 400}
        };
    }

    @DataProvider
    private static Object[][] updateRosterOptions() {
        return new Object[][]{
                {"correct", "random", 200},
                {"correct", "round_robin", 200},
                {"correct", "less_busy", 200},
                {"correct", "111", 200},
                {"correct", "test", 200},
                {"correct", "", 200},
                {"test", "random", 400},
                {"test", "round_robin", 400},
                {"test", "less_busy", 400},
                {"test", "", 400},
                {"", "", 400},
                {"correct", "multicast", 200}
        };
    }

    @DataProvider
    private static Object[][] roster() {
        return new Object[][]{
                {"correct", "chat,away", "false", "", 200},
                {"test", "", "true", "", 400},
                {"", "", "true", "", 400},
                {"", "", "", "", 400},
                {"", "test", "false", "", 400}
        };
    }

}
