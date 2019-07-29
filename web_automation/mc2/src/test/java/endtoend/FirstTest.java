package endtoend;

import driverfactory.MC2DriverFactory;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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
    @Link("https://jira.clickatell.com/browse/TPORT-9659")
    public void onlyNames(){
        signUpPage.setFirstName("Tom Jones");
        signUpPage.setAccountName("allure");
        Assert.assertEquals(1, 2, "equals");
    }

    @Description("The very second test")
    @Test(testName = "Test with full sign up data") //test name ignored on method level
    @Severity(SeverityLevel.BLOCKER)
    @Link("https://jira.clickatell.com/browse/TPORT-9659")
    public void allInfo(){
        signUpPage.signUp("Tom Jones", "alluew2", "aqa@a.a", "123456");
        Assert.assertEquals(1, 1, "equals");
    }

}
