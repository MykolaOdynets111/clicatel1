package endtoend.acceptance;


import endtoend.basetests.SignUpBaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.PortalLoginPage;
import portalpages.PortalMainPage;


@Test(testName = "Registration :: Sign in old user", groups = {"newaccount"}, dependsOnGroups = {"registration"})
@TmsLink("TECH-12060")
public class SignInOldUserTest extends SignUpBaseTest {

    private PortalMainPage mainPage;
    private PortalLoginPage loginPage;

    @Description("Registration :: Sign in old user")
    @Epic("Account Registration")
    @Feature("Sign in old user")
    public void registrationSignUp(){
        verifyNewAccountSecondLogin();
        verifyNewAccountLogout();
    }

    @Step(value = "Verify new account second log in")
    private void verifyNewAccountSecondLogin(){
        loginPage = PortalLoginPage.openPortalLoginPage();
        mainPage = loginPage.login(email.get(), pass.get());
        SoftAssert soft = new SoftAssert();

        soft.assertTrue(mainPage.isUpdatePolicyPopUpNotShown(),
                "Update policy pop up is shown in second time");
        soft.assertTrue(mainPage.isLandingPopUpNotShown(),
                "Landing pop up is shown in second time");
        soft.assertEquals(mainPage.getGreetingMessage(),
                "Welcome, "+ firstName.get() + ". Add a solution to your account.",
                "Greeting is not as expected");
        soft.assertTrue(mainPage.isGetStartedWithTouchButtonIsShown(),
                "'Get started' button is not shown");
        soft.assertAll();
    }


    @Step(value = "Verify new account log out")
    private void verifyNewAccountLogout(){
        mainPage.getPageHeader().logoutAdmin();

        Assert.assertTrue(loginPage.isLoginPageOpened(5), "Login page is not opened");
    }



}
