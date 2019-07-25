package portaluielem;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div.cl-wizzard.create-integration-container")
public class AddPaymentMethodWindow extends BasePortalWindow {

    @FindBy(css = "span[aria-label='Select box activate']")
    private WebElement selectPaymentBox;

    @FindBy(xpath =  ".//span[@class='ui-select-choices-row-inner']/span[contains(text(), 'Card')]")
    private WebElement cardOption;

    @FindBy(css = "div#expirationMonth")
    private WebElement expirationMonth;

    @FindBy(xpath = "(//ul[@class = 'ui-select-choices ui-select-choices-content ui-select-dropdown dropdown-menu ng-scope']//div[contains(@class, 'ui-select-choices-row ng-scope')])[3]")
    private WebElement thirdMons;

    @FindBy(css = "div[cl-options='expirationYears']")
    private WebElement expirationYear;

    @FindBy(xpath =  "(//ul[@class = 'ui-select-choices ui-select-choices-content ui-select-dropdown dropdown-menu ng-scope']//div[contains(@class, 'ui-select-choices-row ng-scope')])[3]")
    private WebElement thirdYear;

    @FindBy(css = "input[type='checkbox']+span")
    private List<WebElement> checkboxes;

    private String cardNumber = "input#cardNumber";

    private String cardCvv =  "input#cardCvv";

    private String firstName = "input#firstName";

    private String lastName =  "input#lastName";

    private String paymentAddedAlert = "//div[@ng-bind-html='alert'][text()='Payment method has been configured successfully']";

    public AddPaymentMethodWindow addTestCardAsANewPayment(){
        selectPaymentBox.click();
        waitForElementsToBeVisible(this.getCurrentDriver(), selectOptionsInDropdown, 5);
        cardOption.click();
        findElemByCSS(this.getCurrentDriver(), cardNumber).sendKeys("4111111111111111");
        expirationMonth.click();
        waitForElementToBeClickable(this.getCurrentDriver(), thirdMons, 2);
        thirdMons.click();
        expirationYear.click();
        waitForElementToBeClickable(this.getCurrentDriver(), thirdYear, 2);
        thirdYear.click();
        findElemByCSS(this.getCurrentDriver(), cardCvv).sendKeys("112");
        findElemByCSS(this.getCurrentDriver(), firstName).sendKeys("AQA");
        findElemByCSS(this.getCurrentDriver(), lastName).sendKeys("Test");
        checkAllCheckboxesForAddingNewPayment();
        waitForAngularToBeReady(this.getCurrentDriver());
        waitFor(2000);
//        getNgDriver(MC2DriverFactory.getAgentDriverInstance()).waitForAngularRequestsToFinish();
        executeAngularClick(this.getCurrentDriver(), primaryBindingButton);
        return this;
    }

    public void checkAllCheckboxesForAddingNewPayment(){
        checkboxes.forEach(WebElement::click);

    }

    public AddPaymentMethodWindow fillInNewCardInfo(){
        selectPaymentBox.click();
        waitForElementToBeVisible(this.getCurrentDriver(), selectOptionsInDropdown.get(0), 5);
        cardOption.click();
        findElemByCSS(this.getCurrentDriver(), cardNumber).sendKeys("4111111111111111");
        expirationMonth.click();
        thirdMons.click();
        expirationYear.click();
        thirdYear.click();
        findElemByCSS(this.getCurrentDriver(), cardCvv).sendKeys("112");
        findElemByCSS(this.getCurrentDriver(), firstName).sendKeys("AQA");
        findElemByCSS(this.getCurrentDriver(), lastName).sendKeys("Test");
        return this;
    }

    public void clickCheckBox(int checkboxOrder){
        checkboxes.get(checkboxOrder-1).click();
    }

    public void clickNextButton(){
        executeAngularClick(this.getCurrentDriver(), nextButton);
    }

    public void clickAddPaymentButton(){
        waitForElementToBeClickable(this.getCurrentDriver(), primaryBindingButton, 15);
        waitFor(2000);
        executeAngularClick(this.getCurrentDriver(), primaryBindingButton);
    }


    public boolean isAddPaymentButtonEnabled(){
        executeJSHover(primaryBindingButton, this.getCurrentDriver());
        return primaryBindingButton.isEnabled();
    }

    public void waitForAddingNewPaymentConfirmationPopup(){
        try {
            waitForElementToBeVisibleByXpath(this.getCurrentDriver(), paymentAddedAlert, 15);
            waitForElementToBeInVisibleByXpath(this.getCurrentDriver(), paymentAddedAlert, 5);
        }catch(TimeoutException e){
            // nothing to do 'cause it were stabilizing waits before continuing
        }
    }

}
