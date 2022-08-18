package agentpages.supervisor.uielements;

import abstractclasses.AbstractUIElement;
import agentpages.supervisor.SupervisorDeskPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".chats-list")
public class SupervisorOpenedClosedChatsList extends AbstractUIElement {
    @FindBy(css = ".cl-r-chat-item")
    private List<WebElement> closedChats;

    public boolean isClosedChatsHaveSendEmailButton() {
        waitForFirstElementToBeVisible(getCurrentDriver(), closedChats, 5);
        return closedChats.stream().allMatch(closedChat -> {
            wheelScrollDownToElement(this.getCurrentDriver(), this.getWrappedElement(), closedChat, 3);
            clickElem(getCurrentDriver(), closedChat, 2, "Closed Chat");
            return new SupervisorDeskPage(this.getCurrentDriver()).isSendEmailForOpenedClosedChatShown();
        });
    }

    public boolean isClosedChatsHaveMessageCustomerButton() {
        waitForFirstElementToBeVisible(getCurrentDriver(), closedChats, 5);
        return closedChats.stream().allMatch(closedChat -> {
            wheelScrollDownToElement(this.getCurrentDriver(), this.getWrappedElement(), closedChat, 3);
            clickElem(getCurrentDriver(), closedChat, 2, "Closed Chat");
            return new SupervisorDeskPage(this.getCurrentDriver()).isMessageCustomerButtonForClosedChatShown();
        });
    }
}
