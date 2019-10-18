package endtoend.acceptance;

import com.github.javafaker.Faker;
import datamanager.model.AccountSignUp;
import endtoend.basetests.BaseTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import mc2api.ApiHelperPlatform;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.PortalLoginPage;
import portalpages.PortalMainPage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Listeners({TestAllureListener.class})
@Test(testName = "Launchpad :: Check sections for new account", groups = {"newaccount", "launchpad"})
@TmsLink("TECH-5731")
public class LaunchpadTest extends BaseTest {

    private String accountID;
    private PortalMainPage mainPage;
    private AccountSignUp accountSignUp;
    private Map<String, String> navButtons = new LinkedHashMap<String, String>() {{
                                                put("Welcome", "Welcome to your Clickatell account");
                                                put("SMS", "SMS");
                                                put("WhatsApp", "WhatsApp");
                                                put("One API", "One API");
                                                put("Touch", "Touch");
                                            }};

    @BeforeClass()
    private void createAccount() {
        Faker faker = new Faker();
        accountSignUp = new AccountSignUp();
        accountSignUp.setAccountName( "aqa_" + faker.lorem().word() + faker.number().digits(3))
                        .setPassword("p@$$w0rd4te$t")
                        .setEmail("aqatest_" + System.currentTimeMillis() +"@gmail.com")
                        .setFirstName("Clickatell")
                        .setLastName("Test");

        accountID = ApiHelperPlatform.createNewAccount(accountSignUp);
        saveNewAccountProperties();
    }

    @Description("Launchpad :: Check sections for new account")
    @Epic("Launchpad")
    @Feature("Check sections for new account")
    public void checkLaunchpadSectionsNewAccount(){
        verifySectionsPresence();
        verifyGetStartedNavMenu();
        verifyPreviousButton();
        verifyNextButton();
        reopenGetStartedWindow();
        verifyLaunchPadSectionsAfterFirstLogin();
        verifyLaunchPadSectionsAfterSecondLogin();
    }

    @Step(value = "Verify Get Started sections presence")
    private void verifySectionsPresence(){
        mainPage = PortalLoginPage.openPortalLoginPage()
                        .login(accountSignUp.getEmail(), accountSignUp.getPassword());

        mainPage.closeUpdatePolicyPopup();
        mainPage.makeSureGetStartedWindowOpened();
        Assert.assertEquals(mainPage.getStartedWindow().getIntegrationStepsLbls(), navButtons.keySet(),
                "There are missing section on Get started window");
    }

    @Step(value = "Verify Get Started navigation via nav buttons")
    private void verifyGetStartedNavMenu(){
        SoftAssert soft = new SoftAssert();
        mainPage.closeUpdatePolicyPopup();
        for(String navButton : navButtons.keySet()){
            mainPage.getStartedWindow().clickNavButton(navButton);
            soft.assertTrue(mainPage.getStartedWindow().isNavButtonOnFocus(navButton),
                    navButton + " navigation button is not in focus after clicking it");
            soft.assertTrue(mainPage.getStartedWindow().isSectionTitleCorrect(navButtons.get(navButton)),
                    "Section title is not as expected after clicking '" + navButton + "' nav button." );
        }

        soft.assertAll();
    }

    @Step(value = "Verify Previous button")
    private void verifyPreviousButton(){
        SoftAssert soft = new SoftAssert();
        List<String> reversedSections = new ArrayList<>(navButtons.keySet());
        Collections.reverse(reversedSections);
        for(int i = 1; i < reversedSections.size(); i++){
            String navButton = reversedSections.get(i);
            mainPage.getStartedWindow().clickPreviousButton();

            soft.assertTrue(mainPage.getStartedWindow().isNavButtonOnFocus(navButton),
                    navButton + " navigation button is not in focus after clicking 'Previous' button");
            soft.assertTrue(mainPage.getStartedWindow().isSectionTitleCorrect(navButtons.get(navButton)),
                    "Section title is not as expected after clicking 'Previous' button'." );
        }

        soft.assertAll();
    }

    @Step(value = "Verify Next button")
    private void verifyNextButton(){
        SoftAssert soft = new SoftAssert();
        List<String> sections = new ArrayList<>(navButtons.keySet());

        for(int i = 1; i < navButtons.keySet().size(); i++){
            String navButton = sections.get(i);
            mainPage.getStartedWindow().clickNextButton();

            soft.assertTrue(mainPage.getStartedWindow().isNavButtonOnFocus(navButton),
                    navButton + " navigation button is not in focus after clicking 'Next' button");
            soft.assertTrue(mainPage.getStartedWindow().isSectionTitleCorrect(navButtons.get(navButton)),
                    "Section title is not as expected after clicking 'Next' button'." );
        }

        soft.assertAll();
    }

    @Step(value = "Verify re-opening Get Started window")
    private void reopenGetStartedWindow(){
        mainPage.getStartedWindow().clickNextButton();
        mainPage.clickGetStartedButton();

        Assert.assertTrue(mainPage.isGetStartedWindowShown(), "'Get Started' window is not re-opened");
    }

    @Step(value = "Verify Launchpad elements after first login")
    private void verifyLaunchPadSectionsAfterFirstLogin(){
        mainPage.closeGetStartedWindow();
        SoftAssert soft = new SoftAssert();

        soft.assertTrue(mainPage.isGetStartedButtonShown(), "'Get Started' button is not shown");
        soft.assertTrue(mainPage.getPageHeader().isTopUpBalanceButtonShown(),
                "'Top up balance' button is not shown");
        soft.assertEquals(mainPage.getLaunchpad().getNumberOfSections(), 3,
                "Unexpected number of Launchpad sections after first Login\n");

        verifySMSSection(soft);
        verifyWhatsAppSection(soft);
        verifyTouchSection(soft);

        soft.assertAll();
    }

    @Step(value = "Verify Launchpad elements after re-login")
    private void verifyLaunchPadSectionsAfterSecondLogin(){
        mainPage.getPageHeader().logoutAdmin();
        PortalLoginPage.openPortalLoginPage().login(accountSignUp.getEmail(), accountSignUp.getPassword());

        SoftAssert soft = new SoftAssert();

        soft.assertTrue(mainPage.isGetStartedWindowNotShown(),
                "Landing pop up is shown in second time");
        soft.assertTrue(mainPage.isGetStartedButtonShown(), "'Get Started' button is not shown");
        soft.assertTrue(mainPage.getPageHeader().isTopUpBalanceButtonShown(),
                "'Top up balance' button is not shown");

        verifySMSSection(soft);
        verifyWhatsAppSection(soft);
        verifyTouchSection(soft);
        verifySMSAddOnsSection(soft);
        verifyConfigureTestPhoneSection(soft);

        soft.assertAll();
    }

    @Step(value = "Verify SMS Launchpad section")
    private void verifySMSSection(SoftAssert soft){

        soft.assertTrue(mainPage.getLaunchpad().isSectionPresent("SMS APIs",2),
                "'SMS APIs' section is not present on Launchpad");
        soft.assertTrue(mainPage.getLaunchpad()
                        .getTargetSection("SMS APIs").isButtonDisplayed("Add test phone", 1),
                "'Add test phone' button is not present in 'SMS APIs' section on Launchpad");
        soft.assertTrue(mainPage.getLaunchpad()
                        .getTargetSection("SMS APIs").isButtonDisplayed("Manage SMS integrations", 1),
                "'Manage SMS integrations' button is not present in 'SMS APIs' section on Launchpad");
        soft.assertEquals(mainPage.getLaunchpad()
                        .getTargetSection("SMS APIs").getCardStatus(), "Testing in progress",
                "'SMS APIs' section has incorrect status");
    }

    @Step(value = "Verify WhatsApp APIs Launchpad section")
    private void verifyWhatsAppSection(SoftAssert soft){

        soft.assertTrue(mainPage.getLaunchpad().isSectionPresent("WhatsApp APIs",2),
                "'WhatsApp APIs' section is not present on Launchpad");
        soft.assertTrue(mainPage.getLaunchpad()
                        .getTargetSection("WhatsApp APIs").isButtonDisplayed("Apply for WhatsApp", 1),
                "'Apply for WhatsApp' button is not present in 'WhatsApp APIs' section on Launchpad");
        soft.assertEquals(mainPage.getLaunchpad()
                        .getTargetSection("WhatsApp APIs").getCardStatus(), "Not enabled",
                "'WhatsApp APIs' section has incorrect status");
    }

    @Step(value = "Verify Touch Launchpad section")
    private void verifyTouchSection(SoftAssert soft){

        soft.assertTrue(mainPage.getLaunchpad().isSectionPresent("Clickatell Touch",2),
                "'Touch' section is not present on Launchpad");
        soft.assertTrue(mainPage.getLaunchpad()
                        .getTargetSection("Clickatell Touch").isButtonDisplayed("Get started with Touch", 1),
                "'Get started with Touch' button is not present in 'Touch' section on Launchpad");
        soft.assertEquals(mainPage.getLaunchpad()
                        .getTargetSection("Clickatell Touch").getCardStatus(), "Not enabled",
                "'Touch' section has incorrect status");
    }

    @Step(value = "Verify SMS Add Ons Launchpad section")
    private void verifySMSAddOnsSection(SoftAssert soft){

        soft.assertTrue(mainPage.getLaunchpad().isSectionPresent("SMS Add Ons",2),
                "'SMS Add Ons' section is not present on Launchpad");
        soft.assertTrue(mainPage.getLaunchpad()
                        .getTargetSection("SMS Add Ons").isButtonDisplayed("Get a long number", 1),
                "'Get a long number' button is not present in 'SMS Add Ons' section on Launchpad");
        soft.assertTrue(mainPage.getLaunchpad()
                        .getTargetSection("SMS Add Ons").isButtonDisplayed("Apply for a short code", 1),
                "'Apply for a short code' button is not present in 'SMS Add Ons' section on Launchpad");
        soft.assertEquals(mainPage.getLaunchpad()
                        .getTargetSection("SMS Add Ons").getCardStatus(), "Not enabled",
                "'Apply for a short code' section has incorrect status");
    }

    @Step(value = "Verify Configure test phone Launchpad section")
    private void verifyConfigureTestPhoneSection(SoftAssert soft){

        soft.assertTrue(mainPage.getLaunchpad().isSectionPresent("Configure test phone",2),
                "'Configure test phone' section is not present on Launchpad");
        soft.assertTrue(mainPage.getLaunchpad()
                        .getTargetSection("Configure test phone").isButtonDisplayed("Add test phone", 1),
                "'Add test phone' button is not present in 'Configure test phone' section on Launchpad");
        soft.assertEquals(mainPage.getLaunchpad()
                        .getTargetSection("Configure test phone").getCardStatus(), "Not enabled",
                "'Configure test phone' section has incorrect status");
    }

    private void saveNewAccountProperties(){
        try(FileInputStream in = new FileInputStream("src/test/resources/newapiaccount.properties");
            FileOutputStream out = new FileOutputStream("src/test/resources/newapiaccount.properties");
        ) {

            Properties props = new Properties();
            props.load(in);

            props.setProperty("accountName", accountSignUp.getAccountName());
            props.setProperty("email", accountSignUp.getEmail());
            props.setProperty("pass", accountSignUp.getPassword());
            props.setProperty("firstName", accountSignUp.getFirstName());
            props.setProperty("lastName", accountSignUp.getLastName());
            props.setProperty("name", accountSignUp.getFirstName() + " " + accountSignUp.getLastName());
            props.setProperty("accountID", accountID);

            props.store(out, null);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
