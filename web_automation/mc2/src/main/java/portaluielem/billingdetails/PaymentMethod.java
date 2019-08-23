package portaluielem.billingdetails;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentMethod extends AbstractWidget {

    @FindBy(css = "div.dashboard-step-content")
    private WebElement paymentMethodDetails;

    @FindBy(css = "button")
    private WebElement manageButton;

    @FindBy(xpath = ".//p[child::span[@ng-bind='paymentMethod.cardHolderFirstName']]")
    private WebElement cartHolder;


    public PaymentMethod(WebElement element) {
        super(element);
    }

    public PaymentMethod setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getCartHolder(){
        return getTextFromElem(this.getCurrentDriver(), cartHolder, 3, "Cart Holder");
    }

    public void clickManage(){
        clickElem(this.getCurrentDriver(), manageButton, 3, "'Manage' button");
    }
}