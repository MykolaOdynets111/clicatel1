package endtoend.acceptance.billingandpayments;

import datamanager.ExistedAccount;
import datamanager.TopUpBalanceLimits;
import datamanager.mc2jackson.MC2AccountBalance;
import endtoend.basetests.BaseTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import mc2api.ApiHelperPlatform;
import mc2api.auth.PortalAuthToken;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;


@Listeners({TestAllureListener.class})
@Test(testName = "Billing & Payments :: Add top up", groups = {"newaccount", "billingspayments", "payment"})
@TmsLink("TECH-5991")
public class AddTopUpTest extends BaseTest {

    private PortalMainPage mainPage;
    private PortalBillingDetailsPage billingDetailsPage;
    private String accCurrency;
    private String boundaryErrorMessage;
    private double minValue;
    private CartPage cartPage;
    private String authToken;
    private MC2AccountBalance preTestBalance;
    private int topUpAmount;
    private ExistedAccount account;


    @BeforeClass
    private void setUp(){
        account = ExistedAccount.getExistedAccount();

        billingDetailsPage = new PortalBillingDetailsPage();
        authToken = PortalAuthToken.getAccessTokenForPortalUser(account.getAccountName(), account.getEmail(), account.getPass());

        preTestBalance = ApiHelperPlatform.getAccountBalance(authToken);
        accCurrency = preTestBalance.getCurrency().toUpperCase();
        boundaryErrorMessage = "The minimum purchase amount is " + TopUpBalanceLimits.getMinValueByCurrency(accCurrency)
                + " " + accCurrency;
        cartPage = new CartPage();
    }


    @Description("Billing & Payments :: Add top up")
    @Epic("Billing & Payments")
    @Feature("Add top up")
    public void addTopUp(){
        mainPage = PortalLoginPage.openPortalLoginPage().login(account.getEmail(), account.getPass());


        addLessThenMinimumAmount();
        addValidAmountToTheCart();
        verifyTopUpPayment();
        verifyBalanceUpdated();
//        verifyTransactionOnBillingAndPayments(); -- commented out because discussion needed


    }

    @Step(value = "Verify adding less than a minimum threshold top up amount")
    private void addLessThenMinimumAmount(){
        SoftAssert soft = new SoftAssert();
        mainPage.getPageHeader().clickTopUpBalanceButton();
        minValue = Double.valueOf(mainPage.getTopUpBalanceWindow().getMinLimit().trim());
        topUpAmount = (int) minValue;
        double addingSum = minValue - 0.01;

        mainPage.getTopUpBalanceWindow().enterNewAmount(addingSum);
        mainPage.getTopUpBalanceWindow().clickAddToCardButton();

        soft.assertTrue(mainPage.getTopUpBalanceWindow().isShown(),
                "Top window is unexpectedly closed after adding not valid Top Up sum");
        soft.assertTrue(mainPage.getTopUpBalanceWindow().isInputHighlighted(),
                "Input field is not highlighted after providing invalid top up amount");
        soft.assertEquals(mainPage.getTopUpBalanceWindow().getErrorWhileAddingPopup(), boundaryErrorMessage,
                "Message about minimum allowed purchase is not shown");
        soft.assertAll();
    }

    @Step(value = "Verify adding valid top up amount to the cart")
    private void addValidAmountToTheCart(){
        ApiHelperPlatform.deleteAllFromCart(authToken);
        mainPage.getTopUpBalanceWindow().enterNewAmount(minValue);
        mainPage.getTopUpBalanceWindow().clickAddToCardButton();
        mainPage.waitWhileProcessing(3,12);

        Assert.assertTrue(cartPage.isCartPageOpened(),
                "Cart page is not opened after adding top up to the cart");
        Assert.assertTrue(cartPage.getCartTable().isItemAddedToTheCart("Top Up", accCurrency + " " + (int) minValue, 4));
    }

    @Step(value = "Verify top up payment successful")
    private void verifyTopUpPayment(){

        cartPage.clickCheckoutButton();
        cartPage.getConfirmPaymentDetailsWindow().clickSelectPaymentField()
                .selectPaymentMethod("VISA")
                .clickNexButton()
                .acceptTerms()
                .clickNexButton()
                .waitFotPaymentSummaryScreenToLoad()
                .acceptTerms()
                .clickPayNowButton();
        cartPage.waitWhileProcessing(14, 20);

        Assert.assertEquals(cartPage.getConfirmPaymentDetailsWindow().getSuccessMessageMessage(),
                "Payment Successful", "Payment successful message was not shown");
    }

    @Step(value = "Verify balance is updated")
    private void verifyBalanceUpdated(){
        cartPage.getConfirmPaymentDetailsWindow().closeWindow();
        Assert.assertTrue(cartPage.getPageHeader()
                        .isTopUpUpdatedOnBackend(preTestBalance.getBalance()+minValue, 5, authToken),
                "Balance was not updated on backend\n" +
                        "Expected: " + (preTestBalance.getBalance() + minValue) + "\n" +
                        "Actual: " + ApiHelperPlatform.getAccountBalance(authToken).getBalance()
        );

        Assert.assertTrue((boolean) cartPage.getPageHeader().isTopUpUpdated(authToken, 2).get("result"),
                "Balance was not updated \n");
    }

    @Step(value = "Verify transaction on Billing & payments > Transactions page")
    private void verifyTransactionOnBillingAndPayments(){
        PortalBillingTransactionsPage transactionsPage = new PortalBillingTransactionsPage();
        mainPage.getLeftMenu().navigateINLeftMenuWithSubmenu("Settings", "Billing & payments");
        billingDetailsPage.clickPageNavButton("Transactions");
        billingDetailsPage.waitWhileProcessing(2, 5);

        Assert.assertTrue(transactionsPage.isTransactionRecordsAppear(25),
                "Transactions page is empty");
        Assert.assertTrue(new PortalBillingTransactionsPage().getTransactionsTable()
                        .isTransactionPresent("TopUp", getBalance() , getCurrentDate(), 10),
                "Top up is not shown in transactions\n");
    }

    private String getCurrentDate(){
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        LocalDateTime now = LocalDateTime.now(zoneId);
        return now.format(DateTimeFormatter.ofPattern("M/dd/yy"));
    }

    private String getBalance(){
        return accCurrency + " " + topUpAmount;
    }

}
