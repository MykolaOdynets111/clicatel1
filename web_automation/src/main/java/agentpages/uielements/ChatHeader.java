package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TimeZone;

@FindBy(css = "div.chat-header")
public class ChatHeader extends AbstractUIElement {

    @FindBy(xpath = ".//button[text()='End chat']")
    private WebElement endChatButton;

    @FindBy(xpath = ".//button[text()='Pin chat']")
    private WebElement pinChatButton;

    @FindBy(xpath = ".//button[text()='Unpin chat']")
    private WebElement unpinChatButton;

    @FindBy(xpath = ".//button[text()='Transfer chat']")
    private WebElement transferButton;

    @FindBy(xpath = ".//button[text()='Send SMS']")
    private WebElement sendSMSButton;

    //for future
    @FindBy(xpath = ".//button[text()='Send WhatsApp']")
    private WebElement sendWhatsAppButton;

    @FindBy(css = "div.chat-header-title")
    private WebElement chatHeaderTitle;

    @FindBy(xpath = "//div[contains(@class,'chat-header')]//div[@class='icons']/span/*")
    private WebElement channelImg;

    @FindBy(xpath = "//div/span[@class='time']")
    private WebElement timeStamp;

    @FindBy(xpath = ".//button[text()='Cancel transfer']")
    private WebElement cancelTransferButton;



    private String transferChatButton =  "//button[text()='Transfer chat']";
    private String sendSMSXpath = ".//button[text()='Send SMS']";
    private String sendWhatsAppXpath = ".//button[text()='Send WhatsApp']";


    public void clickEndChatButton() {
        if (!isElementShownAgent(endChatButton)) {
            Assert.assertTrue(false, "'End chat' button is not shown.");
        } else {
            clickElemAgent(endChatButton, 6, "agent", "End chat button");
        }
    }

    public boolean isEndChatShown(String agent){
        return isElementShownAgent(endChatButton,1, agent);
    }

    public void clickTransferButton(String agent){
        waitForElementToBeVisibleByXpathAgent(transferChatButton, 5, agent);
        findElemByXPATHAgent(transferChatButton,agent).click();
    }

    public boolean isButtonEnabled(String buttonTitle){
        try {
            switch (buttonTitle) {
                case "Transfer chat":
                    waitForElementToBeVisibleByXpathAgent(transferChatButton, 5, "main agent");
                    return findElemByXPATHAgent(transferChatButton).isEnabled();
                case "Send SMS":
                    waitForElementToBeVisibleByXpathAgent(sendSMSXpath, 5, "main agent");
                    return findElemByXPATHAgent(sendSMSXpath).isEnabled();
                case "Send WhatsApp":
                    waitForElementToBeVisibleByXpathAgent(sendWhatsAppXpath, 5, "main agent");
                    return findElemByXPATHAgent(sendWhatsAppXpath).isEnabled();

                default:
                    throw new NoSuchElementException("Button '" + buttonTitle + "' wasn't found");
            }
        }catch (TimeoutException e){
            Assert.fail(buttonTitle + " is not visible.");
            return false;
        }
    }

    public String getChatHeaderText(){
        return chatHeaderTitle.getText();
    }

    public void clickPinButton(String agent){
        clickElemAgent(pinChatButton, 2, agent, "Pin chat");
    }

    public void clickUnpinButton(String agent){
        clickElemAgent(unpinChatButton, 2, agent, "Unpin chat");
    }

    public void clickCancelTransferButton(String agent){
        clickElemAgent(cancelTransferButton, 2, agent, "Cancel transfer");
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
        File image = new File("src/test/resources/adaptericons/headerChannel.png");
        return isWebElementEqualsImage(channelImg,image, "main");
    }
        //Verify if tame stanp in 24 hours format
    public boolean isValidTimeStamp() {
        Map<String, String> sessionDetails = DBConnector.getSessionDetailsByClientID(ConfigManager.getEnv(),getUserNameFromLocalStorage());
        String startedDate = sessionDetails.get("startedDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime formatDateTime = LocalDateTime.parse(startedDate, formatter).atZone(ZoneId.of("UTC")).withZoneSameInstant(TimeZone.getDefault().toZoneId()).toLocalDateTime();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("'Started:' dd MMM, HH:mm|");
        return timeStamp.getAttribute("textContent").equals(formatDateTime.format(formatter2));
    }

    public String getTextHeader() {
        return  chatHeaderTitle.getText().toLowerCase();
    }

}
