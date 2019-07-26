package endtoend;

import driverfactory.MC2DriverFactory;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.*;
import portalpages.PortalSignUpPage;
import steps.StepOne;

@Test(testName = "First Test")
public class FirstTest extends BaseTest {

    private PortalSignUpPage signUpPage;

    @BeforeMethod
    public void setUp(){
        PortalSignUpPage.openPortalSignUpPage(MC2DriverFactory.getPortalDriver());
        signUpPage = new PortalSignUpPage(MC2DriverFactory.getPortalDriver());
    }


    @Description("The very first test")
    @Test(testName = "Test with only names") //test name ignored on method level
    public void onlyNames(){
        signUpPage.setFirstName("Tom Jones");
        signUpPage.setAccountName("allure");
        Assert.assertEquals(1, new StepOne().createInt(), "equals");
    }

    @Description("The very second test")
    @Test(testName = "Test with full sign up data") //test name ignored on method level
    public void allInfo(){
        signUpPage.signUp("Tom Jones", "alluew2", "aqa@a.a", "123456");
        Assert.assertEquals(1, 1, "equals");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        MC2DriverFactory.closePortalBrowser();
    }
}
