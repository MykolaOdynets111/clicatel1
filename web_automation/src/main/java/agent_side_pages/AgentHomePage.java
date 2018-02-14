package agent_side_pages;

import abstract_classes.AgentAbstractPage;
import agent_side_pages.UIElements.ChatBody;
import agent_side_pages.UIElements.LeftMenuWithChats;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import touch_pages.uielements.WidgetConversationArea;

public class AgentHomePage extends AgentAbstractPage {

    @FindBy(css = "textarea.text-input__control")
    private WebElement messageInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitMessageButton;

    @FindBy(css = "div.dashboard div.chat")
    private WebElement conversationAreaContainer;

    private LeftMenuWithChats leftMenuWithChats;
    private ChatBody chatBody;

    public LeftMenuWithChats getLeftMenuWithChats() {
        return leftMenuWithChats;
    }

    public ChatBody getChatBody() {
        return chatBody;
    }

    public AgentHomePage sendResponseToUser(String responseToUser) {
        messageInput.sendKeys(responseToUser);
        click(submitMessageButton);
        return this;
    }

    public boolean isAgentSuccessfullyLoggedIn() {
        try {
            return isElementShown(conversationAreaContainer);
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
