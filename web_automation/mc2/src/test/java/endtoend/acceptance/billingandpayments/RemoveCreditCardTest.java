package endtoend.acceptance.billingandpayments;

import datamanager.ExistedAccount;
import datamanager.model.PaymentMethod;
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


@Listeners({TestAllureListener.class})
@Test(testName = "Billing & Payments :: Remove credit card", groups = {"newaccount", "billingspayments"})
@TmsLink("TECH-5831")
public class RemoveCreditCardTest extends BaseTest {

    private PortalMainPage mainPage;
    private PortalBillingDetailsPage billingDetailsPage;
    private SoftAssert soft;
    private ExistedAccount account;
    private PaymentMethod paymentMethod;
    private String cardHolder;
    private PaymentMethodPage paymentMethodPage;


    @BeforeClass
    private void setUp(){
        account = ExistedAccount.getExistedAccountForBilling();
        billingDetailsPage = new PortalBillingDetailsPage();
        soft = new SoftAssert();

        String authToken = PortalAuthToken.getAccessTokenForPortalUser(account.getAccountName(), account.getEmail(), account.getPass());
        paymentMethod = ApiHelperPlatform.getAllNotDefaultPaymentMethods(authToken).get(0);
        cardHolder = paymentMethod.getCardHolderFirstName() + " " + paymentMethod.getCardHolderLastName();
    }

    @Description("Billing & Payments :: Remove credit card")
    @Epic("Billing & Payments")
    @Feature("Remove credit card")
    public void removeCreditCard(){
        mainPage = PortalLoginPage.openPortalLoginPage().login(account.getEmail(), account.getPass());

        verifySensitiveData();
        verifyDeclineRemoving();
        verifyAcceptRemoving();

        soft.assertAll();
    }

    @Step(value = "Verify masking sensitive data")
    private void verifySensitiveData(){
        mainPage.getLeftMenu().navigateINLeftMenuWithSubmenu("Settings", "Billing & payments");
        billingDetailsPage.clickPageNavButton("Payment methods");
        billingDetailsPage.clickManageButton(cardHolder);

        paymentMethodPage = new PaymentMethodPage();

        soft.assertEquals(paymentMethodPage.getCardNumber(), "**** **** **** 1111",
                "Card CVV is not masked\n");
        soft.assertEquals(paymentMethodPage.getCardCVV(), "***",
                "Card CVV is not masked\n");
    }

    @Step(value = "Verify decline removing")
    private void verifyDeclineRemoving(){
        paymentMethodPage.clickRemoveButton()
                            .clickDeclineButton();
        Assert.assertEquals(paymentMethodPage.getCardNumber(), "**** **** **** 1111",
                "Card CVV is not masked\n");
    }

    @Step(value = "Verify accept removing")
    private void verifyAcceptRemoving(){
        paymentMethodPage.clickRemoveButton()
                            .clickAcceptButton();
        soft.assertEquals(paymentMethodPage.getNotificationAlertText(), "Your payment method has been successfully removed",
                "Notification alert about removing card is not as expected\n");
        soft.assertFalse(billingDetailsPage.isPaymentShown(cardHolder, 2), "Card is not removed");
    }
}
