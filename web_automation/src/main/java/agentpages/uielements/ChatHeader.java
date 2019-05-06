package agentpages.uielements;

import abstractclasses.AbstractUIElement;
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

    @FindBy(css = "div.chat-header-title")
    private WebElement chatHeaderTitle;

    private String transferChatButton =  "//button[text()='Transfer chat']";

    public void clickEndChatButton(){
        if (!isElementShownAgent(endChatButton)){
            Assert.assertTrue(false, "'End chat' button is not shown.");
        } else {endChatButton.click();}
    }

    public boolean isEndChatShown(String agent){
        return isElementShownAgent(endChatButton,1, agent);
    }

    public void clickTransferButton(){
        waitForElementToBeVisibleByXpathAgent(transferChatButton, 5, "main agent");
        findElemByXPATHAgent(transferChatButton).click();
    }

    public boolean isTransferButtonEnabled(){
        waitForElementToBeVisibleByXpathAgent(transferChatButton, 5, "main agent");
        return findElemByXPATHAgent(transferChatButton).isEnabled();
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
        String hexColor = Color.fromString(endChatButton.getCssValue("color")).asHex();
        return hexColor;
    }

    public String getTransferButtonColor() {
        String hexColor = Color.fromString(transferButton.getCssValue("color")).asHex();
        return hexColor;
    }

    public String getPinChatButtonColor() {
        String hexColor = Color.fromString(pinChatButton.getCssValue("color")).asHex();
        return hexColor;
    }
}
