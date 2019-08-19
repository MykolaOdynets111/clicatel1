package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import drivermanager.ConfigManager;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

@FindBy(css = "div.user-info-container>div")
public class Customer360Container extends AbstractUIElement {


    @FindBy(css = "h2.context-profile-name")
    private WebElement profileNameLabel;
    @FindAll({
            @FindBy(name = "firstName"),
            @FindBy(css = "input.info-name-input")
    })
    private WebElement profileNameInput;

    @FindBy(css = "span.icon.icon-location+span")
    private WebElement locationLabel;
    @FindBy(name = "location")
    private WebElement locationInput;

    @FindBy(css = "span.icon.icon-sending+span")
    private WebElement customerSinceLabel;

    @FindBy(css = "span.icon.icon-mail+span")
    private WebElement mailLabel;
    @FindBy(name = "email")
    private WebElement mailInput;

    @FindBy(css = "span.icon.icon-twitter+span")
    private WebElement twitterLabel;

    @FindBy(css = "span.icon.icon-facebook+span")
    private WebElement fbLabel;

    @FindBy(css = "span.icon.svg-icon-mobile+span")
    private WebElement phoneLabel;

    @FindBy(name = "phone")
    private WebElement phoneInput;

    @FindBy(xpath = "//div[@class='info-row']/button[text()='Send OTP']")
    private WebElement sendOTPButton;

    @FindBy(xpath = "//div[@class='info-row']/button[text()='Verify']")
    private WebElement verifyPhoneButton;

    @FindBy(xpath = "//div[@class='info-row']/button[text()='Re-send OTP']")
    private WebElement resendOTPButton;

    @FindBy(css = ".status-text")
    private WebElement verifiedLabel;

    @FindBy(css = "button.pull-right.disable-spacing-top.btn-default")
    private WebElement saveEditButton;

    @FindBy(xpath = "//div[@class='empty-icon no-border']")
    private WebElement userPicture;

    @FindBy(xpath = "//div[contains(@class,'context-part')]//a")
    private WebElement mailColor;

    private String phoneCSS = "span.icon.svg-icon-mobile+span";

    public Customer360PersonalInfo getActualPersonalInfo(){
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), profileNameLabel, 3);
        } catch (TimeoutException e){
            Assert.fail("Customer 360 details is not visible.");
        }

        String channelUsername = "Unknown";
        if(!twitterLabel.getText().equals("Unknown")) channelUsername = twitterLabel.getText();
        if(!fbLabel.getText().equals("Unknown")) channelUsername = fbLabel.getText();
        if(channelUsername.equals("Unknown")&getUserNameFromLocalStorage(this.getCurrentDriver())!=null) channelUsername = getUserNameFromLocalStorage(this.getCurrentDriver());
        return new Customer360PersonalInfo(profileNameLabel.getText().replace("\n", " "),
                locationLabel.getText(), customerSinceLabel.getText(),
                mailLabel.getText(), channelUsername, phoneLabel.getText().replaceAll(" ", ""));
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
        phoneInput.clear();
        phoneInput.sendKeys(valuesToSet.getPhone());
    }

    public void setPhoneNumber(String phoneNumber){
        phoneInput.clear();
        phoneInput.sendKeys(phoneNumber);
    }

    public String getUserFullName(){
        return profileNameLabel.getText().replace("\n", " ");
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
        return findElemByCSS(this.getCurrentDriver(),phoneCSS).getText(); // phoneLabel.getText();
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
            return getPhoneNumber().equalsIgnoreCase("Unknown");
        else
            return !getPhoneNumber().equalsIgnoreCase("Unknown");
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
        return isElementNotShown(this.getCurrentDriver(), verifiedLabel, 1);
    }
}
