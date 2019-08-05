package endtoend.acceptance;


import emailhelper.CheckEmail;
import emailhelper.GmailConnector;
import endtoend.basetests.SignUpBaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.PortalLoginPage;
import portalpages.PortalMainPage;


@Test(testName = "Registration :: Change password", groups = {"newaccount"}, dependsOnGroups = {"secondlogin"}, alwaysRun = true)
@TmsLink("TECH-12039")
public class ChangePasswordTest extends SignUpBaseTest {

    private PortalMainPage mainPage;
    private PortalLoginPage loginPage;
    private String newPass = "newp@ssw0rd";

    @BeforeClass(alwaysRun = true)
    private void cleanUpTestEmailBox(){
        GmailConnector.loginAndGetInboxFolder(email.get(), pass.get());
        CheckEmail.clearEmailInbox();
    }

    @Description("Registration :: Change password")
    @Epic("Account Registration")
    @Feature("Change password via Forgot email")
    public void changePassword(){
        verifyForgotPasswordEmail();
        verifySetPassPage();
        changePass();
        verifyOldPass();
        verifyNewPass();
    }

    @Step(value = "Verify forgot password email sent")
    private void verifyForgotPasswordEmail(){
        loginPage = PortalLoginPage.openPortalLoginPage();
        loginPage.getAccountForm().clickForgotPasswordLink();
        loginPage.enterEmailAndSubmit(email.get());

        SoftAssert soft = new SoftAssert();

        soft.assertTrue(loginPage.getAccountForm().isForgotPasswordSentNotificationShown(),
                "\"Your Forgot Password email has been sent!\" message is not displayed");
        soft.assertFalse(loginPage.getAccountForm().isInstructionsEmpty(),
                "Forgot Password instructions are empty");
        soft.assertNotEquals(loginPage.checkResetPassConfirmationEmail(email.get(), pass.get(), 60),
                "none", "There is no confirmation URL about password reset");
        soft.assertAll();
    }

    @Step(value = "Verify \"Set new password\" page opened")
    private void verifySetPassPage(){
        loginPage.openConfirmationURL();

        SoftAssert soft = new SoftAssert();

        soft.assertTrue(loginPage.getAccountForm().isSetNewPasswordLabelShown(5),
                "\"Set new password\" page is not loaded after 5 seconds wait");
        soft.assertTrue(loginPage.getAccountForm().areCreatePasswordInputsShown(3),
                "Two fields for setting new password not shown");
        soft.assertAll();
    }

    @Step(value = "Change password")
    private void changePass(){
        loginPage.getAccountForm().createNewPass(newPass)
                                    .clickLogin();

        SoftAssert soft = new SoftAssert();

        soft.assertEquals(loginPage.getNotificationAlertText(),
                "Password has been changed successfully",
                "\"Password has been changed successfully\" message is not displayed");
        soft.assertTrue(loginPage.isLoginPageOpened(5),
                "Login page is not opened");
        soft.assertAll();
    }

    @Step(value = "Verify Login with an old password ")
    private void verifyOldPass(){
        loginPage.waitForNotificationAlertToBeProcessed(1, 6);
        loginPage.getAccountForm().enterAdminCreds(email.get(), pass.get())
                                    .clickLogin();

        SoftAssert soft = new SoftAssert();

        soft.assertEquals(loginPage.getNotificationAlertText(),
                "Username or password is invalid",
                "\"Username or password is invalid\" message is not displayed");
        soft.assertTrue(loginPage.isLoginPageOpened(5),
                "Login page is not opened");
        soft.assertAll();
    }


    @Step(value = "Verify Login with a new password ")
    private void verifyNewPass(){
        loginPage.waitForNotificationAlertToBeProcessed(1, 6);
        mainPage = loginPage.login(email.get(), newPass);

        Assert.assertTrue(mainPage.isPortalPageOpened(), "Main page is not loaded");
    }


}
