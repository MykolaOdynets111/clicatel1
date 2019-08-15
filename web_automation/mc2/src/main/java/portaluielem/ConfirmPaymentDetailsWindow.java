package portaluielem;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

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

    private String billingContact = "form[name='form.cartPaymentDetails']";

    @Step(value = "Select payment method option")
    public ConfirmPaymentDetailsWindow selectPaymentMethod(String payment){
        String paymentMethod = String.format(paymentMethodXpath, payment);
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(), paymentMethod, 8);
        clickHoldRelease(this.getCurrentDriver(), findElemByXPATH(this.getCurrentDriver(), paymentMethod),
                3, "Add Credit / Debit Card");
//        findElemByXPATH(this.getCurrentDriver(), paymentMethod).click();
        return this;
    }

    @Step(value = "Click select payment field")
    public ConfirmPaymentDetailsWindow clickSelectPaymentField(){
        waitForElementToBeVisible(this.getCurrentDriver(), selectPaymentBox, 5);
        selectPaymentBox.click();
        if(!isElementShownByCSS(this.getCurrentDriver(), choisesGroup, 5)){
            selectPaymentBox.click();
        }
        try {
            waitForElementToBeVisibleByCss(this.getCurrentDriver(), choisesGroup, 9);
        } catch(TimeoutException e){
            Assert.fail("Incorrect screen was shown after clicking select payment box \n " +
                    "Please check the screenshot");
        }
        return this;
    }

    public boolean isSelectPaymentShown(){
        return isElementShown(this.getCurrentDriver(), paymentethodWindowHeader, 5);
    }

    @Step(value = "Click 'Next' button")
    public ConfirmPaymentDetailsWindow clickNexButton(){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
//        waitFor(500);
        waitForElementToBeClickable(this.getCurrentDriver(), nextButton, 15);
        clickHoldRelease(this.getCurrentDriver(), nextButton, 2, "Next button");
        return this;
    }

    public ConfirmPaymentDetailsWindow clickNexButtonOnDetailsTab(){
        waitFor(500);
        clickHoldRelease(this.getCurrentDriver(), nextButton, 15, "Next Button");
        try {
            waitForElementToBeInVisibleByCss(this.getCurrentDriver(), billingContact, 3);
        }catch (TimeoutException e){
            clickHoldRelease(this.getCurrentDriver(), nextButton, 2, "Next Button");
        }
        return this;
    }

    @Step(value = "Click 'Pay now' button")
    public ConfirmPaymentDetailsWindow clickPayNowButton(){
        waitForElementToBeClickable(this.getCurrentDriver(), primaryBindingButton, 15);
        clickHoldRelease(this.getCurrentDriver(), primaryBindingButton, 1, "Primary button");
        return this;
    }

    @Step(value = "Accept all terms")
    public ConfirmPaymentDetailsWindow acceptTerms(){
        try {
            waitForElementsToBeVisibleByCss(this.getCurrentDriver(), acceptTermsCheckboxCSS, 7);
            findElemsByCSS(this.getCurrentDriver(), acceptTermsCheckboxCSS).forEach(WebElement::click);
        } catch(TimeoutException e){

        }

        return this;
    }

    public ConfirmPaymentDetailsWindow waitFotPaymentSummaryScreenToLoad(){
        waitForElementsToBeVisibleByCss(this.getCurrentDriver(), paymentSummaryScreenSCC, 10);
        return this;
    }

    public String getSuccessMessageMessage(){
        try{
            waitForElementsToBeVisibleByCss(this.getCurrentDriver(), paymentSuccessfulMessageCSS, 10);
            return findElemByCSS(this.getCurrentDriver(), paymentSuccessfulMessageCSS).getText();
        } catch (TimeoutException |NoSuchElementException e){
            return "no success message is shown";
        }
    }

    public String getTestVisaCardToPayDetails(){
        clickSelectPaymentField();
        return findElemByXPATH(this.getCurrentDriver(), String.format(paymentMethodXpath, "VISA")).getText();
    }

    public boolean isPaymentOptionShown(String option){
        return isElementShownByXpath(this.getCurrentDriver(), String.format(paymentMethodXpath, option), 4);
    }

    @Step(value = "Verify Payment Review tab opened")
    public boolean isPaymentReviewTabOpened(){
        return isElementShownByCSS(this.getCurrentDriver(), totalOrderSum, 12);
    }

    @Step(value = "Close Confirm Details window")
    public void closeWindow(){
        executeJSclick(findElemByCSS(this.getCurrentDriver(), closeWindowButton), this.getCurrentDriver());
        if(isElementShownByXpath(this.getCurrentDriver(), confirmationButton, 2))
            findElemByXPATH(this.getCurrentDriver(), confirmationButton).click();
    }

    public boolean isPaymentSummaryTabOpened(){
        return isElementShownByXpath(this.getCurrentDriver(), termsAndConditions, 1);
    }

    public boolean isBillingContactShown(){
        return isElementShownByCSS(this.getCurrentDriver(), billingContact, 3);
    }

}
