package endtoend.acceptance.signup;


import datamanager.ExistedAccount;
import endtoend.basetests.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.PortalLoginPage;
import portalpages.PortalMainPage;


@Test(testName = "Registration :: Sign in old user", groups = {"secondlogin"})
@TmsLink("TECH-5672")
public class SignInOldUserTest extends BaseTest {

    private PortalMainPage mainPage;
    private PortalLoginPage loginPage;
    private ExistedAccount account;

    @BeforeClass
    private void getAccountForTest(){
        account = ExistedAccount.getExistedAccount();
    }

    @Description("Registration :: Sign in old user")
    @Epic("Account Registration")
    @Feature("Sign in old user")
    public void signInOldUser(){
        verifyNewAccountSecondLogin();
        verifyNewAccountLogout();
    }

    @Step(value = "Verify new account second log in")
    private void verifyNewAccountSecondLogin(){
        loginPage = PortalLoginPage.openPortalLoginPage();
        mainPage = loginPage.login(account.getEmail(), account.getPass());
        SoftAssert soft = new SoftAssert();

        soft.assertTrue(mainPage.isUpdatePolicyPopUpNotShown(),
                "Update policy pop up is shown in second time");
        soft.assertTrue(mainPage.isGetStartedWindowNotShown(),
                "Landing pop up is shown in second time");
        soft.assertEquals(mainPage.getGreetingMessage(),
                "Welcome, "+ account.getFirstName() + ". Add a solution to your account.",
                "Greeting is not as expected");
        soft.assertTrue(mainPage.getLaunchpad().isGetStartedWithTouchButtonShown(),
                "'Get started' button is not shown");
        soft.assertAll();
    }


    @Step(value = "Verify new account log out")
    private void verifyNewAccountLogout(){
        mainPage.getPageHeader().logoutAdmin();

        Assert.assertTrue(loginPage.isLoginPageOpened(5), "Login page is not opened");
    }



}
