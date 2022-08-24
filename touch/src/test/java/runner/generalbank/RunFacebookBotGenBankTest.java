package runner.generalbank;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = {"facebook bot"})
@CucumberOptions(
         plugin={"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        features = {
                Path.FB_TESTS + "BotAnswerOnPost.feature",
                Path.FB_TESTS + "BotAnswersDMFacebook.feature",
        },
        glue ="steps")
public class RunFacebookBotGenBankTest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
