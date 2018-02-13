package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import touch_pages.uielements.messages.FromUserMessage;

import java.util.List;

@FindBy(css = "div.chat-body")
public class ChatBody extends AbstractUIElement {

    @FindBy(css = "li.from")
    private List<WebElement> fromUserMessages;

    private WebElement getFromUserWebElement(String messageText) {
        AgentDeskFromUserMessage theMessage =  fromUserMessages.stream().map(e -> new AgentDeskFromUserMessage(e))
                .filter(e1 -> e1.getMessageText().equals(messageText))
                .findFirst().get();
        return theMessage.getWrappedElement();
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
