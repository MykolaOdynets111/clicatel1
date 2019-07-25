package endtoend;

import driverfactory.MC2DriverFactory;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import portalpages.PortalSignUpPage;
import steps.StepOne;

@Test
public class FirstTest extends BaseTest {

    private PortalSignUpPage signUpPage;

    @BeforeTest
    public void setUp(){
        PortalSignUpPage.openPortalSignUpPage(MC2DriverFactory.getPortalDriver());
        signUpPage = new PortalSignUpPage(MC2DriverFactory.getPortalDriver());
    }



    @Description("The very first test")
    @Test
    public void assertMethod(){
        Assert.assertEquals(1, new StepOne().createInt(), "equals");
    }
}
