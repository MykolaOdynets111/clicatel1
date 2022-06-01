package runner.generalbank;


import apihelper.ApiHelper;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = "General Bank widget UI tests")
@CucumberOptions(
        plugin={"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        monochrome = true,
        features ="src/test/java/scenario/generalbank/touch/botflows",
        glue ="steps")
public class RunAllGenBankBotTest extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios() {
                return super.scenarios();
        }

}
