package runner;

import cucubmerrunner.SuiteCucumberRunner;
import cucumber.api.CucumberOptions;
import org.testng.annotations.Test;


@Test(groups = "cucumber")
@CucumberOptions(
        plugin={"pretty",
                "com.github.kirlionik.cucumberallure.AllureReporter"
        },
//        strict = true,
//        dryRun = true,
        monochrome = true,
        features ="src/test/java/scenario",
        glue ="steps")
public class RunTest extends SuiteCucumberRunner {
}
