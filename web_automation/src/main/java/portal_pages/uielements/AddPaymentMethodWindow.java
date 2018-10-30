package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import driverManager.DriverFactory;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portal_pages.CartPage;

import java.util.List;


@FindBy(css = "div.cl-wizzard.create-integration-container")
public class AddPaymentMethodWindow extends AbstractUIElement {

    @FindBy(css = "span[aria-label='Select box activate']")
    private WebElement selectPaymentBox;

    @FindBy(css = "li.ui-select-choices-group")
    private WebElement choisesGroup;

    @FindBy(xpath =  ".//span[@class='ui-select-choices-row-inner']/span[contains(text(), 'Card')]")
    private WebElement cardOption;

    @FindBy(css = "div#expirationMonth")
    private WebElement expirationMonth;

    @FindBy(xpath = "(//li[@id='ui-select-choices-5']/div)[5]")
    private WebElement thirdMons;

    @FindBy(css = "div[cl-options='expirationYears']")
    private WebElement expirationYear;

    @FindBy(xpath =  "(//li[@id='ui-select-choices-6']/div)[5]")
    private WebElement thirdYear;

    @FindBy(css = "input[type='checkbox']+span")
    private List<WebElement> checkboxes;

    @FindBy(css = "button.button.button-primary.ng-scope")
    private WebElement nextButton;

    private String cardNumber = "input#cardNumber";

    private String cardCvv =  "input#cardCvv";

    private String firstName = "input#firstName";

    private String lastName =  "input#lastName";

    private String paymentAddedAlert = "//div[@ng-bind-html='alert'][text()='Payment method has been configured successfully']";

    public AddPaymentMethodWindow addTestCardAsANewPayment(){
        selectPaymentBox.click();
        waitForElementToBeVisibleAgent(choisesGroup, 5);
        cardOption.click();
        findElemByCSSAgent(cardNumber).sendKeys("4111111111111111");
        expirationMonth.click();
        thirdMons.click();
        expirationYear.click();
        thirdYear.click();
        findElemByCSSAgent(cardCvv).sendKeys("112");
        findElemByCSSAgent(firstName).sendKeys("AQA");
        findElemByCSSAgent(lastName).sendKeys("Test");
        checkAllCheckboxesForAddingNewPayment();
        nextButton.click();
        waitForAddingNewPaymentConfirmationPopup();
        return this;
    }

    public void checkAllCheckboxesForAddingNewPayment(){
        checkboxes.stream().forEach(e -> e.click());

    }

    public AddPaymentMethodWindow fillInNewCardInfo(){
        selectPaymentBox.click();
        waitForElementToBeVisibleAgent(choisesGroup, 5);
        cardOption.click();
        findElemByCSSAgent(cardNumber).sendKeys("4111111111111111");
        expirationMonth.click();
        thirdMons.click();
        expirationYear.click();
        thirdYear.click();
        findElemByCSSAgent(cardCvv).sendKeys("112");
        findElemByCSSAgent(firstName).sendKeys("AQA");
        findElemByCSSAgent(lastName).sendKeys("Test");
        return this;
    }

    public void clickCheckBox(int checkboxOrder){
        checkboxes.get(checkboxOrder-1).click();
    }

    public void clickAddPaymentButton(){
        executeJSclick(nextButton, DriverFactory.getAgentDriverInstance());
    }

    public void waitForAddingNewPaymentConfirmationPopup(){
        try {
            waitForElementToBeVisibleByXpathAgent(paymentAddedAlert, 10);
            waitForElementToBeInVisibleByXpathAgent(paymentAddedAlert, 5);
        }catch(TimeoutException e){
            // nothing to do 'cause it were stabilizing waits before continuing
        }
    }
}
