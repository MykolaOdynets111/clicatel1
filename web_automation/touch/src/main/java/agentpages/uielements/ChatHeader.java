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
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TimeZone;

@FindBy(css = "[selenium-id=chat-header]")
public class ChatHeader extends AbstractUIElement {

    @FindBy(css = "[selenium-id=header-exit-chat]")
    private WebElement endChatButton;

    @FindBy(css = "[selenium-id=header-flag-chat]")
    private WebElement pinChatButton;

    @FindBy(css = "[selenium-id=header-unflag-chat]")
    private WebElement unpinChatButton;

    @FindBy(css = "[selenium-id=header-transfer-chat]")
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

    @FindBy(css = "[selenium-id=chat-header-timer]")
    private WebElement timeStamp;

    @FindBy(css = "[selenium-id=header-cancel-transfer]")
    private WebElement cancelTransferButton;



    private String transferChatButton =  "//button[text()='Transfer chat']";
    private String sendSMSXpath = ".//button[text()='Send SMS']";
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
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(), transferChatButton, 5);
        findElemByXPATH(this.getCurrentDriver(), transferChatButton).click();
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
        clickElem(this.getCurrentDriver(), pinChatButton, 2,"Flag chat");
    }

    public void clickUnflagChatButton(){
        clickElem(this.getCurrentDriver(), unpinChatButton, 2, "Unflag chat");
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
        return  Color.fromString(pinChatButton.getCssValue("color")).asHex();
    }

    public boolean isValidChannelImg() {
        File image = new File(System.getProperty("user.dir")+"/touch/src/test/resources/adaptericons/headerChannel.png");
        return isWebElementEqualsImage(this.getCurrentDriver(), channelImg, image);
    }
        //Verify if tame stanp in 24 hours format
    public boolean isValidTimeStamp() {
        Map<String, String> sessionDetails = DBConnector.getSessionDetailsByClientID(ConfigManager.getEnv()
                ,getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        String startedDate = sessionDetails.get("startedDate");
        int numberOfMillis = startedDate.split("\\.")[1].length();
        String dateFormat = "yyyy-MM-dd HH:mm:ss." + StringUtils.repeat("S", numberOfMillis);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDateTime formatDateTime = LocalDateTime.parse(startedDate, formatter).atZone(ZoneId.of("UTC")).withZoneSameInstant(TimeZone.getDefault().toZoneId()).toLocalDateTime();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("'Started:' dd MMM, HH:mm|");
        return timeStamp.getAttribute("textContent").equals(formatDateTime.format(formatter2));
    }

    public String getTextHeader() {
        return  chatHeaderTitle.getText().toLowerCase();
    }

}
