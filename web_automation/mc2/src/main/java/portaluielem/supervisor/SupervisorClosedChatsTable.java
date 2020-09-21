package portaluielem.supervisor;


import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".chats-list-wrapper")
public class SupervisorClosedChatsTable extends AbstractUIElement {

    @FindBy (xpath = "//span[text()='Chat Ended']/ancestor::span/following-sibling::span/span[contains(@class, 'sorting-box__arrow--top')]")
    private  WebElement ascendingArrowOfChatEndedColumn;

    @FindBy(css =".chats-list .cl-table-row")
    private List<WebElement> closedChats;

    public List<LocalDateTime> getClosedChatsDates(){
        return closedChats.stream().map(e -> new SupervisorDeskClosedChatRow(e).setCurrentDriver(this.getCurrentDriver())).collect(Collectors.toList())
                .stream().map(a -> a.getDate()).collect(Collectors.toList());
    }

    public void clickAscendingArrowOfChatEndedColumn(){
        clickElem(this.getCurrentDriver(), ascendingArrowOfChatEndedColumn, 3, "Ascending Arrow Of Chat Ended Column");
    }

}
