package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import dbmanager.DBConnector;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@FindBy(css = ".cl-r-chat-header")
public class ChatHeader extends AbstractUIElement {

    @FindAll({
            @FindBy(css = "[selenium-id=header-exit-chat]"),
            @FindBy(css = ".cl-r-end-chat") //old locator
    })
    private WebElement endChatButton;

    @FindAll({
            @FindBy(css = "[selenium-id=header-flag-chat]"),
            @FindBy(css = ".cl-r-chat-unflagged")
    })
    private WebElement flagChatButton;

    @FindAll({
            @FindBy(css = "[selenium-id=header-unflag-chat]"),
            @FindBy(css = ".cl-r-chat-flagged")
    })
    private WebElement unflagChatButton;

    @FindAll({
            @FindBy(css = "[selenium-id=header-transfer-chat]"),
            @FindBy(css = ".cl-r-icon-transfer")
    })
    private WebElement transferButton;

    @FindBy(css = "[selenium-id=header-send-sms]")
    private WebElement sendSMSButton;

    //for future
    @FindBy(css = "[selenium-id=header-whats-app-button]")
    private WebElement sendWhatsAppButton;

    @FindBy(css = "[selenium-id=chat-header-title]")
    private WebElement chatHeaderTitle;

    @FindBy(css = "[selenium-id=chat-header-channel-icon]")
    private WebElement channelImg;

    @FindBy(xpath = ".//span[@class= 'cl-r-chat-header-time'][1]")
    private WebElement timeStamp;

    @FindBy(css = "[selenium-id=header-cancel-transfer]")
    private WebElement cancelTransferButton;

    @FindBy(css = ".cl-r-avatar")
    private WebElement userAvatar;


    private String transferChatButton =  ".//button[@selenium-id='header-transfer-chat']";
    private String sendSMSXpath = ".//button[@selenium-id='header-send-sms']";
    private String sendWhatsAppXpath = ".//button[text()='Send WhatsApp']";

    public ChatHeader (WebDriver current){
        this.currentDriver = current;
    }

    public ChatHeader (){
        super();
    }

    public void clickEndChatButton() {
        if (!isElementShown(this.getCurrentDriver(), endChatButton, 4)) {
            Assert.fail("'End chat' button is not shown.");
        } else {
            clickElem(this.getCurrentDriver(), endChatButton, 6, "End chat button");
        }
    }

    public boolean isEndChatShown(){
        return isElementShown(this.getCurrentDriver(), endChatButton,4);
    }

    public void clickTransferButton(){
        clickElem(this.getCurrentDriver(), transferButton, 5, "Transfer button");
        waitForAngularToBeReady(this.getCurrentDriver());
        waitForAngularRequestsToFinish(this.getCurrentDriver());
    }

    public boolean isButtonEnabled(String buttonTitle){
        try {
            switch (buttonTitle) {
                case "Transfer chat":
                    waitForElementToBeVisibleByXpath(this.getCurrentDriver(), transferChatButton, 5);
                    return findElemByXPATH(this.getCurrentDriver(), transferChatButton).isEnabled();
                case "Send SMS":
                    waitForElementToBeVisibleByXpath(this.getCurrentDriver(), sendSMSXpath, 5);
                    return findElemByXPATH(this.getCurrentDriver(), sendSMSXpath).isEnabled();
                case "Send WhatsApp":
                    waitForElementToBeVisibleByXpath(this.getCurrentDriver(), sendWhatsAppXpath, 5);
                    return findElemByXPATH(this.getCurrentDriver(), sendWhatsAppXpath).isEnabled();

                default:
                    throw new NoSuchElementException("Button '" + buttonTitle + "' wasn't found");
            }
        }catch (TimeoutException e){
            return false;
        }
    }

    public String getChatHeaderText(){
        return chatHeaderTitle.getText();
    }

    public void clickFlagChatButton(){
        clickElem(this.getCurrentDriver(), flagChatButton, 2,"Flag chat");
    }

    public void clickUnflagChatButton(){
        clickElem(this.getCurrentDriver(), unflagChatButton, 2, "Unflag chat");
    }

    public void clickCancelTransferButton(){
        clickElem(this.getCurrentDriver(), cancelTransferButton, 2, "Cancel transfer");
    }

    public String getEndChatButtonColor() {
        return Color.fromString(endChatButton.getCssValue("color")).asHex();
    }

    public String getTransferButtonColor() {
        return Color.fromString(transferButton.getCssValue("color")).asHex();
    }

    public String getPinChatButtonColor() {
        return  Color.fromString(flagChatButton.getCssValue("color")).asHex();
    }

    public boolean isValidChannelImg(String channelPictureName) {
        File image = new File(System.getProperty("user.dir")+"/touch/src/test/resources/adaptericons/"+channelPictureName+".png");
        return isWebElementEqualsImage(this.getCurrentDriver(), channelImg, image);
    }
        //Verify if tame stanp in 24 hours format
    public String getTimeStamp() {
        return timeStamp.getAttribute("textContent").trim();
    }

    public String getTextHeader() {
        return  chatHeaderTitle.getText().toLowerCase();
    }

    public boolean isAvatarContainUserNameFirstLetter(String userName){
        String expectedInitials;
        List<String> userNames = Arrays.asList(userName.split(" "));
        if(userNames.size() == 2){
            expectedInitials = userNames.get(0).substring(0,1) + userNames.get(1).substring(0,1);
        } else {
            expectedInitials = userNames.get(0).substring(0,1);
        }
        String letter = getTextFromElem(this.getCurrentDriver(), userAvatar, 1,"Avatar");
        return letter.equalsIgnoreCase(expectedInitials);
    }
}
