package endtoend.acceptance.signup;

import com.github.javafaker.Faker;
import emailhelper.CheckEmail;
import emailhelper.GmailConnector;
import endtoend.basetests.BaseTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import portalpages.PortalLoginPage;
import portalpages.PortalMainPage;
import portalpages.PortalSignUpPage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

@Listeners({TestAllureListener.class})
@Test(testName = "Registration :: Sign up", groups = {"newaccount", "registration"})
@TmsLink("TECH-12068")
public class SignUpTest extends BaseTest {

    private HashMap<String, String> signUpInfo = new HashMap<>();
    private PortalLoginPage loginPage;
    private PortalMainPage mainPage;
    Faker faker;

    @BeforeClass
    private void prepareSignUpInfo(){
        faker = new Faker();
        signUpInfo.put("firstName", faker.name().firstName());
        signUpInfo.put("lastName", faker.name().lastName());
        signUpInfo.put("name", signUpInfo.get("firstName") + " " + signUpInfo.get("lastName"));
        signUpInfo.put("accountName", "aqa_" + faker.lorem().word() + faker.number().digits(3));
        signUpInfo.put("email", "automationmc2+" + System.currentTimeMillis() +"@gmail.com");
        signUpInfo.put("pass", "p@$$w0rd4te$t");
        saveNewAccountProperties();

        GmailConnector.loginAndGetInboxFolder(signUpInfo.get("email"), signUpInfo.get("pass"));
        CheckEmail.clearEmailInbox();
    }

    @Description("Registration :: Sign up")
    @Epic("Account Registration")
    @Feature("Sign Up")
    public void registrationSignUp(){
        verifyNewSignUpRequest();
        verifyNewAccountActivation();
        verifyNewAccountFirstSingUp();
        verifyNewAccountGDPRLinks();
        verifyWelcomeNewUser();
    }


    @Step(value = "Verify sign up request sending")
    private void verifyNewSignUpRequest(){
        loginPage = PortalSignUpPage.openPortalSignUpPage()
                .provideSignUpDetails(signUpInfo.get("name"), signUpInfo.get("accountName"),
                                        signUpInfo.get("email"), signUpInfo.get("pass"))
                .clickSignUpButton();
        loginPage.waitWhileProcessing(1,5);

        Assert.assertTrue(loginPage.isLoginPageOpened(5),
                "Login Page is not opened after providing sign up info");
        Assert.assertEquals(loginPage.getAccountForm().getMessageAboutSendingConfirmationEmail(),
                "A confirmation email has been sent to "+ signUpInfo.get("email") +" to complete your sign up process",
                "Message about sending confirmation email is incorrect");
        Assert.assertNotEquals(loginPage.checkConfirmationEmail(signUpInfo.get("accountName"), signUpInfo.get("email"), signUpInfo.get("pass"), 60),
                "none", "There is no confirmation letter");
    }

    @Step(value = "Verify new account activation")
    private void verifyNewAccountActivation(){
        loginPage.openConfirmationURL();
        loginPage.waitWhileProcessing(2,5);

        Assert.assertTrue(loginPage.isAccountCreatedMessageShown(),
                "'Your account has successfully been created!' message is not shown");
        Assert.assertTrue(loginPage.isLoginPageOpened(7),
                "Login Page is not loaded after 7 seconds wait");
    }

    @Step(value = "Verify new account first log in")
    private void verifyNewAccountFirstSingUp(){
        mainPage = loginPage.login(signUpInfo.get("email"), signUpInfo.get("pass"));
        SoftAssert soft = new SoftAssert();

        soft.assertTrue(mainPage.isUpdatePolicyPopUpOpened(),
                "Update policy pop up is not shown");
        soft.assertTrue(mainPage.isGetStartedWindowShown(),
                "Landing pop up is not shown");
        soft.assertAll();
    }

    @Step(value = "Verify new account GDPR links")
    private void verifyNewAccountGDPRLinks(){
        SoftAssert soft = new SoftAssert();
        String expectedPolicyLink = "https://www.clickatell.com/legal/general-terms-notices/privacy-notice/";
        String expectedComplianceLink = "https://www.clickatell.com/legal/general-terms-notices/clickatell-maintaining-gdpr-compliance/";

        soft.assertEquals(mainPage.getGdprWindow().clickGDPRPolicyLink().getGDPRLink(),  expectedPolicyLink,
                "GDPR policy link is incorrect. \n Expected: " + expectedPolicyLink );
        soft.assertEquals(mainPage.getGdprWindow().clickGDPRComplianceLink().getGDPRLink(), expectedComplianceLink,
                "GDPR compliance is incorrect. \n Expected: " + expectedComplianceLink);
        soft.assertAll();
    }

    @Step(value = "Verify welcoming new user")
    private void verifyWelcomeNewUser(){
        SoftAssert soft = new SoftAssert();

        mainPage.closeUpdatePolicyPopup();
        mainPage.closeGetStartedWindow();

        soft.assertEquals(mainPage.getGreetingMessage(),
                "Welcome, "+ signUpInfo.get("firstName") + ". Add a solution to your account.",
                "Greeting is not as expected");
        soft.assertTrue(mainPage.isGetStartedButtonShown(),
                "'Get started' button is not shown");
        soft.assertAll();
    }

    private void saveNewAccountProperties(){
        try {
            FileInputStream in = new FileInputStream("src/test/resources/newaccount.properties");
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream("src/test/resources/newaccount.properties");
            props.setProperty("accountName", signUpInfo.get("accountName"));
            props.setProperty("email", signUpInfo.get("email"));
            props.setProperty("pass", signUpInfo.get("pass"));
            props.setProperty("firstName", signUpInfo.get("firstName"));
            props.setProperty("lastName", signUpInfo.get("lastName"));
            props.setProperty("name", signUpInfo.get("firstName") + " " + signUpInfo.get("lastName"));

            props.store(out, null);
            out.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
