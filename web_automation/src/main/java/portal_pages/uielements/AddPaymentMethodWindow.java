package portal_pages.uielements;

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

    @FindBy(xpath = "(//li[@id='ui-select-choices-5']/div)[5]")
    private WebElement thirdMons;

    @FindBy(css = "div[cl-options='expirationYears']")
    private WebElement expirationYear;

    @FindBy(xpath =  "(//li[@id='ui-select-choices-6']/div)[5]")
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
        waitForElementsToBeVisibleAgent(selectOptionsInDropdown, 5, "admin");
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
        addPaymentMethod.click();
        return this;
    }

    public void checkAllCheckboxesForAddingNewPayment(){
        checkboxes.stream().forEach(e -> e.click());

    }

    public AddPaymentMethodWindow fillInNewCardInfo(){
        selectPaymentBox.click();
        waitForElementToBeVisibleAgent(selectOptionsInDropdown.get(0), 5, "admin");
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

    public void clickNextButton(){
        nextButton.click();
    }

    public void clickAddPaymentButton(){
        addPaymentMethod.click();
    }


    public void waitForAddingNewPaymentConfirmationPopup(){
        try {
            waitForElementToBeVisibleByXpathAgent(paymentAddedAlert, 15);
            waitForElementToBeInVisibleByXpathAgent(paymentAddedAlert, 5);
        }catch(TimeoutException e){
            // nothing to do 'cause it were stabilizing waits before continuing
        }
    }
}
