package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.NoSuchElementException;
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
        clickElemAgent(sendOTPButton, 1, "main", "Send OTP");
    }

    public void closeSendOTPPopUp(){
        clickElemAgent(cancelButton, 1, "main", "Cancel");
    }

    public void sendOrCancelClick(String buttonName){
        if (buttonName.equalsIgnoreCase("send otp"))
            sendOTPMessage();
        else if (buttonName.equalsIgnoreCase("cancel"))
            closeSendOTPPopUp();
        else
            throw new NoSuchElementException("No such button with specified name");
    }

    public boolean isOpened(){
        return isElementShownAgent(this.getWrappedElement(), 1);
    }

    public boolean isClosed(){
        return isElementNotShown(this.getWrappedElement(), 1);
    }
}
