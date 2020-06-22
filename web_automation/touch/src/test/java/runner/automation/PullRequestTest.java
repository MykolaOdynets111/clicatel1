package runner.automation;


import cucubmerrunner.TestNgCucumberFeatureRunner;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import static runner.automation.Path.PATH;
import static runner.automation.Path.DOTCONTROL;

@Test(groups = "General Bank agent UI tests")
@CucumberOptions(
        plugin={"com.github.kirlionik.cucumberallure.AllureReporter"
        },
        monochrome = true,
        features ={
                PATH + "SupervisorDefaultTicketsFilter.feature",
                PATH + "FilteringPinnedChat.feature",
                PATH + "AgentFeedbackManagingAgentMode.feature",
                PATH + "AgentMode.feature",
                PATH + "AgentModeSuggestion.feature",
                PATH + "AutomaticScheduler.feature",
                PATH + "DisablingWidget.feature",
                PATH + "MaxChatsLImitationAgentMode.feature",
                PATH + "OutOfSupportHoursAgentMode.feature",
                PATH + "RedirectionOnAgentAfterProvidingUserInfo.feature",
                DOTCONTROL + "DotControlMessagesForAllAdapter.feature",
                DOTCONTROL + "DotControlCustomer360.feature",
                DOTCONTROL + "DotControlEditedSuggestionSending.feature",
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
