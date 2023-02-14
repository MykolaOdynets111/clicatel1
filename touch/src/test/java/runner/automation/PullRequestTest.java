package runner.automation;



import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static runner.automation.Path.PATH;
import static runner.automation.Path.DOTCONTROL;

@Test(groups = "General Bank agent UI tests")
@CucumberOptions(
        plugin={"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        features ={
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
                DOTCONTROL + "DotControlUserProfile.feature",
                DOTCONTROL + "DotControlEditedSuggestionSending.feature",
                DOTCONTROL + "DotControlWithContext.feature"
        },
        glue = "steps")
public class PullRequestTest extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios() {
                return super.scenarios();
        }
}
