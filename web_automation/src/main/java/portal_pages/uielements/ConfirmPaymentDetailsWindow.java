package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import driverManager.DriverFactory;
import interfaces.JSHelper;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ConfirmPaymentDetailsWindow extends Widget implements WebActions, JSHelper {

    public ConfirmPaymentDetailsWindow(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    @FindBy(css = "span[aria-label='Select box activate']")
    private WebElement selectPaymentBox;

    @FindBy(css = "li.ui-select-choices-group")
    private WebElement choisesGroup;
    
    @FindBy(css = "button.button.button-primary.ng-scope")
    private WebElement nextButton;

    private String closeWindowButton = "span.svg-close";

    private String confirmationButton = "//button[text()='Yes']";

    private String totalOrderSum = "th[ng-bind='model.totalCurrency']";

    private String acceptTermsCheckboxCSS = "label.checkbox-control.control--checkbox";

    private String paymentSuccessfulMessageCSS = "div[ng-bind='processingStatusMessage']";

    private String paymentSummaryScreenSCC = "ul[class ='payment-summary list-unstyled mod-absolute-side']";

    private String paymentMethodXpath =  "//span[@class='ui-select-choices-row-inner']/span[contains(text(), '%s')]";

    private String termsAndConditions = "//a[text()='Terms and Conditions']";

    public ConfirmPaymentDetailsWindow selectPaymentMethod(String payment){
        findElemByXPATHAgent(String.format(paymentMethodXpath, payment)).click();
        return this;
    }

    public ConfirmPaymentDetailsWindow clickSelectPaymentField(){
        selectPaymentBox.click();
        waitForElementToBeVisibleAgent(choisesGroup, 9);
        return this;
    }
    
    public ConfirmPaymentDetailsWindow clickNexButton(){
        waitFor(500);
        waitForElementToBeClickableAgent(nextButton, 7, "admin");
        JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getAgentDriverInstance();
        executor.executeScript("arguments[0].click();", nextButton);
        return this;
    }

    public ConfirmPaymentDetailsWindow acceptTerms(){
        waitForElementsToBeVisibleByCssAgent(acceptTermsCheckboxCSS, 7);
        findElemsByCSSAgent(acceptTermsCheckboxCSS).forEach(WebElement::click);
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

    public String getTestVisaCardToPayDetails(){
        clickSelectPaymentField();
        return findElemByXPATHAgent(String.format(paymentMethodXpath, "VISA")).getText();
    }

    public boolean isPaymentOptionshown(String option){
        return isElementShownAgentByXpath(String.format(paymentMethodXpath, option), 4, "admin");
    }

    public boolean isPaymentReviewTabOpened(){
        return isElementShownAgentByCSS(totalOrderSum, 12, "admin");
    }

    public void closeWindow(){
        executeJSclick(findElemByCSSAgent(closeWindowButton), DriverFactory.getAgentDriverInstance());
        if(isElementShownAgentByXpath(confirmationButton, 2, "admin")) findElemByXPATHAgent(confirmationButton).click();
    }

    public boolean isPaymnentSummaryTabOPened(){
        return isElementShownAgentByXpath(termsAndConditions, 1, "admin");
    }
}
