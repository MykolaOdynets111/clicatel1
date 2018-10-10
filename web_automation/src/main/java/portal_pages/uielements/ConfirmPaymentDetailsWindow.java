package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(css = "div.cl-wizzard.create-integration-container")
public class ConfirmPaymentDetailsWindow extends AbstractUIElement {

    @FindBy(css = "span[aria-label='Select box activate']")
    private WebElement selectPaymentBox;

    @FindBy(css = "li.ui-select-choices-group")
    private WebElement choisesGroup;

    @FindBy(xpath =  ".//span[@class='ui-select-choices-row-inner']/span[contains(text(), 'VISA')]")
    private WebElement visaOption;

    @FindBy(css = "button.button.button-primary.ng-scope")
    private WebElement nextButton;

    @FindBy(css = "input[type='checkbox']")
    private WebElement acceptTermsCheckbox;

    public void selectTestVisaCardToPay(){
        selectPaymentBox.click();
        waitForElementToBeVisibleAgent(choisesGroup, 5);
        visaOption.click();
        nextButton.click();
    }

    public void confirmPaymentReviewStep(){
        waitForElementToBeVisibleAgent(nextButton, 7);
        nextButton.click();
    }

    public void acceptTermsAndClickPayNow(){
        waitForElementToBeVisibleAgent(acceptTermsCheckbox, 7);
        acceptTermsCheckbox.click();
        nextButton.click();
    }
}
