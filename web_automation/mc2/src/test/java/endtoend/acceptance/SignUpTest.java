package endtoend.acceptance;

import com.github.javafaker.Faker;
import endtoend.BaseTest;
import io.qameta.allure.Step;
import io.qameta.allure.TmsLink;
import listeners.TestAllureListener;
import mc2api.ApiHelperPlatform;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import portalpages.PortalLoginPage;
import portalpages.PortalMainPage;
import portalpages.PortalSignUpPage;

import java.util.HashMap;

@Listeners({TestAllureListener.class})
@Test(testName = "Registration :: Sign up")
@TmsLink("TECH-12068")
public class SignUpTest extends BaseTest {

    private HashMap<String, String> signUpInfo = new HashMap<>();
    private PortalLoginPage loginPage;
    private PortalMainPage mainPage;
    Faker faker;

    @BeforeTest
    private void prepareSignUpInfo(){
        System.setProperty("env", "qa");
        faker = new Faker();
        signUpInfo.put("firstName", faker.name().firstName());
        signUpInfo.put("lastName", faker.name().lastName());
        signUpInfo.put("name", signUpInfo.get("firstName") + " " + signUpInfo.get("lastName"));
        signUpInfo.put("accountName", "aqa_" + faker.lorem().word() + faker.number().digits(3));
        signUpInfo.put("email", "automationmc2+" + System.currentTimeMillis() +"@gmail.com");
        signUpInfo.put("pass", "p@$$w0rd4te$t");
    }


    @Test(testName = "Registration :: Sign up method")
    public void registrationSignUp(){
        verifyNewSignUpRequest();
        verifyNewAccountActivation();
        verifyNewAccountFirstSingUp();
        verifyNewAccountGDPRLinks();
        verifyWelcomeNewUser();
    }

    @AfterTest(alwaysRun = true)
    public void deactivateTestAccount(){
        try {
            ApiHelperPlatform.closeAccount(signUpInfo.get("accountName"),
                    signUpInfo.get("email"), signUpInfo.get("pass"));
        }catch (AssertionError e){
            // Nothing to do. Account was not activated.
        }

    }

    @Step(value = "Verify sign up request sending")
    private void verifyNewSignUpRequest(){
        loginPage = PortalSignUpPage.openPortalSignUpPage()
                .provideSignUpDetails(signUpInfo.get("name"), signUpInfo.get("accountName"),
                                        signUpInfo.get("email"), signUpInfo.get("pass"))
                .clickSignUpButton();

        Assert.assertTrue(loginPage.isLoginPageOpened(5),
                "Login Page is not opened after providing sign up info");
        Assert.assertEquals(loginPage.getMessageAboutSendingConfirmationEmail(),
                "A confirmation email has been sent to "+ signUpInfo.get("email") +" to complete your sign up process",
                "Message about sending confirmation email is incorrect");
        Assert.assertNotEquals(loginPage.checkConfirmationEmail(signUpInfo.get("accountName"), signUpInfo.get("email"), signUpInfo.get("pass"), 60),
                "none", "There is no confirmation URL");
    }

    @Step(value = "Verify new account activation")
    private void verifyNewAccountActivation(){
        loginPage.openConfirmationURL();

        Assert.assertTrue(loginPage.isAccountCreatedMessageShown(),
                "'Your account has successfully been created!' message is not shown");
        Assert.assertTrue(loginPage.isLoginPageOpened(7),
                "Login Page is not loaded after 7 seconds wait");
    }

    @Step(value = "Verify new account first sign up")
    private void verifyNewAccountFirstSingUp(){
        mainPage = loginPage.login(signUpInfo.get("email"), signUpInfo.get("pass"));
        SoftAssert soft = new SoftAssert();

        soft.assertTrue(mainPage.isUpdatePolicyPopUpOpened(),
                "Update policy pop up is not shown");
        soft.assertTrue(mainPage.isLandingPopUpOpened(),
                "Login Page is not loaded after 7 seconds wait");
        soft.assertAll();
    }

    @Step(value = "Verify new account GDPR links")
    private void verifyNewAccountGDPRLinks(){
        SoftAssert soft = new SoftAssert();
        String expectedPolicyLink = "https://www.clickatell.com/legal/general-terms-notices/privacy-notice/";
        String expectedComplianceLink = "https://www.clickatell.com/legal/general-terms-notices/clickatell-maintaining-gdpr-compliance/";

        soft.assertTrue(mainPage.getGdprWindow().clickGDPRPolicyLink()
                                                .verifyCorrectnessGDPRLink(expectedPolicyLink),
                "GDPR policy link is incorrect. \n Expected: " + expectedPolicyLink );
        soft.assertTrue(mainPage.getGdprWindow().clickGDPRComplianceLink()
                        .verifyCorrectnessGDPRLink(expectedComplianceLink),
                "GDPR compliance is incorrect. \n Expected: " + expectedComplianceLink);
        soft.assertAll();
    }

    @Step(value = "Verify welcoming new user")
    private void verifyWelcomeNewUser(){
        SoftAssert soft = new SoftAssert();

        mainPage.closeUpdatePolicyPopup();
        mainPage.closeLandingPage();

        soft.assertEquals(mainPage.getGreetingMessage(),
                "Welcome, "+ signUpInfo.get("firstName") + ". Add a solution to your account.",
                "Greeting is not as expected");
        soft.assertTrue(mainPage.isGetStartedWithTouchButtonIsShown(),
                "'Get started' button is not shown");
        soft.assertAll();
    }


}
