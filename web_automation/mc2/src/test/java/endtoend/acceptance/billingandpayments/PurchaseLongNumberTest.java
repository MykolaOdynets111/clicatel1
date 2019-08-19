package endtoend.acceptance.billingandpayments;

import endtoend.basetests.APICreatedAccountTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import portalpages.*;


@Listeners({TestAllureListener.class})
@Test(testName = "Billing & Payments :: Purchase a long number", groups = {"newaccount", "billingspayments", "payment"})
@TmsLink("TECH-5993")
public class PurchaseLongNumberTest extends APICreatedAccountTest  {

    private PortalLaunchpadPage launchpad;
    private PortalMainPage mainPage;
    private CartPage cartPage;
    private String authToken;


    @BeforeClass
    private void setUp(){
        launchpad = new PortalLaunchpadPage();

//        authToken = PortalAuthToken.getAccessTokenForPortalUser(accountName.get(), email.get(), pass.get());

        cartPage = new CartPage();
    }


    @Description("Billing & Payments :: Purchase a long number")
    @Epic("Billing & Payments")
    @Feature("Purchase a long number")
    public void purchaseLongNumber(){

//        mainPage = PortalLoginPage.openPortalLoginPage().login(email.get(), pass.get());

        launchpad.getTargetSection("SMS Add Ons").clickButton("Get a long number", 5);

        launchpad.getLongNumberWindow().selectCountry("United States", "California").selectNumber();
        launchpad.getLongNumberWindow().selectSubscription("Month").clickAddToCardButton();
    }



}
