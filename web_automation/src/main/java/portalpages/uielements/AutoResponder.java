package portalpages.uielements;

import abstractclasses.AbstractUIElement;
import drivermanager.DriverFactory;
import interfaces.JSHelper;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@FindBy(css = "div.integration-content cl-content-area mod-portal mod-small-cards automated-messages ng-scope")
public class AutoResponder  extends Widget implements WebActions, JSHelper {

      @FindBy(css = "bttn-toggle ng-valid ng-not-empty ng-dirty']")
      private WebElement buttonOnOff;

    public AutoResponder(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

      public void clickOnOff(){
        waitForElementToBeVisibleAgent(buttonOnOff, 10);
        buttonOnOff.click();
    }


//    @FindBy(css = "span[aria-label='Select box activate']")
//    private WebElement selectPaymentBox;
//
//    @FindBy(xpath =  ".//span[@class='ui-select-choices-row-inner']/span[contains(text(), 'Card')]")
//    private WebElement cardOption;
//
//    @FindBy(css = "div#expirationMonth")
//    private WebElement expirationMonth;
//
//    @FindBy(xpath = "(//li[@id='ui-select-choices-5']/div)[5]")
//    private WebElement thirdMons;
//
//    @FindBy(css = "div[cl-options='expirationYears']")
//    private WebElement expirationYear;
//
//    @FindBy(xpath =  "(//li[@id='ui-select-choices-6']/div)[5]")
//    private WebElement thirdYear;
//
//    @FindBy(css = "input[type='checkbox']+span")
//    private List<WebElement> checkboxes;
//
//    private String cardNumber = "input#cardNumber";
//
//    private String cardCvv =  "input#cardCvv";
//
//    private String firstName = "input#firstName";
//
//    private String lastName =  "input#lastName";
//
//    private String paymentAddedAlert = "//div[@ng-bind-html='alert'][text()='Payment method has been configured successfully']";
//
//    public AutoResponder addTestCardAsANewPayment(){
//        selectPaymentBox.click();
//        waitForElementsToBeVisibleAgent(selectOptionsInDropdown, 5, "admin");
//        cardOption.click();
//        findElemByCSSAgent(cardNumber).sendKeys("4111111111111111");
//        expirationMonth.click();
//        waitForElementToBeClickableAgent(thirdMons, 2, "main");
//        thirdMons.click();
//        expirationYear.click();
//        waitForElementToBeClickableAgent(thirdYear, 2, "main");
//        thirdYear.click();
//        findElemByCSSAgent(cardCvv).sendKeys("112");
//        findElemByCSSAgent(firstName).sendKeys("AQA");
//        findElemByCSSAgent(lastName).sendKeys("Test");
//        checkAllCheckboxesForAddingNewPayment();
//        waitForAngularToBeReady(DriverFactory.getAgentDriverInstance());
//        waitFor(2000);
////        getNgDriver(DriverFactory.getAgentDriverInstance()).waitForAngularRequestsToFinish();
//        executeAngularClick(DriverFactory.getAgentDriverInstance(), addPaymentMethod);
//        return this;
//    }
//
//    public void checkAllCheckboxesForAddingNewPayment(){
//        checkboxes.stream().forEach(e -> e.click());
//
//    }
//
//    public AutoResponder fillInNewCardInfo(){
//        selectPaymentBox.click();
//        waitForElementToBeVisibleAgent(selectOptionsInDropdown.get(0), 5, "admin");
//        cardOption.click();
//        findElemByCSSAgent(cardNumber).sendKeys("4111111111111111");
//        expirationMonth.click();
//        thirdMons.click();
//        expirationYear.click();
//        thirdYear.click();
//        findElemByCSSAgent(cardCvv).sendKeys("112");
//        findElemByCSSAgent(firstName).sendKeys("AQA");
//        findElemByCSSAgent(lastName).sendKeys("Test");
//        return this;
//    }
//
//    public void clickCheckBox(int checkboxOrder){
//        checkboxes.get(checkboxOrder-1).click();
//    }
//
//    public void clickNextButton(){
////        getNgDriver(DriverFactory.getAgentDriverInstance()).waitForAngularRequestsToFinish();
//        executeAngularClick(DriverFactory.getAgentDriverInstance(), nextButton);
//    }
//
//    public void clickAddPaymentButton(){
////        getNgDriver(DriverFactory.getAgentDriverInstance()).waitForAngularRequestsToFinish();
//        waitForElementToBeClickableAgent(addPaymentMethod, 15, "admin");
//        waitFor(2000);
//        executeAngularClick(DriverFactory.getAgentDriverInstance(), addPaymentMethod);
//    }
//
//
//    public boolean isAddPaymentButtonEnabled(){
//        executeJSHover(addPaymentMethodButton, DriverFactory.getAgentDriverInstance());
//        return addPaymentMethodButton.isEnabled();
//    }
//
//    public void waitForAddingNewPaymentConfirmationPopup(){
//        try {
//            waitForElementToBeVisibleByXpathAgent(paymentAddedAlert, 15);
//            waitForElementToBeInVisibleByXpathAgent(paymentAddedAlert, 5);
//        }catch(TimeoutException e){
//            // nothing to do 'cause it were stabilizing waits before continuing
//        }
//    }

}
