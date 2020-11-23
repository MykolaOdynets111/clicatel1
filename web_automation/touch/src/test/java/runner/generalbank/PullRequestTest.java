package runner.generalbank;


import cucubmerrunner.TestNgCucumberFeatureRunner;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static runner.generalbank.Path.PATH;
import static runner.generalbank.Path.DOTCONTROL;

@Test(groups = "General Bank agent UI tests")
@CucumberOptions(
        plugin={"com.github.kirlionik.cucumberallure.AllureReporter"
        },
        monochrome = true,
        features = {
                PATH + "AgentDeskSuggestions.feature", //  +
                PATH + "UserSentimentUpdating.feature", // +
                PATH + "AgentAvailability.feature", // +
                PATH + "ChatConsoleAgentTab.feature", // +
                PATH + "ChatConsoleOverview.feature", // +
                PATH + "UserProfileFromWidget.feature", // +
                PATH + "PinChatCloseTransfer.feature", // +
                PATH + "CancelTransferingChat.feature",
                PATH + "TransferBackTransferedChat.feature", // +
                PATH + "Transfering2Chats.feature", // +
                PATH + "TransferingChat.feature", // +
                DOTCONTROL + "DotControlInitCallWithLiveAgent.feature", // +
                DOTCONTROL + "DotControlMessagingWithAgent.feature", // +
        },
        glue ="steps")
public class PullRequestTest {

        @Factory
        public Object[] features() {
                List objects = new ArrayList<>();
                TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
                for (CucumberFeature testDatum : testNGCucumberRunner.getFeatures()) {
                        objects.add(new TestNgCucumberFeatureRunner(testDatum, this));
                }
                return objects.toArray();
        }

}
