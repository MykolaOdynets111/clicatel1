package endtoend.acceptance.billingandpayments;

import drivermanager.ConfigManager;
import endtoend.basetests.APICreatedAccountTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import mc2api.ApiHelperPlatform;
import mc2api.auth.PortalAuthToken;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.CartPage;
import portalpages.PortalLoginPage;
import portalpages.PortalMainPage;


@Listeners({TestAllureListener.class})
@Test(testName = "Billing & Payments :: Purchase 1 agent seat", groups = {"newaccount", "touch"})
@TmsLink("TECH-6539")
public class Purchase1AgentSeatTest extends APICreatedAccountTest  {

    private PortalMainPage mainPage;
    private SoftAssert soft;
    private CartPage cartPage;

    @BeforeClass
    private void cleanCart(){
        String authToken = PortalAuthToken.getAccessTokenForPortalUser(accountName.get(),email.get(), pass.get());
        ApiHelperPlatform.deleteAllFromCart(authToken);
    }


    @Description("Billing & Payments :: Purchase 1 agent seat")
    @Epic("Billing & Payments")
    @Feature("Purchase 1 agent seat")
    public void purchase1AgentTest(){
        mainPage = PortalLoginPage.openPortalLoginPage().login(email.get(), pass.get());

        verifyDefaultUpgradeValues();
        addAgentToCart();
        verifyPurchaseAgent();
        verifyPurchasedAgentAdded();
    }

    @Step(value = "Verify default Upgrade values")
    private void verifyDefaultUpgradeValues() {
        soft = new SoftAssert();
        mainPage.getPageHeader().clickUpgradeButton();

        soft.assertTrue(mainPage.getUpgradeYourPlanWindow().isAddAgentSeatsButtonShown(),
                "Add agent seats button is not shown");
        soft.assertEquals(mainPage.getUpgradeYourPlanWindow().getAgentSeatsInfo(), "1 x Agent seat(s)",
                "Incorrect add agent seats default label");
        soft.assertAll();
    }

    @Step(value = "Add 1 agent to the cart")
    private void addAgentToCart() {
        soft = new SoftAssert();
        if (ConfigManager.getEnv().equals("testing") |
                ConfigManager.getEnv().equals("qa"))  mainPage.getUpgradeYourPlanWindow().selectMonthly();
        mainPage.getUpgradeYourPlanWindow().clickAddToCardButton();

        soft.assertEquals(mainPage.getNotificationAlertText(), "Added to cart",
                "Notification about adding to cart is not shown");

        cartPage = mainPage.getPageHeader().openCart();
        soft.assertTrue(new CartPage().getCartTable().isItemAddedToTheCart("1x Touch Agent Seats", 5),
                "Item was not added to the cart");
        soft.assertAll();
    }

    @Step(value = "Verify 1 agent purchasing")
    private void verifyPurchaseAgent() {
        cartPage.clickCheckoutButton();
        mainPage.checkoutAndBuy(cartPage);

        Assert.assertEquals(cartPage.getConfirmPaymentDetailsWindow().getSuccessMessageMessage(),
                "Payment Successful", "Payment successful message was not shown");
    }

    @Step(value = "Verify 1 agent added")
    private void verifyPurchasedAgentAdded() {

        cartPage.getConfirmPaymentDetailsWindow().closeWindow();

        Assert.assertTrue(cartPage.getPageHeader().isCorrectAgentSeatsShown("2x AGENT SEATS", 2),
                "Incorrect number of agents added.");
    }

}
