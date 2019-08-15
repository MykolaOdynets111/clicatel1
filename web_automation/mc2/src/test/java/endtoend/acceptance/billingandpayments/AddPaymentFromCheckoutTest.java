package endtoend.acceptance.billingandpayments;

import com.github.javafaker.Faker;
import endtoend.basetests.APICreatedAccountTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.CartPage;
import portalpages.PortalBillingDetailsPage;
import portalpages.PortalLoginPage;
import portalpages.PortalMainPage;
import portaluielem.ConfirmPaymentDetailsWindow;


@Listeners({TestAllureListener.class})
@Test(testName = "Billing & Payments :: Add credit card during checkout", groups = {"newaccount", "billingspayments"})
@TmsLink("TECH-5829")
public class AddPaymentFromCheckoutTest extends APICreatedAccountTest  {

    private PortalMainPage mainPage;
    private PortalBillingDetailsPage billingDetailsPage;
    private SoftAssert soft;
    private CartPage cartPage;
    private ConfirmPaymentDetailsWindow confirmPaymentDetails;
    private String cardHolderLastName;

    @BeforeClass
    private void setUp(){
        billingDetailsPage = new PortalBillingDetailsPage();
        soft = new SoftAssert();
        cartPage = new CartPage();
        cardHolderLastName = new Faker().name().lastName();
    }

    @Issue("TECH-13957")
    @Description("Billing & Payments :: Add credit card during checkout")
    @Epic("Billing & Payments")
    @Feature("Add credit card during checkout")
    public void addingPaymentMethodDuringCheckout(){

        mainPage = PortalLoginPage.openPortalLoginPage().login(email.get(), pass.get());

        addTopUpToTheCart();
        openPaymentTab();
        verifyAddingNewCardInfo();
        verifySelectingTermCheckbox();
        verifyAuthoriseStoreCardCheckbox();
        verifyClickingNextButton();
        verifyClosingDetailsWindow();

        soft.assertAll();
    }

    @Step(value = "Add top up to the cart")
    private void addTopUpToTheCart(){
        mainPage.getPageHeader().clickTopUpBalanceButton();
        String minValue = mainPage.getTopUpBalanceWindow().getMinLimit().trim();
        int addingSum = Integer.valueOf(minValue) + 1;

        mainPage.getTopUpBalanceWindow().enterNewAmount(addingSum);
        mainPage.getTopUpBalanceWindow().clickAddToCardButton();
        mainPage.waitWhileProcessing(2, 10);
    }

    @Step(value = "Open Payments tab")
    private void openPaymentTab(){
        cartPage.clickCheckoutButton().waitWhileProcessing(2, 3);
        confirmPaymentDetails = cartPage.getConfirmPaymentDetailsWindow();
    }

    @Step(value = "Verify adding new card details")
    private void verifyAddingNewCardInfo(){
        confirmPaymentDetails.clickSelectPaymentField();
        confirmPaymentDetails.selectPaymentMethod("Add Credit/Debit Card");
        mainPage.getAddPaymentMethodWindow().fillInNewCardInfo("AQA", cardHolderLastName);

        soft.assertFalse(mainPage.getAddPaymentMethodWindow().isNextButtonEnabled("disabled"),
                "Next button was enabled before accepting Terms checkbox");
    }

    @Step(value = "Verify selecting Terms checkbox")
    private void verifySelectingTermCheckbox(){

        mainPage.getAddPaymentMethodWindow().selectCheckBox("I Agree to Clickatell's Terms and Conditions");

        soft.assertTrue(mainPage.getAddPaymentMethodWindow().isNextButtonEnabled("enabled"),
                "Next button was not enabled after accepting Terms checkbox");
        soft.assertTrue(mainPage.getAddPaymentMethodWindow()
                        .isCheckBoxDisabled("Would you like to enable this card to be used for automatic payments in future?", "disabled"),
                "'Would you like to enable this card to be used for automatic payments in future?' " +
                        "section was enabled before selecting Authorize checkbox");
    }

    @Step(value = "Verify selecting authorise to store card checkbox")
    private void verifyAuthoriseStoreCardCheckbox(){

        mainPage.getAddPaymentMethodWindow().selectCheckBox("I authorise Clickatell to store this card for future transactions");

        soft.assertFalse(mainPage.getAddPaymentMethodWindow()
                        .isCheckBoxDisabled("Would you like to enable this card to be used for automatic payments in future?", "enabled"),
                "'Would you like to enable this card to be used for automatic payments in future?' " +
                        "section was not enabled after selecting Authorize checkbox");
    }

    @Step(value = "Verify clicking Next button")
    private void verifyClickingNextButton(){

        mainPage.getAddPaymentMethodWindow().clickNextButton();
        mainPage.waitWhileProcessing(4, 5);

        soft.assertEquals(billingDetailsPage.getNotificationAlertText(), "Payment method has been configured successfully",
                "Notification about new payment added is not shown");
        soft.assertTrue(confirmPaymentDetails.isPaymentReviewTabOpened(), "Payment Review tab is not opened");
    }

    @Step(value = "Verify closing Confirm Details window")
    private void verifyClosingDetailsWindow(){

        confirmPaymentDetails.closeWindow();

        soft.assertEquals(billingDetailsPage.getOpenedTabTitle(), "Payment methods",
                "Payment method  page is not opened after closing Confirm Payment details window");
        soft.assertTrue(billingDetailsPage.getPaymentMethodDetails().contains("AQA " + cardHolderLastName),
                "Cardholder name of added card is not as expected");
    }
}
