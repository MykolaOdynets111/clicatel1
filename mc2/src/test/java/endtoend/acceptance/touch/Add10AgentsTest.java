package endtoend.acceptance.touch;

import endtoend.basetests.APICreatedAccountTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.*;


@Listeners({TestAllureListener.class})
@Test(testName = "Touch :: Add 10 agent seats to cart", groups = {"newaccount", "touch"})
@TmsLink("TECH-6540")
public class Add10AgentsTest extends APICreatedAccountTest  {

    private PortalMainPage mainPage;
    private SoftAssert soft;


    @BeforeClass
    private void cleanCart(){
//        String authToken = PortalAuthToken.getAccessTokenForPortalUser(accountName.get(),email.get(), pass.get());
//        ApiHelperPlatform.deleteAllFromCart(authToken);
    }


    @Description("Touch :: Add 10 agent seats to cart")
    @Epic("Touch")
    @Feature("Add 10 agent seats to cart")
    public void add10AgentsTest(){
        mainPage = PortalLoginPage.openPortalLoginPage().login(email.get(), pass.get());

        verifyDefaultUpgradeValues();
        clickPlusButtonNineTimes();
        verifyAddingToCart();
    }

    @Step(value = "Verify default Upgrade values")
    private void verifyDefaultUpgradeValues() {
        soft = new SoftAssert();
        mainPage.getPageHeader().clickUpgradeButton();

        soft.assertTrue(mainPage.getUpgradeYourPlanWindow().isAddAgentSeatsButtonShown(),
                "Add agent seats button is not shown");
        soft.assertEquals(mainPage.getUpgradeYourPlanWindow().getAgentSeatsInfo(), "1 x Agent seat(s)",
                "Incorrect add agent seats default label");
        soft.assertTrue(mainPage.getUpgradeYourPlanWindow().verifyMonthlySelected(),
                "'Monthly' radio is not selected be default");
        soft.assertAll();
    }

    @Step(value = "Click '+' 9 times")
    private void clickPlusButtonNineTimes() {
        mainPage.getUpgradeYourPlanWindow().selectAgentSeats(10);

        Assert.assertEquals(mainPage.getUpgradeYourPlanWindow().getAgentSeatsInfo(), "10 x Agent seat(s)",
                "Incorrect add agent seats default label");
    }

    @Step(value = "Verify adding to cart")
    private void verifyAddingToCart() {
        soft = new SoftAssert();
        mainPage.getUpgradeYourPlanWindow().selectMonthly();
        mainPage.getUpgradeYourPlanWindow().clickAddToCardButton();

        soft.assertEquals(mainPage.getNotificationAlertText(), "Added to cart",
                "Notification about adding to cart is not shown");

        mainPage.getPageHeader().openCart();
        soft.assertTrue(new CartPage().getCartTable().isItemAddedToTheCart("1x Touch Agent Seats 10-pack", 5),
                "Item was not added to the cart");
        soft.assertAll();
    }


}
