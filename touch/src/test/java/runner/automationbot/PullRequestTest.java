package runner.automationbot;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static runner.automationbot.Path.DOTCONTROL;
import static runner.automationbot.Path.PATH;

@Test(groups = "General Bank agent UI tests")
@CucumberOptions(
        plugin={"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        monochrome = true,
        features ={
                PATH + "DisconnectingAgent.feature",
                PATH + "RejectTransferingChat.feature",
                PATH + "TransfedChatContainElements.feature",
                PATH + "ClosingSession.feature",
                PATH + "TransferingChatWithNoAvailableAgent.feature",
                PATH + "AgentPhotoManaging.feature",
                PATH + "AvailableUnavailableAgents.feature",
                PATH + "ChatConsoleWaitingCustomers.feature",
                PATH + "EditAutorespondersViaPortalUI.feature",
                PATH + "OutOfSupportHoursBotMode.feature",
                PATH + "TenantBrandImageManaging.feature",
//                PATH + "TransferingChatViaTimeout.feature", - not stable one
                PATH + "TransferOvernigthTicket.feature",
                DOTCONTROL + "DotControlInitCallInvalidAgent.feature"
        },
        glue = "steps")
public class PullRequestTest extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios() {
                return super.scenarios();
        }


}
