package runner.generalbank;

import cucubmerrunner.TestNgCucumberFeatureRunner;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test(groups = {"facebook bot"})
@CucumberOptions(
        plugin={"com.github.kirlionik.cucumberallure.AllureReporter"
        },
        monochrome = true,
        features = {
                Path.FB_TESTS + "BotAnswerOnPost.feature",
                Path.FB_TESTS + "BotAnswersDMFacebook.feature",
        },
        glue ="steps")
public class RunFacebookBotGenBankTest {

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
