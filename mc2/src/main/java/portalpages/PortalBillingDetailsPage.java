package portalpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.AddPaymentMethodWindow;
import portaluielem.billingdetails.BillingContactsDetails;
import portaluielem.TopUpBalanceWindow;
import portaluielem.billingdetails.PaymentMethod;

import java.util.List;


public class PortalBillingDetailsPage extends PortalAbstractPage {

    @FindBy(css = "form[name=billingDetailsForm]")
    private WebElement billingDetailsForm;

    @FindBy(css = "a[ng-click='addPaymentMethod()']")
    private WebElement addPaymentMethodButton;

    @FindBy(css = "div.payment-method")
    private List<WebElement> addedPayments;

    @FindBy(xpath = "//header//button[@ng-click='topUpBalance()']")
    private WebElement topUpBalanceButton;

    @FindBy(xpath="//input[@name='orderNumber']")
    private WebElement orderNub;

    @FindBy(xpath = "//input[@name='price']")
    private WebElement OrderPrice;

    @FindBy()
    private WebElement SendButton;

    private BillingContactsDetails billingContactsDetails;

    private AddPaymentMethodWindow addPaymentMethodWindow;

    private TopUpBalanceWindow topUpBalanceWindow;


    // == Constructors == //

    public PortalBillingDetailsPage() {
        super();
    }
    public PortalBillingDetailsPage(String agent) {
        super(agent);
    }
    public PortalBillingDetailsPage(WebDriver driver) {
        super(driver);
    }

    public AddPaymentMethodWindow getAddPaymentMethodWindow(){
        addPaymentMethodWindow.setCurrentDriver(this.getCurrentDriver());
        return addPaymentMethodWindow;
    }

    public BillingContactsDetails getBillingContactsDetails() {
        billingContactsDetails.setCurrentDriver(this.getCurrentDriver());
        return billingContactsDetails;
    }

    public TopUpBalanceWindow getTopUpBalanceWindow(){
        topUpBalanceWindow.setCurrentDriver(this.getCurrentDriver());
        return topUpBalanceWindow;
    }

    public boolean isPageOpened(int wait){
        return isElementShown(this.getCurrentDriver(), billingDetailsForm, wait);
    }

    public boolean isAddPaymentMethodButtonShown(int wait){
        return isElementShown(this.getCurrentDriver(), addPaymentMethodButton, wait);
    }

    public boolean isAddPaymentMethodWindowShown(int wait){
        return isElementShown(this.getCurrentDriver(), getAddPaymentMethodWindow().getWrappedElement(), wait);
    }

    @Step(value = "Click 'Add Payment Method' button")
    public void clickAddPaymentButton(){
        clickElem(this.getCurrentDriver(), addPaymentMethodButton, 3, "Add Payment Method");
    }

    @Step(value="Click 'order number' field")
    public PortalBillingDetailsPage setOrderNumberField(String order)
    {
        clickElem(this.getCurrentDriver(), orderNub, 10, "order");
        orderNub.clear();
        orderNub.sendKeys(order);
        return this;
    }
    @Step(value="Click 'order number' field")
    public PortalBillingDetailsPage setPriceForOrder(String price)
    {
        clickElem(this.getCurrentDriver(), OrderPrice, 10, "price");
        OrderPrice.clear();
        OrderPrice.sendKeys(price);
        return this;
    }
    @Step(value = "Click Create button")
    public void clickSendButton(){
        clickElem(this.getCurrentDriver(), SendButton, 10, "Send button");
    }

    public PaymentMethod getTargetPaymentMethod(String cartHolder){
        try{
            return addedPayments.stream().map(e -> new PaymentMethod(e).setCurrentDriver(this.getCurrentDriver()))
                    .filter(e -> e.getCartHolder().equals(cartHolder))
                    .findFirst().orElseGet(null);
        }catch (NullPointerException e){
            return null;
        }
    }

    @Step(value = "Verify the payment present on the Billing & payments > Payments page")
    public boolean isPaymentShown(String cartHolder, int wait){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
        PaymentMethod payment = getTargetPaymentMethod(cartHolder);
        for(int i =0; i<wait; i++){
            if(payment!=null) return true;
            else{
                waitFor(5000);
                this.getCurrentDriver().navigate().refresh();
                waitWhileProcessing(this.getCurrentDriver(), 2, 5);
                areElementsShown(this.getCurrentDriver(), addedPayments, 5);
                payment = getTargetPaymentMethod(cartHolder);
            }
        }
        return false;
    }

   @Step(value = "Click 'Manage' button")
   public void clickManageButton(String cartHolder){
       getTargetPaymentMethod(cartHolder).clickManage();
   }


    public void clickTopUPBalance(){
        clickElem(this.getCurrentDriver(), topUpBalanceButton, 5,  "'Top up balance' button" );
    }


}
