package endtoend.acceptance;

import com.github.javafaker.Faker;
import dbmanager.DBConnector;
import driverfactory.MC2DriverFactory;
import drivermanager.ConfigManager;
import endtoend.BaseTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import mc2api.ApiHelperPlatform;
import mc2api.EndpointsPlatform;
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
    private SoftAssert soft;

    @BeforeTest
    public void prepareSignUpInfo(){
        System.setProperty("env", "qa");
        Faker faker = new Faker();
        signUpInfo.put("name", faker.name().fullName());
        signUpInfo.put("accountName", "aqa_" + faker.lorem().word() + faker.number().digits(3));
        signUpInfo.put("email", "automationmc2@gmail.com");
        signUpInfo.put("pass", "p@$$w0rd4te$t");
    }

    @BeforeMethod
    public void initializeSoftAssert(){
        soft = new SoftAssert();
    }

    @Description("New account sign up request")
    @Test
    public void newSignUpRequest(){
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

    @Description("New account activation")
    @Test(dependsOnMethods = {"newSignUpRequest"})
    public void newAccountActivation(){
        loginPage.openConfirmationURL();

        soft.assertTrue(loginPage.isAccountCreatedMessageShown(),
                "'Your account has successfully been created!' message is not shown");
        soft.assertTrue(loginPage.isLoginPageOpened(7),
                "Login Page is not loaded after 7 seconds wait");
        soft.assertAll();

    }

    @Description("New account first Sing Up")
    @Test(dependsOnMethods = {"newAccountActivation"})
    public void newAccountFirstSingUp(){
        mainPage = loginPage.login(signUpInfo.get("email"), signUpInfo.get("pass"));

        soft.assertTrue(mainPage.isUpdatePolicyPopUpOpened(),
                "Update policy pop up is not shown");
        soft.assertTrue(mainPage.isLandingPopUpOpened(),
                "Login Page is not loaded after 7 seconds wait");
        soft.assertAll();
    }

    @AfterTest(alwaysRun = true)
    public void deactivateTestAccount(){
//        String activationId = DBConnector.getAccountActivationIdFromMC2DB(ConfigManager.getEnv(),
//                MC2Account.getTouchGoAccount().getAccountName());
        String activationId = DBConnector.getAccountActivationIdFromMC2DB(ConfigManager.getEnv(),
                signUpInfo.get("accountName"));
        if(activationId != null){
            String activationURL = String.format(EndpointsPlatform.PORTAL_ACCOUNT_ACTIVATION, activationId);
        } else {
            ApiHelperPlatform.closeAccount(signUpInfo.get("accountName"),
                    signUpInfo.get("email"), signUpInfo.get("pass"));
        }
    }

}
