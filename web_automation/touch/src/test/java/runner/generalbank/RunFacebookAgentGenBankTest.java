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
                Path.GENBANK_TESTS + "AgentAnswersDMFacebook.feature",
                Path.GENBANK_TESTS + "AgentAnswersIntoTwoChannels.feature",
                Path.GENBANK_TESTS + "AgentAnswersOnNegativePost.feature",
                Path.GENBANK_TESTS + "AgentAnswersOnPost.feature",
                Path.GENBANK_TESTS + "AgentAvailabilityFBDM.feature",
                Path.GENBANK_TESTS + "Customer360FromFB.feature",
                Path.GENBANK_TESTS + "FacebookChatTranscript.feature",
                Path.GENBANK_TESTS + "FBTransferringChat.feature",
                Path.GENBANK_TESTS + "RedirectToAgentNegativeDMMessage.feature",
                Path.GENBANK_TESTS + "SendingEditedSuggestionToFacebook.feature"
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
