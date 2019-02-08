package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

@FindBy(css = "div.chat-header")
public class ChatHeader extends AbstractUIElement {

    @FindBy(xpath = ".//button[text()='End chat']")
    private WebElement endChatButton;

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

    public String getChatHeaderText(){
        return chatHeaderTitle.getText();
    }

}
