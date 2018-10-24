package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portal_pages.CartPage;


@FindBy(css = "div.cl-wizzard.create-integration-container")
public class AddPaymentMethodWindow extends AbstractUIElement {

    @FindBy(css = "span[aria-label='Select box activate']")
    private WebElement selectPaymentBox;

    @FindBy(css = "li.ui-select-choices-group")
    private WebElement choisesGroup;

    @FindBy(xpath =  ".//span[@class='ui-select-choices-row-inner']/span[contains(text(), 'Debit/Credit Card')]")
    private WebElement cardOption;

    @FindBy(css = "input#cardNumber")
    private WebElement cardNumber;


    public AddPaymentMethodWindow selectTestVisaCardToPay(){
        selectPaymentBox.click();
        waitForElementToBeVisibleAgent(choisesGroup, 5);
        cardOption.click();
//        nextButton.click();
        return this;
    }
}
