package endtoend;

import driverfactory.MC2DriverFactory;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import org.testng.Assert;
import org.testng.annotations.*;
import portalpages.PortalSignUpPage;

@Listeners({TestAllureListener.class})
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
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("TECH-12068")
    public void onlyNames(){
        Assert.assertEquals(1, 2, "equals");
    }

    @Description("The very second test")
    @Test(testName = "Test with full sign up data") //test name ignored on method level
    @Severity(SeverityLevel.BLOCKER)
    public void allInfo(){
        signUpPage.signUp("Tom Jones", "alluew2", "aqa@a.a", "123456");
        Assert.assertEquals(1, 1, "equals");
    }

}
