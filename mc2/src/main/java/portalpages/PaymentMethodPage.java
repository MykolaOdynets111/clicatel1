package portalpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentMethodPage extends PortalAbstractPage {

    @FindBy(xpath = "//button[text()='Accept']")
    private WebElement acceptRemovePaymentButton;

    @FindBy(xpath = "//button[text()='Decline']")
    private WebElement declineRemovePaymentButton;

    @FindBy(css = "input#cardNumber")
    private WebElement cardNumber;

    @FindBy(css = "input#cardCvv")
    private WebElement cardCvv;

    @FindBy(css = "//button[@ng-click='removeCard()']")
    private WebElement removeButton;

    private String removePaymentButton =  "//button[@ng-click='removeCard()']";

    // == Constructors == //

    public PaymentMethodPage() {
        super();
    }
    public PaymentMethodPage(String agent) {
        super(agent);
    }
    public PaymentMethodPage(WebDriver driver) {
        super(driver);
    }

    @Step(value = "Delete payment")
    public void deletePaymentMethod(){
        clickRemoveButton();
        if(isElementShown(this.getCurrentDriver(), acceptRemovePaymentButton, 3))
            acceptRemovePaymentButton.click();
    }

    @Step(value = "Get card number")
    public String getCardNumber(){
        return getAttributeFromElem(this.getCurrentDriver(), cardNumber, 2, "Card number", "value");
    }

    @Step(value = "Get card cvv")
    public String getCardCVV(){
        return getAttributeFromElem(this.getCurrentDriver(), cardCvv, 2, "Card CVV", "value");
    }

    @Step(value = "Click 'Remove' button")
    public PaymentMethodPage clickRemoveButton(){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(), removePaymentButton, 12);
        executeAngularClick(this.getCurrentDriver(), findElemByXPATH(this.getCurrentDriver(), removePaymentButton));
        return this;
    }

    @Step(value = "Click 'Decline' button")
    public void clickDeclineButton(){
        clickElem(this.getCurrentDriver(), declineRemovePaymentButton, 4, "'Decline' button");
    }

    @Step(value = "Click 'Accept' button")
    public void clickAcceptButton(){
        clickElem(this.getCurrentDriver(), acceptRemovePaymentButton, 4, "'Accept' button");
        waitWhileProcessing(2,3);
    }
}
