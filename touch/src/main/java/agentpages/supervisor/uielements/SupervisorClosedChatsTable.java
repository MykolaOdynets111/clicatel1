package agentpages.supervisor.uielements;


import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".chats-list-wrapper")
public class SupervisorClosedChatsTable extends AbstractUIElement {

    @FindBy (xpath = "//span[text()='Chat Ended']/ancestor::span/following-sibling::span/span[contains(@class, 'sorting-box__arrow--top')]")
    private  WebElement ascendingArrowOfChatEndedColumn;

    @FindBy(css =".chats-list .cl-table-row")
    private List<WebElement> closedChats;

    @FindBy(css = ".chats-list .iscroll-main iscroll-main-chats-list-scroll cl-scroll-wrap")
    private WebElement scrollArea;

    @FindBy(css = ".chats-list__loading-more")
    private WebElement loadingMoreClosedChats;

    public List<LocalDateTime> getClosedChatsDates(){
        return closedChats.stream().map(e -> new SupervisorDeskClosedChatRow(e).setCurrentDriver(this.getCurrentDriver())).collect(Collectors.toList())
                .stream().map(a -> a.getDate()).collect(Collectors.toList());
    }

    public LocalDateTime getFirstClosedChatDate(){
        if(closedChats.size() == 0)
            throw new AssertionError("No closed chats found");
        return new SupervisorDeskClosedChatRow(closedChats.get(0))
                .setCurrentDriver(this.getCurrentDriver())
                .getDate();
    }

    public void clickAscendingArrowOfChatEndedColumn(){
        clickElem(this.getCurrentDriver(), ascendingArrowOfChatEndedColumn, 3, "Ascending Arrow Of Chat Ended Column");
    }

    public void openFirstClosedChat() {
        new SupervisorDeskClosedChatRow(closedChats.get(0))
                .setCurrentDriver(this.getCurrentDriver())
                .clickOnChat();
    }

    public void scrollClosedChatsToTheBottom(){
        wheelScroll(this.getCurrentDriver(), scrollArea, 2000, 0,0);
    }

    public void scrollClosedChatsToTheTop(){
        wheelScroll(this.getCurrentDriver(), scrollArea, -2000, 0,0);
    }

    public void waitForMoreTicketsAreLoading(int waitForSpinnerToAppear, int waitForSpinnerToDisappear){
        waitForAppearAndDisappear(this.getCurrentDriver(), loadingMoreClosedChats, waitForSpinnerToAppear, waitForSpinnerToDisappear);
    }

    public void loadAllFoundChats() {
        int closedChats;
        do {
            closedChats = this.closedChats.size();
            scrollClosedChatsToTheBottom();
            waitForMoreTicketsAreLoading(2, 5);
        } while (closedChats != this.closedChats.size());
        scrollClosedChatsToTheTop();
    }

    public boolean verifyChanelOfTheChatsIsPresent(String channelName){
        waitForFirstElementToBeVisible(this.getCurrentDriver(), closedChats, 7);
        return  closedChats.stream()
                .map(e -> new SupervisorDeskClosedChatRow(e).setCurrentDriver(this.getCurrentDriver()))
                .allMatch(closedChat -> closedChat.getIconName().equalsIgnoreCase(channelName));
    }

    public String getUserName(){
        waitForFirstElementToBeVisible(this.getCurrentDriver(), closedChats, 7);
        return new SupervisorDeskClosedChatRow(closedChats.get(0)).setCurrentDriver(this.getCurrentDriver()).getUserName();
    }
}