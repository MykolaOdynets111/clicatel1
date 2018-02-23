package touch_pages.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import touch_pages.uielements.messages.FromUserMessage;
import touch_pages.uielements.messages.ToUserMessageWithActions;
import touch_pages.uielements.messages.ToUserTextMessage;

import java.util.List;
import java.util.NoSuchElementException;

@FindBy(css = "div.ctl-conversation-area")
public class WidgetConversationArea extends AbstractUIElement {

    @FindBy(css = "li.ctl-chat-message-container.message-to.with-content")
    private WebElement salutationElement;

    @FindBy(css = "li.ctl-chat-message-container.message-from")
    private List<WebElement> fromUserMessages;


    private WebElement getFromUserWebElement(String messageText) {
        FromUserMessage theMessage =  fromUserMessages.stream().map(e -> new FromUserMessage(e))
                                            .filter(e1 -> e1.getMessageText().equals(messageText))
                                            .findFirst().get();
        return theMessage.getWrappedElement();
    }


    public String getResponseTextOnUserInput(String userMessageText) {
            return new ToUserTextMessage(getFromUserWebElement(userMessageText)).getMessageText();
    }

    public boolean isTextResponseShownFor(String userMessageText, int wait) {
        return new ToUserTextMessage(getFromUserWebElement(userMessageText)).isTextResponseShown(wait);
    }

    public boolean isOnlyOneTextResponseShwonFor(String userMessage) {
        return new ToUserTextMessage(getFromUserWebElement(userMessage)).isOnlyOneTextResponseShwon();
    }

    public boolean isCardShownFor(String userMessageText, int wait) {
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).isTextInCardShown(wait);
    }

    public boolean isCardButtonsShownFor(String userMessageText, List<String> buttons) {
        boolean result = false;
        for (String button : buttons) {
            result = new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).isButtonShown(button);
        }
        return result;
    }

    public void  waitForSalutation() {
        try {
            waitForElementToBeVisible(salutationElement, 5);
        } catch (TimeoutException e) {
        }
    }

    public void clickOptionInTheCard(String userMessageText, String buttonName) {
        new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).clickButton(buttonName);
    }
}
