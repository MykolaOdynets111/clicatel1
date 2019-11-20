package endtoend.acceptance.billingandpayments;

import endtoend.basetests.APICreatedAccountTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@Listeners({TestAllureListener.class})
@Test(testName = "Billing & Payments :: Add billing details", groups = {"newaccount", "billingspayments"})
@TmsLink("TECH-5826")
public class BillingDetailsAddingTest extends APICreatedAccountTest  {

    private PortalMainPage mainPage;
    private PortalBillingDetailsPage billingDetailsPage;
    private SoftAssert soft;
    private Map details;

    @BeforeClass
    private void generateTestPhone(){
        billingDetailsPage = new PortalBillingDetailsPage();
        soft = new SoftAssert();
    }


    @Description("Billing & Payments :: Add billing details")
    @Epic("Billing & Payments")
    @Feature("Add billing details")
    public void addingBillingDetails(){
        mainPage = PortalLoginPage.openPortalLoginPage().login(email.get(), pass.get());
        mainPage.getLeftMenu().navigateINLeftMenuWithSubmenu("Settings", "Billing & payments");

        verifyEmptyBillingSaving();
        provideBillingINFO();
        changeZipCode();

        soft.assertAll();
    }

    @Step(value = "Try to save empty billing details")
    private void verifyEmptyBillingSaving(){
        billingDetailsPage.clickPageActionButton("Save changes");
        Map<Boolean, List<String>> inputFieldsResult = billingDetailsPage.getBillingContactsDetails()
                                                                                .verifyMandatoryInputFields();
        Map<Boolean, List<String>> dropdownResult = billingDetailsPage.getBillingContactsDetails()
                                                                                .verifyMandatoryDropdowns();

        soft.assertTrue(inputFieldsResult.containsKey(true), "The following fields are not marked as mandatory: \n" +
                inputFieldsResult.get(false));
        soft.assertTrue(dropdownResult.containsKey(true), "The following dropdowns are not marked as mandatory: \n" +
                dropdownResult.get(false));
    }

    @Step(value = "Verify saving valid billing info")
    private void provideBillingINFO(){
        details = billingDetailsPage.getBillingContactsDetails().fillInBillingDetailsForm();
        saveNewAccountCompanyName();
        billingDetailsPage.waitWhileProcessing(2,9);

        soft.assertEquals(billingDetailsPage.getNotificationAlertText(),
                "Your changes have been saved successfully",
                "Billing saving success notification is not as expected");
    }

    @Step(value = "Verify updating Zip/Postal code")
    private void changeZipCode(){
        billingDetailsPage.getBillingContactsDetails().enterPostalCode("79032")
                                                        .clickSaveChanges();
        billingDetailsPage.waitWhileProcessing(2,9);

        soft.assertEquals(billingDetailsPage.getNotificationAlertText(),
                "Your changes have been saved successfully",
                "Billing saving success notification is not as expected");
        soft.assertEquals(billingDetailsPage.getBillingContactsDetails().getPostalCode(), "79032",
                "Postal code was not changed after updating");
    }

    private void saveNewAccountCompanyName(){
        try(FileInputStream in = new FileInputStream("src/test/resources/newapiaccount.properties");
            FileOutputStream out = new FileOutputStream("src/test/resources/newapiaccount.properties");
        ) {
            Properties props = new Properties();
            props.load(in);

            props.setProperty("companyName", (String) details.get("companyName"));
            props.store(out, null);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
