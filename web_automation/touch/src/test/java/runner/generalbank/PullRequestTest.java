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
                PATH + "AgentDeskSuggestions.feature", //  +-
                PATH + "DisconnectingAgent.feature", //  move
                PATH + "UserSentimentUpdating.feature", // +
                PATH + "AgentAvailability.feature", // +
                PATH + "CancelTransferingChat.feature", // move
                PATH + "ChatConsoleAgentTab.feature", // +
                PATH + "ChatConsoleInbox.feature", // move
                PATH + "ChatConsoleOverview.feature", // +
                PATH + "Customer360FromWidget.feature", // +
                PATH + "FilteringPinnedChat.feature", // move
                PATH + "PinChatCloseTransfer.feature", // +
                PATH + "RejectTransferingChat.feature", // move
                PATH + "TransferBackTransferedChat.feature", // +
                PATH + "TransfedChatContainElements.feature", // move
                PATH + "Transfering2Chats.feature", // +
                PATH + "TransferingChat.feature", // +
                DOTCONTROL + "DotControlCustomer360.feature", // move
                DOTCONTROL + "DotControlEditedSuggestionSending.feature", // move
                DOTCONTROL + "DotControlInitCallWithLiveAgent.feature", // +
                DOTCONTROL + "DotControlMessagingWithAgent.feature", // +
                DOTCONTROL + "DotControlWithContext.feature" // move
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
