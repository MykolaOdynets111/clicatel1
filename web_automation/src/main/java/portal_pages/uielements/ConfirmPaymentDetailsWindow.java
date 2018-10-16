package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ConfirmPaymentDetailsWindow extends Widget implements WebActions{

    public ConfirmPaymentDetailsWindow(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    @FindBy(css = "span[aria-label='Select box activate']")
    private WebElement selectPaymentBox;

    @FindBy(css = "li.ui-select-choices-group")
    private WebElement choisesGroup;

    @FindBy(xpath =  ".//span[@class='ui-select-choices-row-inner']/span[contains(text(), 'VISA')]")
    private WebElement visaOption;

    @FindBy(css = "button.button.button-primary.ng-scope")
    private WebElement nextButton;

    private String acceptTermsCheckboxCSS = "label.cl-label.checkbox-control";

    private String paymentSuccessfulMessageCSS = "div[ng-bind='processingStatusMessage']";

    private String paymentSummaryScreenSCC = "ul[class ='payment-summary list-unstyled mod-absolute-side']";

    public ConfirmPaymentDetailsWindow selectTestVisaCardToPay(){
        selectPaymentBox.click();
        waitForElementToBeVisibleAgent(choisesGroup, 5);
        visaOption.click();
        nextButton.click();
        return this;
    }

    public ConfirmPaymentDetailsWindow clickNexButton(){
        waitForElementToBeVisibleAgent(nextButton, 7);
        nextButton.click();
        return this;
    }

    public ConfirmPaymentDetailsWindow acceptTerms(){
        waitForElementsToBeVisibleByCssAgent(acceptTermsCheckboxCSS, 7);
        findElemByCSSAgent(acceptTermsCheckboxCSS).click();
        return this;
    }

    public ConfirmPaymentDetailsWindow waitFotPaymentSummaryScreenToLoad(){
        waitForElementsToBeVisibleByCssAgent(paymentSummaryScreenSCC, 10);
        return this;
    }

    public String getSuccessMessageMessage(){
        try{
            waitForElementsToBeVisibleByCssAgent(paymentSuccessfulMessageCSS, 10);
            return findElemByCSSAgent(paymentSuccessfulMessageCSS).getText();
        } catch (TimeoutException |NoSuchElementException e){
            return "no success message is shown";
        }
    }
}
