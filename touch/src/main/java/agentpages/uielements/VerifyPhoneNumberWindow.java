package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(xpath = "//div[text()='Verify phone number']/ancestor::div[contains(@class,'ReactModal__Content')]")
public class VerifyPhoneNumberWindow extends AbstractUIElement {

    @FindBy(css = "[seleniumid='send-otp-modal-title']")
    private WebElement popUpTitle;

    @FindBy(css = "[data-testid='send-otp-modal-phone']")
    private WebElement phoneNumberInputField;

    @FindBy(css = "[data-testid='send-otp-modal-cancel']")
    private WebElement cancelButton;

    @FindBy(css = "[data-testid='send-otp-modal-send']")
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
        clickElem(this.getCurrentDriver(), sendOTPButton, 1,"Send OTP");
    }

    public void closeSendOTPPopUp(){
        clickElem(this.getCurrentDriver(), cancelButton, 1,"Cancel");
    }

    public void sendOrCancelClick(String buttonName){
        if (buttonName.equalsIgnoreCase("send otp"))
            sendOTPMessage();
        else if (buttonName.equalsIgnoreCase("cancel"))
            closeSendOTPPopUp();
        else
            throw new NoSuchElementException("No such button with specified name");
    }

    public boolean isOpened(int wait){
        return isElementShown(this.getCurrentDriver(), this.getWrappedElement(), wait);
    }

    public boolean isClosed(){
        return isElementRemoved(this.getCurrentDriver(), popUpTitle, 3);
    }
}
