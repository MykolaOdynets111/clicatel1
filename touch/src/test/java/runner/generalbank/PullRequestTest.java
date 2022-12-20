package runner.generalbank;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static runner.generalbank.Path.DOTCONTROL;
import static runner.generalbank.Path.PATH;

@Test(groups = "General Bank agent UI tests")
@CucumberOptions(
         plugin={"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        features = {
                PATH + "AgentDeskSuggestions.feature", //  +
                PATH + "UserSentimentUpdating.feature", // +
                PATH + "AgentAvailability.feature", // +
                PATH + "ChatConsoleOverview.feature", // +
                PATH + "UserProfileFromWidget.feature", // +
                PATH + "PinChatCloseTransfer.feature", // +
                PATH + "CancelTransferingChat.feature",
                PATH + "TransferBackTransferedChat.feature", // +
                PATH + "Transfering2Chats.feature", // +
                DOTCONTROL + "DotControlInitCallWithLiveAgent.feature", // +
                DOTCONTROL + "DotControlMessagingWithAgent.feature", // +
        },
        glue = "steps")
public class PullRequestTest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}