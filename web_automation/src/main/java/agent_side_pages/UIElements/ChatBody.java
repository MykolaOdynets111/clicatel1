package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import touch_pages.uielements.messages.FromUserMessage;

import java.util.List;
import java.util.NoSuchElementException;

@FindBy(css = "div.chat-body")
public class ChatBody extends AbstractUIElement {

    @FindBy(css = "li.from")
    private List<WebElement> fromUserMessages;

    private WebElement getFromUserWebElement(String messageText) {
        try {
            AgentDeskFromUserMessage theMessage = fromUserMessages.stream().map(e -> new AgentDeskFromUserMessage(e))
                    .filter(e1 -> e1.getMessageText().equals(messageText))
                    .findFirst().get();
            return theMessage.getWrappedElement();
        } catch (NoSuchElementException e){
            Assert.assertTrue(false, "There is no such user message: "+messageText+"");
            return null;
        }
    }

    public boolean isUserMessageShown(String usrMessage){
       return fromUserMessages.stream()
               .map(e -> new AgentDeskFromUserMessage(e))
               .anyMatch(e2 -> e2.getMessageText().equalsIgnoreCase(usrMessage));
    }

    public boolean isResponseOnUserMessageShown(String userMesage) {
        return new AgentDeskToUserMessage(getFromUserWebElement(userMesage)).isTextResponseShown(5);
    }
}
