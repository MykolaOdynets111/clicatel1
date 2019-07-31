package endtoend.acceptance;

import com.github.javafaker.Faker;
import endtoend.BaseTest;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import listeners.TestAllureListener;
import mc2api.ApiHelperPlatform;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.testng.internal.BaseTestMethod;
import portalpages.PortalLoginPage;
import portalpages.PortalMainPage;
import portalpages.PortalSignUpPage;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

@Listeners({TestAllureListener.class})
public class SignUpTest1 extends BaseTest {

    private HashMap<String, String> signUpInfo = new HashMap<>();
    private PortalLoginPage loginPage;
    private PortalMainPage mainPage;
    private SoftAssert soft;
    protected String testCaseName = "";
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

        soft = new SoftAssert();

    }

    @Story("Registration account")
    @Test
    @TmsLink("TECH-12069")
    public void newSignUp(){
        PortalSignUpPage.openPortalSignUpPage()
                .provideSignUpDetails(signUpInfo.get("name"), signUpInfo.get("accountName"),
                                        signUpInfo.get("email"), signUpInfo.get("pass"));
//        loginPage = PortalSignUpPage.openPortalSignUpPage()
//
//                .provideSignUpDetails(signUpInfo.get("name"), signUpInfo.get("accountName"),
//                                        signUpInfo.get("email"), signUpInfo.get("pass"))
//                .clickSignUpButton();
//
//        Assert.assertTrue(loginPage.isLoginPageOpened(5),
//                "Login Page is not opened after providing sign up info");
//        Assert.assertEquals(loginPage.getMessageAboutSendingConfirmationEmail(),
//                "A confirmation email has been sent to "+ signUpInfo.get("email") +" to complete your sign up process",
//                "Message about sending confirmation email is incorrect");
//        Assert.assertNotEquals(loginPage.checkConfirmationEmail(signUpInfo.get("accountName"), signUpInfo.get("email"), signUpInfo.get("pass"), 60),
//                "none", "There is no confirmation URL");
//
//        loginPage.openConfirmationURL();
//
//        soft.assertTrue(loginPage.isAccountCreatedMessageShown(),
//                "'Your account has successfully been created!' message is not shown");
//        soft.assertTrue(loginPage.isLoginPageOpened(7),
//                "Login Page is not loaded after 7 seconds wait");
//        soft.assertAll();
//
//
//        mainPage = loginPage.login(signUpInfo.get("email"), signUpInfo.get("pass"));
//
//        soft.assertTrue(mainPage.isUpdatePolicyPopUpOpened(),
//                "Update policy pop up is not shown");
//        soft.assertTrue(mainPage.isLandingPopUpOpened(),
//                "Login Page is not loaded after 7 seconds wait");
//        soft.assertAll();
//
//        String expectedPolicyLink = "https://www.clickatell.com/legal/general-terms-notices/privacy-notice/";
//        String expectedComplianceLink = "https://www.clickatell.com/legal/general-terms-notices/clickatell-maintaining-gdpr-compliance/";
//
//        soft.assertTrue(mainPage.getGdprWindow().clickGDPRPolicyLink()
//                                                .verifyCorrectnessGDPRLink(expectedPolicyLink),
//                "GDPR policy link is incorrect. \n Expected: " + expectedPolicyLink );
//        soft.assertTrue(mainPage.getGdprWindow().clickGDPRComplianceLink()
//                        .verifyCorrectnessGDPRLink(expectedComplianceLink),
//                "GDPR compliance is incorrect. \n Expected: " + expectedComplianceLink);
//        soft.assertAll();
//
//        mainPage.closeUpdatePolicyPopup();
//        mainPage.closeLandingPage();
//
//        soft.assertEquals(mainPage.getGreetingMessage(),
//                "Welcome, "+ signUpInfo.get("firstName") + ". Add a solution to your account.",
//                "Greeting is not as expected");
//        soft.assertTrue(mainPage.isGetStartedWithTouchButtonIsShown(),
//                "'Get started' button is not shown");
//        soft.assertAll();
    }

    @AfterTest(alwaysRun = true)
    private void deactivateTestAccount1(){
//        try {
//            ApiHelperPlatform.closeAccount(signUpInfo.get("accountName"),
//                    signUpInfo.get("email"), signUpInfo.get("pass"));
//        }catch (AssertionError e){
//            // Nothing to do. Account was not activated.
//        }

    }

//    @AfterMethod(alwaysRun = true)
//    public void setResultTestName(ITestResult result) {
//        result.setParameters(new String[] {"Registration account"});
//    }



}
