package endtoend.acceptance;

import endtoend.basetests.APICreatedAccountTest;
import interfaces.VerificationHelper;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import mc2api.ApiHelperPlatform;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.*;


@Listeners({TestAllureListener.class})
@Test(testName = "Test Phone :: Add test phone from Launchpad", groups = {"newaccount", "testphone"})
@TmsLink("TECH-5745")
public class TestPhoneAddingTest extends APICreatedAccountTest implements VerificationHelper {

    private PortalMainPage mainPage;
    private PortalManageTestPhonesPage testPhonesPage;
    private PortalConfigureSMSPage configureSMSPage;
    private String testPhone;

    @BeforeClass
    private void generateTestPhone(){
        ApiHelperPlatform.deleteAllTestNumbers(accountName.get(), email.get(), pass.get());
        testPhone = generateUSCellPhoneNumber();
    }


    @Description("Test Phone :: Add test phone from Launchpad")
    @Epic("Test Phone")
    @Feature("Add test phone from Launchpad")
    public void addingTestPhoneTest(){
        mainPage = PortalLoginPage.openPortalLoginPage().login(email.get(), pass.get());

        validateTestPhoneNumber();
        verifyManageTestPhonesSection();
        verifySMSpage();
        verifySetupTestPhoneSection();
        verifyTestPhoneInFirstIntegration();
    }

    @Step(value = "Provide new test phone number")
    private void validateTestPhoneNumber(){
        mainPage.getLaunchpad().getTargetSection("Configure test phone")
                .clickButton("Add test phone", 3);
        mainPage.waitWhileProcessing(2, 3);

        mainPage.getAddTestPhoneWindow().enterTestPhone(testPhone)
                                        .clickAddPhoneButton()
                                        .enterVerificationOTPCode(accountID.get(), testPhone)
                                        .clickRegisterButton();
        Assert.assertEquals(mainPage.getAddTestPhoneWindow().getSuccessMessage(),
                testPhone + " has been added to your account.",
                "Incorrect success message was shown");
    }

    @Step(value = "Verify new test phone shown on Launchpad Manage test phones section")
    private void verifyManageTestPhonesSection(){
        SoftAssert soft = new SoftAssert();

        mainPage.getAddTestPhoneWindow().clickFinishButton();
        mainPage.waitWhileProcessing(1, 2);

        soft.assertTrue(mainPage.getLaunchpad().isSectionPresent("Manage test phones",2),
                "'Manage test phones' section is not present on Launchpad");
        soft.assertTrue(mainPage.getLaunchpad()
                        .getTargetSection("Manage test phones").isButtonDisplayed("Add another test phone", 1),
                "'Add another test phone' button is not present in 'Manage test phones' section on Launchpad");
        soft.assertEquals(mainPage.getLaunchpad()
                        .getTargetSection("Manage test phones").getTestPhones().get(0), testPhone,
                "'Manage test phones' does not contain provided test number");
        soft.assertEquals(mainPage.getLaunchpad()
                        .getTargetSection("Manage test phones").getCardStatus(), "Active",
                "'Manage test phones' section has incorrect status");
        soft.assertAll();
    }

    @Step(value = "Verify test phone on SMS page")
    private void verifySMSpage(){
        mainPage.getLeftMenu().navigateINLeftMenuWithSubmenu("SMS", "Test phones");
        testPhonesPage = new PortalManageTestPhonesPage();

        Assert.assertTrue(testPhonesPage.getManageTestPhonesTable().isPhoneNumberAdded(testPhone, 3),
                testPhone + " test phone is missing on Manage test phones page");
    }

    @Step(value = "Verify test phone on Setup your test phones section")
    private void verifySetupTestPhoneSection(){
        mainPage.getLeftMenu().navigateINLeftMenuWithSubmenu("SMS", "Configure SMS");
        configureSMSPage = new PortalConfigureSMSPage();

        Assert.assertEquals(configureSMSPage.getSection("Setup your test phones").getTestPhone().get(0),
                testPhone,
                testPhone + " test phone is missing on Configure SMS page, Setup your test phones section");
    }

    @Step(value = "Verify test phone in First Integration")
    private void verifyTestPhoneInFirstIntegration(){
        configureSMSPage.getSmsIntegrationsTable().clickOnIntegration("My first test Integration", 1);

        PortalSMSMessagingIntegration smsIntegration = new PortalSMSMessagingIntegration();
        smsIntegration.clickPageNavButton("Phone numbers");

        Assert.assertEquals(smsIntegration.getTestPhonesFromIntegration().get(0), testPhone,
                testPhone + " test phone is not correct on SMS integration page, Phone numbers section");
    }
}
