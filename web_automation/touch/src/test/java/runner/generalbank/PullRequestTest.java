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
        features ={
//                PATH + "AgentDeskSuggestions.feature", need to check the time it takes to run the whole suite if will have some time can add these tests
//                PATH + "ClosingSession.feature",
//                PATH + "DisconnectingAgent.feature",
//                PATH + "RedirectionOnAgent.feature",
                PATH + "AgentAvailability.feature",
                PATH + "CancelTransferingChat.feature",
                PATH + "ChatConsoleAgentTab.feature",
                PATH + "ChatConsoleInbox.feature",
                PATH + "ChatConsoleOverview.feature",
                PATH + "Customer360FromWidget.feature",
                PATH + "FilteringPinnedChat.feature",
                PATH + "PinChatCloseTransfer.feature",
                PATH + "RejectTransferingChat.feature",
//                PATH + "TransferBackTransferedChat.feature", need to wait till message preview is fixed
                PATH + "TransfedChatContainElements.feature",
                PATH + "Transfering2Chats.feature",
//                PATH + "TransferingChat.feature", need to wait till message preview is fixed
                PATH + "TransferingChatWithNoAvailableAgent.feature",
                DOTCONTROL + "DotControlCustomer360.feature",
                DOTCONTROL + "DotControlEditedSuggestionSending.feature",
                DOTCONTROL + "DotControlInitCallWithLiveAgent.feature",
                DOTCONTROL + "DotControlMessagingWithAgent.feature",
                DOTCONTROL + "DotControlWithContext.feature"
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
