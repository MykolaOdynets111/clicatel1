package portaluielem;

import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

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

    @FindBy(css = "div[cl-checkbox]")
    private List<WebElement> checkboxes;

    private String cardNumber = "input#cardNumber";

    private String cardCvv =  "input#cardCvv";

    private String firstName = "input#firstName";

    private String lastName =  "input#lastName";

    private String paymentAddedAlert = "//div[@ng-bind-html='alert'][text()='Payment method has been configured successfully']";

    @Step(value = "Add new payment card")
    public AddPaymentMethodWindow addTestCardAsANewPayment(String cartHolderName, String cartHolderLastName){
        fillInNewCardInfo(cartHolderName, cartHolderLastName);
        checkAllCheckboxesForAddingNewPayment();
        waitForAngularToBeReady(this.getCurrentDriver());
        waitFor(2000);
//        getNgDriver(MC2DriverFactory.getAgentDriverInstance()).waitForAngularRequestsToFinish();
        executeAngularClick(this.getCurrentDriver(), primaryBindingButton);
        return this;
    }

    @Step(value = "Check all checkboxes")
    public AddPaymentMethodWindow checkAllCheckboxesForAddingNewPayment(){
        selectCheckBox("I Agree to Clickatell's Terms and Conditions");
        selectCheckBox("I authorise Clickatell to store this card for future transactions");
        return this;
    }

    @Step(value = "Select '{checkBox}' checkbox")
    public AddPaymentMethodWindow selectCheckBox(String checkBox){
        checkboxes.stream().filter(e -> e.getText().trim().equals(checkBox))
                           .findFirst().orElseThrow(() -> new AssertionError("Cannot select '" + checkBox + "' checkbox"))
                           .click();
        return this;
    }

    @Step(value = "Verify '{checkBox}' checkbox {expectedStatus}")
    public boolean isCheckBoxDisabled(String checkBox, String expectedStatus){
        String disabled = checkboxes.stream().filter(e -> e.getText().trim().equals(checkBox))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Cannot get attribute from '" + checkBox + "' checkbox"))
                .getAttribute("disabled");
        if(disabled == null) return false;
        if(disabled.equals("true")) return true;
        else{
            Assert.fail("Unexpected 'disabled' attribute value: \n" + disabled);
            return false;
        }
    }

    @Step(value = "Fill in new card info")
    public AddPaymentMethodWindow fillInNewCardInfo(String cardHolderName, String cardHolerLastName){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        selectPaymentBox.click();
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForElementToBeVisible(this.getCurrentDriver(), selectOptionsInDropdown.get(0), 5);
        cardOption.click();
        findElemByCSS(this.getCurrentDriver(), cardNumber).sendKeys("4111111111111111");
        expirationMonth.click();
        waitForElementToBeClickable(this.getCurrentDriver(), thirdMons, 2);
        thirdMons.click();
        expirationYear.click();
        waitForElementToBeClickable(this.getCurrentDriver(), thirdMons, 2);
        thirdYear.click();
        findElemByCSS(this.getCurrentDriver(), cardCvv).sendKeys("112");
        findElemByCSS(this.getCurrentDriver(), firstName).sendKeys(cardHolderName);
        findElemByCSS(this.getCurrentDriver(), lastName).sendKeys(cardHolerLastName);
        return this;
    }

    public void clickCheckBox(int checkboxOrder){
        checkboxes.get(checkboxOrder-1).click();
    }

    @Step(value = "Click 'Add payments method' button")
    public void clickAddPaymentButton(){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
        waitForElementToBeClickable(this.getCurrentDriver(), primaryBindingButton, 15);
//        waitFor(2000);
        executeAngularClick(this.getCurrentDriver(), primaryBindingButton);
    }

    @Step(value = "Verify if 'Add payments method' button enabled")
    public boolean isAddPaymentButtonEnabled(){
        executeJSHover(primaryBindingButton, this.getCurrentDriver());
        return primaryBindingButton.isEnabled();
    }

    @Step(value = "Verify if 'Next' button {expectedStatus}")
    public boolean isNextButtonEnabled(String expectedStatus){
        executeJSHover(nextButton, this.getCurrentDriver());
        return nextButton.isEnabled();
    }

    public void waitForAddingNewPaymentConfirmationPopup(){
        try {
            waitForElementToBeVisibleByXpath(this.getCurrentDriver(), paymentAddedAlert, 15);
            waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), paymentAddedAlert, 5);
        }catch(TimeoutException e){
            // nothing to do 'cause it were stabilizing waits before continuing
        }
    }

}
