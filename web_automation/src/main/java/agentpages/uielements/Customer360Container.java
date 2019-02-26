package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import datamanager.Customer360PersonalInfo;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

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

    @FindBy(css = "span.icon.icon-twitter+span")
    private WebElement fbLabel;

    @FindBy(css = "span.icon.svg-icon-mobile+span")
    private WebElement phoneLabel;
    @FindBy(name = "phone")
    private WebElement phoneInput;

    @FindBy(css = "button.pull-right.disable-spacing-top.btn-default")
    private WebElement saveEditButton;


    public Customer360PersonalInfo getActualPersonalInfo(){
        waitForElementToBeVisibleAgent(profileNameLabel, 3, "main");

        String channelUsername = "Unknown";
        if(!twitterLabel.getText().equals("Unknown")) channelUsername = twitterLabel.getText();
        if(!fbLabel.getText().equals("Unknown")) channelUsername = fbLabel.getText();

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
}
