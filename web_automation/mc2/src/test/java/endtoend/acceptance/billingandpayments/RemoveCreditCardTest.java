package endtoend.acceptance.billingandpayments;

import com.github.javafaker.Faker;
import datamanager.ExistedAccount;
import datamanager.model.PaymentMethod;
import endtoend.basetests.BaseTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import mc2api.ApiHelperPlatform;
import mc2api.auth.PortalAuthToken;
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
@Test(testName = "Billing & Payments :: Remove credit card", groups = {"newaccount", "billingspayments"})
@TmsLink("TECH-5831")
public class RemoveCreditCardTest extends BaseTest {

    private PortalMainPage mainPage;
    private PortalBillingDetailsPage billingDetailsPage;
    private SoftAssert soft;
    private ExistedAccount account;
    private PaymentMethod paymentMethod;
    private String cartHolder;


    @BeforeClass
    private void setUp(){
        account = ExistedAccount.getExistedAccountForBilling();
        billingDetailsPage = new PortalBillingDetailsPage();
        soft = new SoftAssert();

        String authToken = PortalAuthToken.getAccessTokenForPortalUser(account.getAccountName(), account.getEmail(), account.getPass());
        paymentMethod = ApiHelperPlatform.getAllNotDefaultPaymentMethods(authToken).get(0);
        cartHolder = paymentMethod.getCardHolderFirstName() + " " + paymentMethod.getCardHolderLastName();
    }

    @Description("Billing & Payments :: Remove credit card")
    @Epic("Billing & Payments")
    @Feature("Remove credit card")
    public void removeCreditCard(){
        mainPage = PortalLoginPage.openPortalLoginPage().login(account.getEmail(), account.getPass());
        mainPage.getLeftMenu().navigateINLeftMenuWithSubmenu("Settings", "Billing & payments");



        soft.assertAll();
    }


}
