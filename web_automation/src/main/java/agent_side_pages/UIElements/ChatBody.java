package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;

@FindBy(css = "div.chat-body")
public class ChatBody extends AbstractUIElement {

    private String fromUserMessagesXPATH = "//li[@class='from']//span[text()='%s']";

    @FindBy(css = "li.from")
    private List<WebElement> fromUserMessages;

    @FindBy(css = "li.to")
    private List<WebElement> toUserMessages;

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

    public boolean isUserMessageShown(String usrMessage) {
        try {

            waitForElementsToBeVisible(fromUserMessages, 15);
            for (int i = 0; i < 35; i++) {
                if (checkThatExpectedUserMessageOnAgentDesk(usrMessage)) {
                    return true;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return false;
        } catch(TimeoutException e1){
            return false;
        }
    }

    public boolean isUserMessageShown(String usrMessage, String agent) {
        return isElementShownAgentByXpath(String.format(fromUserMessagesXPATH, usrMessage), 40, agent);

    }

    private boolean checkThatExpectedUserMessageOnAgentDesk(String usrMessage) {
        return findElemsByXPATHAgent(fromUserMessagesXPATH).stream()
                .map(AgentDeskFromUserMessage::new)
                .anyMatch(e2 -> e2.getMessageText().equalsIgnoreCase(usrMessage));
    }

    public boolean isMoreThanOneUserMassageShown() {
        if (fromUserMessages.size()>1){
            return true;
        } else{
            return false;
        }
    }

    public boolean isResponseOnUserMessageShown(String userMesage) {
        return new AgentDeskToUserMessage(getFromUserWebElement(userMesage)).isTextResponseShown(5);
    }
}
