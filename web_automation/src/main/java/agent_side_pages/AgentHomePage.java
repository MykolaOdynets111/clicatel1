package agent_side_pages;

import abstract_classes.AgentAbstractPage;
import agent_side_pages.UIElements.ChatBody;
import agent_side_pages.UIElements.Header;
import agent_side_pages.UIElements.LeftMenuWithChats;
import agent_side_pages.UIElements.SuggestedGroup;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import touch_pages.uielements.WidgetConversationArea;

public class AgentHomePage extends AgentAbstractPage {

    @FindBy(xpath = "//textarea[contains(@class,'text-input')]")
    private WebElement messageInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitMessageButton;

    @FindBy(css = "div.dashboard div.chat")
    private WebElement conversationAreaContainer;

    private LeftMenuWithChats leftMenuWithChats;
    private ChatBody chatBody;
    private Header header;
    private SuggestedGroup suggestedGroup;

    public SuggestedGroup getSuggestedGroup() {
        return suggestedGroup;
    }

    public Header getHeader() {
        return header;
    }

    public LeftMenuWithChats getLeftMenuWithChats() {
        return leftMenuWithChats;
    }

    public ChatBody getChatBody() {
        return chatBody;
    }

    public AgentHomePage sendResponseToUser(String responseToUser) {
        try {
            messageInput.sendKeys(responseToUser);
            click(submitMessageButton);
            return this;
        } catch (InvalidElementStateException e){
            Assert.assertTrue(false, "There is a problem with agent desk page." +
                    " Check if there is no blinking connection error.");
            return this;
        }

    }

    public boolean isAgentSuccessfullyLoggedIn() {
        try {
            return isElementShown(conversationAreaContainer);
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
