package runner.automationcomon;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = "tie_training_management")
@CucumberOptions(
        plugin={"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        monochrome = true,
        features ="src/test/java/scenario/automationcommon/AddingNewIntentFlow.feature",
        glue ="steps")
public class RunTIETrainingManagement extends AbstractTestNGCucumberTests {
        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios() {
                return super.scenarios();
        }
}
