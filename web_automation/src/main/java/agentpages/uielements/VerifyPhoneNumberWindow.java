package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(xpath = "//div[text()='Verify phone number']/parent::div")
public class VerifyPhoneNumberWindow extends AbstractUIElement {

    String overlappedPage = "//section[@id='app'][@aria-hidden='true']";

    @FindBy(css = "#otpcontactPhone")
    private WebElement phoneNumberInputField;

    @FindBy(xpath = ".//button[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//span[text()='Send OTP']/parent::button")
    private WebElement sendOTPButton;

    public String getEnteredPhoneNumber(){
        return phoneNumberInputField.getAttribute("value");
    }

    public VerifyPhoneNumberWindow enterPhoneNumber(String phoneNumber){
        phoneNumberInputField.clear();
        phoneNumberInputField.sendKeys(phoneNumber);
        return this;
    }

    public void sendOTPMessage(){
        sendOTPButton.click();
        waitFor(300);
    }

    public void closeSendOTPPopUp(){
        cancelButton.click();
        waitFor(300);
    }

    public boolean isOpened(){
        return isElementShownAgent(this.getWrappedElement());
    }
}
