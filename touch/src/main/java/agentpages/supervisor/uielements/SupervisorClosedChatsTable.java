package agentpages.supervisor.uielements;


import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.lang.String.format;

@FindBy(css = ".chats-list-wrapper")
public class SupervisorClosedChatsTable extends AbstractUIElement {

    @FindBy(xpath = "//span[text()='Chat Ended']/ancestor::span/following-sibling::span/span[contains(@class, 'sorting-box__arrow--top')]")
    private WebElement ascendingArrowOfChatEndedColumn;

    @FindBy(css = ".chats-list .cl-table-row")
    private List<WebElement> closedChats;

    public List<LocalDateTime> getClosedChatsDates() {
        return closedChats.stream().map(e -> new SupervisorDeskClosedChatRow(e).setCurrentDriver(this.getCurrentDriver())).collect(Collectors.toList())
                .stream().map(SupervisorDeskClosedChatRow::getDate).collect(Collectors.toList());
    }

    public LocalDateTime getFirstClosedChatDate() {
        if (closedChats.size() == 0)
            throw new AssertionError("No closed chats found");
        return new SupervisorDeskClosedChatRow(closedChats.get(0))
                .setCurrentDriver(this.getCurrentDriver())
                .getDate();
    }

    public void clickAscendingArrowOfChatEndedColumn() {
        clickElem(this.getCurrentDriver(), ascendingArrowOfChatEndedColumn, 3, "Ascending Arrow Of Chat Ended Column");
    }

    public void openFirstClosedChat() {
        openChat(closedChats.get(0));
    }

    public boolean verifyChanelOfTheChatsIsPresent(String channelName) {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), closedChats, 7);
        return closedChats.stream()
                .map(e -> new SupervisorDeskClosedChatRow(e).setCurrentDriver(this.getCurrentDriver()))
                .allMatch(closedChat -> closedChat.getIconName().equalsIgnoreCase(channelName));
    }

    public String getUserName() {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), closedChats, 7);
        return new SupervisorDeskClosedChatRow(closedChats.get(0)).setCurrentDriver(this.getCurrentDriver()).getUserName();
    }

    public String getTagName() {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), closedChats, 7);
        return new SupervisorDeskClosedChatRow(closedChats.get(0)).setCurrentDriver(this.getCurrentDriver()).getTagName();
    }

    public boolean isClosedChatPresent(String userName) {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), closedChats, 7);
        return closedChats.stream().anyMatch(e ->
                new SupervisorDeskClosedChatRow(e)
                        .setCurrentDriver(this.getCurrentDriver())
                        .getName().equals(userName));
    }

    public void openClosedChat(String chatName) {
        WebElement chat = closedChats.stream().filter(c -> c.getText().equals(chatName)).findFirst()
                .orElseThrow(() -> new NoSuchElementException(format("Chat %s is absent!", chatName)));

        openChat(chat);
    }

    private void openChat(WebElement webElement) {
        new SupervisorDeskClosedChatRow(webElement)
                .setCurrentDriver(this.getCurrentDriver())
                .clickOnChat();
    }
}
