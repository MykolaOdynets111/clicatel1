package endtoend.acceptance.billingandpayments;

import endtoend.basetests.APICreatedAccountTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import mc2api.ApiHelperPlatform;
import mc2api.PortalAuthToken;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.PortalBillingDetailsPage;
import portalpages.PortalLoginPage;
import portalpages.PortalMainPage;


@Listeners({TestAllureListener.class})
@Test(testName = "Billing & Payments :: Add credit card on Payment methods page", groups = {"newaccount", "billingspayments"})
@TmsLink("TECH-5828")
public class AddPaymentTest extends APICreatedAccountTest  {

    private PortalMainPage mainPage;
    private PortalBillingDetailsPage billingDetailsPage;
    private SoftAssert soft;

    @BeforeClass
    private void generateTestPhone(){
        billingDetailsPage = new PortalBillingDetailsPage();
        soft = new SoftAssert();
        String authToken = PortalAuthToken.getAccessTokenForPortalUser(accountName.get(), email.get(), pass.get());
        ApiHelperPlatform.deleteAllPayments(authToken, "CREDIT_CARD");
    }


    @Description("Billing & Payments :: Add credit card on Payment methods page")
    @Epic("Billing & Payments")
    @Feature("Add credit card on Payment methods page")
    public void addingPaymentMethod(){

        mainPage = PortalLoginPage.openPortalLoginPage().login(email.get(), pass.get());
        mainPage.getLeftMenu().navigateINLeftMenuWithSubmenu("Settings", "Billing & payments");

        verifyCheckBoxesMandatory();
        verifyAddingPaymentMethod();

        soft.assertAll();
    }

    @Step(value = "Verify checkboxes are mandatory")
    private void verifyCheckBoxesMandatory(){
        billingDetailsPage.clickPageNavButton("Payment methods");
        billingDetailsPage.clickAddPaymentButton();

        billingDetailsPage.getAddPaymentMethodWindow().fillInNewCardInfo("AQA", "Test");

        soft.assertFalse(billingDetailsPage.getAddPaymentMethodWindow().isAddPaymentButtonEnabled(),
                "'Add payments method' button is enabled when checkboxes were not selected");

    }

    @Step(value = "Verify adding new payment method")
    private void verifyAddingPaymentMethod(){

        billingDetailsPage.getAddPaymentMethodWindow().selectCheckBox("I Agree to Clickatell's Terms and Conditions")
                                                      .selectCheckBox("I authorise Clickatell to store this card for future transactions")
                                                      .clickAddPaymentButton();
        billingDetailsPage.waitWhileProcessing(5, 5);

        soft.assertEquals(billingDetailsPage.getNotificationAlertText(), "Payment method has been configured successfully",
                "Notification about new payment added is not shown");
        soft.assertTrue(billingDetailsPage.isNewPaymentAdded(),
                "New payment is not shown on 'Billing & payments' page");
        soft.assertTrue(billingDetailsPage.getPaymentMethodDetails().contains("AQA Test"),
                "Cardholder name of added card is not as expected");
    }

}
