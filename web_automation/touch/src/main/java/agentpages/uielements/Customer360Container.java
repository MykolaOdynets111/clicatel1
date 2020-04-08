package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import datamanager.Customer360PersonalInfo;
import drivermanager.ConfigManager;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

@FindBy(css = "[selenium-id=user-profile-container]")
public class Customer360Container extends AbstractUIElement {


    @FindBy(css = "[selenium-id=user-profile-name]")
    private WebElement profileNameLabel;

    @FindBy(css = "[selenium-id=user-profile-name]")
    private WebElement profileNameInput;

    @FindBy(css = "[selenium-id=user-profile-location]")
    private WebElement locationLabel;
    @FindBy(css = "[selenium-id=user-profile-location]")
    private WebElement locationInput;

    @FindBy(css = "[selenium-id=user-profile-since]")
    private WebElement customerSinceLabel;

    @FindBy(css = "[selenium-id=user-profile-email]")
    private WebElement mailLabel;
    @FindBy(css = "[selenium-id=user-profile-email]")
    private WebElement mailInput;

    @FindBy(css = "[selenium-id=user-profile-twitter]")
    private WebElement twitterLabel;

    @FindBy(css = "[selenium-id=user-profile-facebook]")
    private WebElement fbLabel;

    @FindBy(css = "[selenium-id=user-profile-phone]")
    private WebElement phoneLocator;

    @FindBy(css = "[selenium-id=user-profile-send-otp]")
    private WebElement sendOTPButton;

    @FindBy(css = "[selenium-id=user-profile-verify]")
    private WebElement verifyPhoneButton;

    @FindBy(css = "[selenium-id=user-profile-send-otp]")
    private WebElement resendOTPButton;

    @FindBy(css = "[selenium-id=user-profile-phone-status]")
    private WebElement verifiedLabel;

    @FindBy(css = ".cl-r-tab-profile-header .cl-r-button")
    private WebElement saveEditButton;

    @FindBy(xpath = "//div[@class='empty-icon no-border']")
    private WebElement userPicture;

    @FindBy(css = "[selenium-id=user-profile-container]")
    private WebElement mailColor;

    private String phoneCSS = "[selenium-id = 'user-profile-phone']";

    public Customer360PersonalInfo getActualPersonalInfo(){
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), profileNameLabel, 3);
        } catch (TimeoutException e){
            Assert.fail("Customer 360 details is not visible.");
        }

        String channelUsername = "Unknown";
        String location;
        String phone;
        if(!twitterLabel.getText().equals("Unknown")) channelUsername = twitterLabel.getText();
        if(!fbLabel.getText().equals("Unknown")) channelUsername = fbLabel.getText();
        if(channelUsername.equals("Unknown")&getUserNameFromLocalStorage(this.getCurrentDriver())!=null) channelUsername = getUserNameFromLocalStorage(this.getCurrentDriver());
        if(channelUsername.equals("Unknown")) channelUsername = profileNameLabel.getAttribute("value");
        if (phoneLocator.getAttribute("value").isEmpty()){
            phone = "Unknown";
        } else {
            phone = phoneLocator.getAttribute("value");
        }
        if (locationLabel.getAttribute("value").isEmpty()){
            location = "Unknown location";
        } else {
            location = locationLabel.getAttribute("value");
        }
        return new Customer360PersonalInfo(profileNameLabel.getAttribute("value").replace("\n", " "),
                location, customerSinceLabel.getText(), mailLabel.getAttribute("value"), channelUsername, phone.replaceAll(" ", ""));
    }

    public void clickSaveEditButton(){
        saveEditButton.click();
    }

    public void fillFormWithNewDetails(Customer360PersonalInfo valuesToSet){
        if(!ConfigManager.getSuite().contains("twitter")) { // for now, because there is an issue TPORT-3989
            profileNameInput.clear();
            profileNameInput.sendKeys(valuesToSet.getFullName());
        }
        locationInput.clear();
        locationInput.sendKeys(valuesToSet.getLocation());
        mailInput.clear();
        mailInput.sendKeys(valuesToSet.getEmail());
        setPhoneNumber(valuesToSet.getPhone());
    }

    public void setPhoneNumber(String phoneNumber){
        //phoneInput.clear(); does not clear the value
        phoneLocator.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        phoneLocator.sendKeys(phoneNumber);
    }

    public String getUserFullName(){
        return profileNameLabel.getAttribute("value");
    }

    public String getSaveEditButtonColor() {
        return Color.fromString(saveEditButton.getCssValue("color")).asHex();
    }

    public String getUserPictureColor() {
        return Color.fromString(userPicture.getCssValue("background-color")).asHex();
    }

    public String getMailColor() {
        return Color.fromString(mailColor.getCssValue("color")).asHex();
    }

    public String getPhoneNumber() {
        waitForElementToBeVisibleByCss(this.getCurrentDriver(),phoneCSS, 2);
        return findElemByCSS(this.getCurrentDriver(),phoneCSS).getAttribute("value");//getText(); // phoneLabel.getText();
    }

    public boolean isCustomer360SMSButtonsDisplayed(String button){
        switch (button.toLowerCase()) {
            case "send otp":
                return isElementShown(this.getCurrentDriver(), sendOTPButton, 4);
            case "verify":
                return isElementShown(this.getCurrentDriver(), verifyPhoneButton, 4);
            case "re-send otp":
            case "re send otp":
            case "resend otp":
                return isElementShown(this.getCurrentDriver(), resendOTPButton, 4);
            default:
                return false;
        }
    }

    public boolean isPhoneNumberFieldUpdated(String requiredEndState){
        if (requiredEndState.equalsIgnoreCase("deleted"))
            return getPhoneNumber().equalsIgnoreCase("");
        else
            return !getPhoneNumber().equalsIgnoreCase("");
    }

    public void clickPhoneNumberVerificationButton(String buttonName){
        switch (buttonName.toLowerCase()) {
            case "send otp":
                clickElem(this.getCurrentDriver(), sendOTPButton, 1,"Send OTP");
                break;
            case "verify":
                clickElem(this.getCurrentDriver(), verifyPhoneButton, 1,"Verify");
                break;
            case "re-send otp":
            case "re send otp":
            case "resend otp":
                clickElem(this.getCurrentDriver(), resendOTPButton, 1,"Re-send OTP");
                break;
        }
    }

    public boolean isVerifiedLabelDisplayed(){
        return isElementShown(this.getCurrentDriver(), verifiedLabel, 3);
    }

    public boolean isVerifiedLabelHidden(){
        return isElementRemoved(this.getCurrentDriver(), verifiedLabel, 3);
    }
}
