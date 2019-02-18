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

@FindBy(css = "div.cl-wizzard.create-integration-container")
public class ConfirmPaymentDetailsWindow extends BasePortalWindow {

    @FindBy(xpath = "//label[text()='Payment Method']")
    private WebElement paymentethodWindowHeader;

    @FindBy(css = "span[aria-label='Select box activate']")
    private WebElement selectPaymentBox;

    private String billingContactHeader = "//legend[text()='Billing contact']";

    private String choisesGroup = "li.ui-select-choices-group";

    private String closeWindowButton = "span.svg-close";

    private String confirmationButton = "//button[text()='Yes']";

    private String totalOrderSum = "th[ng-bind='model.totalCurrency']";

    private String acceptTermsCheckboxCSS = "label.checkbox-control.control--checkbox";

    private String paymentSuccessfulMessageCSS = "div[ng-bind='processingStatusMessage']";

    private String paymentSummaryScreenSCC = "ul[class ='payment-summary list-unstyled mod-absolute-side']";

    private String paymentMethodXpath =  "//span[@class='ui-select-choices-row-inner']/span[contains(text(), '%s')]";

    private String termsAndConditions = "//a[text()='Terms and Conditions']";

    public ConfirmPaymentDetailsWindow selectPaymentMethod(String payment){
        waitForElementToBeVisibleByXpathAgent(String.format(paymentMethodXpath, payment), 8);
        findElemByXPATHAgent(String.format(paymentMethodXpath, payment)).click();
        return this;
    }

    public ConfirmPaymentDetailsWindow clickSelectPaymentField(){
        waitForElementToBeVisibleAgent(selectPaymentBox, 5);
        selectPaymentBox.click();
        if(!isElementShownAgentByCSS(choisesGroup, 5, "admin")){
            selectPaymentBox.click();
        }
        waitForElementToBeVisibleByCssAgent(choisesGroup, 9);
        return this;
    }

    public boolean isSelectPaymentShown(){
        return isElementShownAgent(paymentethodWindowHeader, 5);
    }
    
    public ConfirmPaymentDetailsWindow clickNexButton(){
        waitFor(500);
        waitForElementToBeClickableAgent(nextButton, 15, "admin");
        clickHoldRelease(DriverFactory.getAgentDriverInstance(), nextButton);
        return this;
    }

    public ConfirmPaymentDetailsWindow clickNexButtonOnDetailsTab(){
        waitFor(500);
        waitForElementToBeClickableAgent(nextButton, 15, "admin");
        clickHoldRelease(DriverFactory.getAgentDriverInstance(), nextButton);
        try {
            waitForElementToBeInvisibleAgent(cartPaymentDetailsForm, 3);
        }catch (TimeoutException e){
            clickHoldRelease(DriverFactory.getAgentDriverInstance(), nextButton);

        }
        return this;
    }

    public ConfirmPaymentDetailsWindow clickPayNowButton(){
//        getNgDriver(DriverFactory.getAgentDriverInstance()).waitForAngularRequestsToFinish();
        waitForElementToBeClickableAgent(payNowButton, 15, "admin");
        clickHoldRelease(DriverFactory.getAgentDriverInstance(), payNowButton);
        return this;
    }

    public ConfirmPaymentDetailsWindow acceptTerms(){
        try {
            waitForElementsToBeVisibleByCssAgent(acceptTermsCheckboxCSS, 7);
            findElemsByCSSAgent(acceptTermsCheckboxCSS).forEach(WebElement::click);
        } catch(TimeoutException e){

        }

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
