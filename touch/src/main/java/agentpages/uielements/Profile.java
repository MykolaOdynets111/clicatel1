package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import datamanager.UserPersonalInfo;
import drivermanager.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

@FindBy(css = "[data-testid=tab-right-panel-0]")
public class Profile extends AbstractUIElement {


    @FindBy(css = "[data-testid=user-profile-name]")
    private WebElement profileNameLabel;

    @FindAll({
            @FindBy(css = "[data-testid=user-profile-name]"),
            @FindBy(css = "[selenium-id=user-profile-name]")
    })
    private WebElement profileNameInput;

    @FindBy(css = "[data-testid=user-profile-location]")
    private WebElement locationLabel;
    @FindBy(css = "[data-testid=user-profile-location]")
    private WebElement locationInput;

    @FindBy(css = "[data-testid=user-profile-since]")
    private WebElement customerSinceLabel;

    @FindBy(css = "[data-testid=user-profile-email]")
    private WebElement mailLabel;
    @FindBy(css = "[data-testid=user-profile-email]")
    private WebElement mailInput;

    @FindBy(css = "[data-testid=user-profile-twitter]")
    private WebElement twitterLabel;

    @FindBy(css = "[data-testid=user-profile-facebook]")
    private WebElement fbLabel;

    @FindBy(xpath = "//input[@name=\"phoneNumber\"]")
    private WebElement phoneLocator;

    @FindBy(css = "[data-testid=user-profile-send-otp]")
    private WebElement sendOTPButton;

    @FindBy(css = "[data-testid=user-profile-verify]")
    private WebElement verifyPhoneButton;

    @FindBy(css = "[data-testid=user-profile-send-otp]")
    private WebElement resendOTPButton;

    @FindBy(css = "[data-testid=user-profile-phone-status]")
    private WebElement verifiedLabel;

    @FindBy(xpath="//div[@data-testid=\'user-profile-phone-status\']")
    private WebElement notVerifiedLabel;
    @FindBy(xpath = "//button[@name=\"user-profile-save\"]")
    private WebElement saveEditButton;

    @FindBy(xpath = "//div[@class='empty-icon no-border']")
    private WebElement userPicture;

    @FindBy(css = "[data-testid=user-profile-container]")
    private WebElement mailColor;

    @FindBy(xpath = "//button[text()=\'Edit\']")
    private WebElement editButton;

    private String phoneCSS = "[data-testid = 'user-profile-phone']";

    public boolean isProfilePageDisplayed() {
        return isElementShown(this.getCurrentDriver(), getWrappedElement(), 2);
    }

    public UserPersonalInfo getActualPersonalInfo() {
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), profileNameLabel, 3);
        } catch (TimeoutException e) {
            Assert.fail("User details is not visible.");
        }

        String channelUsername = "Unknown";
        String location;
        String phone;
        if (!twitterLabel.getText().equals("Unknown")) channelUsername = twitterLabel.getText();
        if (!fbLabel.getText().equals("Unknown")) channelUsername = fbLabel.getText();
        if (channelUsername.equals("Unknown") & getUserNameFromLocalStorage(this.getCurrentDriver()) != null)
            channelUsername = getUserNameFromLocalStorage(this.getCurrentDriver());
        if (channelUsername.equals("Unknown")) channelUsername = profileNameLabel.getAttribute("value");
        if (phoneLocator.getAttribute("value").isEmpty()) {
            phone = "Unknown";
        } else {
            phone = phoneLocator.getAttribute("value").replace("+", "");
        }
        if (locationLabel.getAttribute("value").isEmpty()) {
            location = "Unknown location";
        } else {
            location = locationLabel.getAttribute("value");
        }
        return new UserPersonalInfo(profileNameLabel.getAttribute("value").replace("\n", " "),
                location, customerSinceLabel.getText(), mailLabel.getAttribute("value"), channelUsername, phone.replaceAll(" ", ""));
    }

    public void clickSaveEditButton() {
        saveEditButton.click();
    }

    public void clickEditButton() {
        editButton.click();
    }

    public void fillFormWithNewDetails(UserPersonalInfo valuesToSet) {
        if (!ConfigManager.getSuite().contains("twitter")) { // for now, because there is an issue TPORT-3989
            profileNameInput.clear();
            profileNameInput.sendKeys(valuesToSet.getFullName());
        }
        locationInput.clear();
        locationInput.sendKeys(valuesToSet.getLocation());
        mailInput.clear();
        mailInput.sendKeys(valuesToSet.getEmail());
        setPhoneNumber(valuesToSet.getPhone());
    }

    public void setPhoneNumber(String phoneNumber) {
        //phoneInput.clear(); does not clear the value
        phoneLocator.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        phoneLocator.sendKeys(phoneNumber);
    }

    public String getUserFullName() {
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
        waitForElementToBeVisibleByCss(this.getCurrentDriver(), phoneCSS, 2);
        return findElemByCSS(this.getCurrentDriver(), phoneCSS).getAttribute("value");//getText(); // phoneLabel.getText();
    }

    public boolean isUserSMSButtonsDisplayed(String button) {
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

    public boolean isPhoneNumberFieldUpdated(String requiredEndState) {
        if (requiredEndState.equalsIgnoreCase("deleted"))
            return getPhoneNumber().equalsIgnoreCase("");
        else
            return !getPhoneNumber().equalsIgnoreCase("");
    }

    public void clickPhoneNumberVerificationButton(String buttonName) {
        switch (buttonName.toLowerCase()) {
            case "send otp":
                clickElem(this.getCurrentDriver(), sendOTPButton, 1, "Send OTP");
                break;
            case "verify":
                clickElem(this.getCurrentDriver(), verifyPhoneButton, 1, "Verify");
                break;
            case "re-send otp":
            case "re send otp":
            case "resend otp":
                clickElem(this.getCurrentDriver(), resendOTPButton, 1, "Re-send OTP");
                break;
        }
    }

    public boolean isVerifiedLabelDisplayed() {
        return isElementShown(this.getCurrentDriver(), verifiedLabel, 3);
    }

    public boolean isVerifiedLabelHidden() {
        return isElementRemoved(this.getCurrentDriver(), verifiedLabel, 5);
    }

    public boolean isNotVerifiedLabelDisplayed(){
        return isElementShown(this.getCurrentDriver(), notVerifiedLabel, 3);
    }
}
