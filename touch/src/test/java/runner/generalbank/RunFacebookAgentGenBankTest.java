package runner.generalbank;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = {"facebook agent"})
@CucumberOptions(
         plugin={"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        features = {
                Path.FB_TESTS + "AgentAnswersDMFacebook.feature",
                Path.FB_TESTS + "AgentAnswersIntoTwoChannels.feature",
                Path.FB_TESTS + "AgentAnswersOnNegativePost.feature",
                Path.FB_TESTS + "AgentAnswersOnPost.feature",
                Path.FB_TESTS + "AgentAvailabilityFBDM.feature",
                Path.FB_TESTS + "UserFromFB.feature",
                Path.FB_TESTS + "FacebookChatTranscript.feature",
                Path.FB_TESTS + "FBTransferringChat.feature",
                Path.FB_TESTS + "RedirectToAgentNegativeDMMessage.feature",
                Path.FB_TESTS + "SendingEditedSuggestionToFacebook.feature"
        },
        glue = "steps")
public class RunFacebookAgentGenBankTest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
