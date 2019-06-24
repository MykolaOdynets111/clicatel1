package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

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

    public void clickTransferButton(){
        waitForElementToBeVisibleByXpathAgent(transferChatButton, 5, "main agent");
        findElemByXPATHAgent(transferChatButton).click();
    }

    public boolean isButtonEnabled(String buttonTitle){
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

    public String getEndChatButtonColor() {
        return Color.fromString(endChatButton.getCssValue("color")).asHex();
    }

    public String getTransferButtonColor() {
        return Color.fromString(transferButton.getCssValue("color")).asHex();
    }

    public String getPinChatButtonColor() {
        return  Color.fromString(pinChatButton.getCssValue("color")).asHex();
    }
}
