package runner.automationbot;


import cucubmerrunner.TestNgCucumberFeatureRunner;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static runner.automationbot.Path.PATH;
import static runner.automationbot.Path.DOTCONTROL;

@Test(groups = "General Bank agent UI tests")
@CucumberOptions(
        plugin={"com.github.kirlionik.cucumberallure.AllureReporter"
        },
        monochrome = true,
        features ={
                PATH + "ClosingSession.feature",
                PATH + "TransferingChatWithNoAvailableAgent.feature",
                PATH + "AgentPhotoManaging.feature",
                PATH + "AvailableAgentsHeadphones.feature",
                PATH + "ChatConsoleWaitingCustomers.feature",
                PATH + "EditAutorespondersViaPortalUI.feature",
                PATH + "OutOfSupportHoursBotMode.feature",
                PATH + "TenantBrandImageManaging.feature",
                PATH + "TransferingChatViaTimeout.feature",
                PATH + "TransferOvernigthTicket.feature",
                DOTCONTROL + "DotControlInitCallInvalidAgent.feature"
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
