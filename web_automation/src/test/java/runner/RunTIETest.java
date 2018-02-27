package runner;

import cucubmerrunner.SuiteCucumberRunner;
import cucumber.api.CucumberOptions;
import org.testng.annotations.Test;

@Test(groups = "tie")
@CucumberOptions(
        plugin={"pretty",
                "com.github.kirlionik.cucumberallure.AllureReporter"
        },
        monochrome = true,
        features ="src/test/java/scenario/tie",
        glue ="steps")
public class RunTIETest extends SuiteCucumberRunner {
}
