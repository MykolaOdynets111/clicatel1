package runner.generalbank;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = {"whatsapporca"})
@CucumberOptions(
         plugin={"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        features ="src/test/java/scenario/generalbank/touch/agentflows",
        glue ="steps")
public class RunWhatsAppORCATest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
//        ApiHelper.ratingEnabling("General Bank Demo", false,"abc");
        return super.scenarios();
    }

}
