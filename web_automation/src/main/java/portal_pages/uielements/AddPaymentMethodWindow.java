package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
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

    @FindBy(xpath =  ".//span[@class='ui-select-choices-row-inner']/span[contains(text(), 'Debit/Credit Card')]")
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
        checkboxes.stream().forEach(e -> e.click());
        nextButton.click();
        return this;
    }
}
