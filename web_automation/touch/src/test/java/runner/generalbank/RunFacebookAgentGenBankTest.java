package runner.generalbank;

import cucubmerrunner.TestNgCucumberFeatureRunner;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test(groups = {"facebook agent"})
@CucumberOptions(
        plugin={"com.github.kirlionik.cucumberallure.AllureReporter"
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
        glue ="steps")
public class RunFacebookAgentGenBankTest {

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
