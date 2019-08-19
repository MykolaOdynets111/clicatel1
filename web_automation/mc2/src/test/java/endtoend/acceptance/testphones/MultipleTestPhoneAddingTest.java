package endtoend.acceptance.testphones;

import datamanager.ExistedAccount;
import endtoend.basetests.BaseTest;
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

import java.util.Arrays;
import java.util.List;


@Listeners({TestAllureListener.class})
@Test(testName = "Test Phone :: Add multiple test phones from SMS Add-ons", groups = {"newaccount", "testphone"})
@TmsLink("TECH-6173")
public class MultipleTestPhoneAddingTest extends BaseTest implements VerificationHelper {

    private PortalMainPage mainPage;
    private PortalConfigureSMSPage configureSMSPage;
    private List<String> testPhones;
    private ExistedAccount account;


    @BeforeClass
    private void generateTestPhones(){
        account = ExistedAccount.getExistedAccount();
        ApiHelperPlatform.deleteAllTestNumbers(account.getAccountName(), account.getEmail(), account.getPass());
        testPhones = Arrays.asList(generateUSCellPhoneNumber(),
                                    generateUSCellPhoneNumber(),
                                    generateUSCellPhoneNumber());
    }


    @Description("Test Phone :: Add multiple test phones from SMS Add-ons")
    @Epic("Test Phone")
    @Feature("Add multiple test phones from SMS Add-ons")
    public void addingMultipleTestPhonesTest(){
        mainPage = PortalLoginPage.openPortalLoginPage().login(account.getEmail(), account.getPass());

        mainPage.getLaunchpad().getTargetSection("Configure test phone")
                .clickButton("Add test phone", 3);
        mainPage.waitWhileProcessing(2, 3);

        validateTestPhoneNumbers();
        verifyManageTestPhonesSection();
        verifySetupTestPhoneSection();
        verifyTestPhonesInFirstIntegration();
    }

    @Step(value = "Provide new test phone numbers")
    private void validateTestPhoneNumbers(){
        SoftAssert soft = new SoftAssert();

        for(String phoneNumber : testPhones) {
            mainPage.getAddTestPhoneWindow().enterTestPhone(phoneNumber)
                    .clickAddPhoneButton()
                    .enterVerificationOTPCode(account.getAccountID(), phoneNumber)
                    .clickRegisterButton();
            soft.assertEquals(mainPage.getAddTestPhoneWindow().getSuccessMessage(),
                    phoneNumber + " has been added to your account.",
                    "Incorrect success message was shown");
            if(testPhones.indexOf(phoneNumber)!=2) mainPage.getAddTestPhoneWindow().clickAddAnotherButton();
        }

        soft.assertAll();
    }

    @Step(value = "Verify new test phones are shown on Launchpad Manage test phones section")
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
                        .getTargetSection("Manage test phones").getTestPhones(), testPhones,
                "'Manage test phones' does not contain provided test numbers");
        soft.assertEquals(mainPage.getLaunchpad()
                        .getTargetSection("Manage test phones").getCardStatus(), "Active",
                "'Manage test phones' section has incorrect status");
        soft.assertAll();
    }

    @Step(value = "Verify test phones on Setup your test phones section")
    private void verifySetupTestPhoneSection(){
        mainPage.getLeftMenu().navigateINLeftMenuWithSubmenu("SMS", "Configure SMS");
        configureSMSPage = new PortalConfigureSMSPage();

        Assert.assertEquals(configureSMSPage.getSection("Setup your test phones").getTestPhone(),
                testPhones,
                "Some of " + testPhones + " test phones are missing on Configure SMS page, Setup your test phones section");
    }

    @Step(value = "Verify test phones in First Integration")
    private void verifyTestPhonesInFirstIntegration(){
        configureSMSPage.getSmsIntegrationsTable().clickOnIntegration("My first test Integration", 1);

        PortalSMSMessagingIntegration smsIntegration = new PortalSMSMessagingIntegration();
        smsIntegration.clickPageNavButton("Phone numbers");

        Assert.assertEquals(smsIntegration.getTestPhonesFromIntegration(), testPhones,
                "Some of " + testPhones + " test phones are not correct on SMS integration page, Phone numbers section");
    }

}
