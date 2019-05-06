package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import datamanager.Customer360PersonalInfo;
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

    @FindBy(css = "button.pull-right.disable-spacing-top.btn-default")
    private WebElement saveEditButton;

    @FindBy(xpath = "//div[@class='empty-icon no-border']")
    private WebElement userPicture;

    @FindBy(xpath = "//div[contains(@class,'context-part')]//a")
    private WebElement mailColor;

    public Customer360PersonalInfo getActualPersonalInfo(){
        try {
            waitForElementToBeVisibleAgent(profileNameLabel, 3, "main");
        } catch (TimeoutException e){
            Assert.assertTrue(false, "Customer 360 details is not visible.");
        }

        String channelUsername = "Unknown";
        if(!twitterLabel.getText().equals("Unknown")) channelUsername = twitterLabel.getText();
        if(!fbLabel.getText().equals("Unknown")) channelUsername = fbLabel.getText();
        if(channelUsername.equals("Unknown")&!getUserNameFromLocalStorage().equals("")) channelUsername = getUserNameFromLocalStorage();
        return new Customer360PersonalInfo(profileNameLabel.getText().replace("\n", " "), locationLabel.getText(), customerSinceLabel.getText(),
                mailLabel.getText(), channelUsername, phoneLabel.getText().replaceAll(" ", ""));
    }

    public void clickSaveEditButton(){
        saveEditButton.click();
    }

    public void fillFormWithNewDetails(Customer360PersonalInfo valuesToSet){
        profileNameInput.clear();
        profileNameInput.sendKeys(valuesToSet.getFullName());
        locationInput.clear();
        locationInput.sendKeys(valuesToSet.getLocation());
        mailInput.clear();
        mailInput.sendKeys(valuesToSet.getEmail());
        phoneInput.clear();
        phoneInput.sendKeys(valuesToSet.getPhone());
    }

    public String getUserFullName(){
        return profileNameLabel.getText().replace("\n", " ");
    }

    public String getSaveEditButtonColor() {
        String hexColor = Color.fromString(saveEditButton.getCssValue("color")).asHex();
        return hexColor;
    }

    public String getUserPictureColor() {
        String hexColor = Color.fromString(userPicture.getCssValue("background-color")).asHex();
        return hexColor;
    }

    public String getMailColor() {
        String hexColor = Color.fromString(mailColor.getCssValue("color")).asHex();
        return hexColor;
    }

}
