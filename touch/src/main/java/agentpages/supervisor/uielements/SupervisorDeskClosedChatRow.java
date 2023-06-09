package agentpages.supervisor.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;

import static datetimeutils.DateTimeHelper.parseDate;

public class SupervisorDeskClosedChatRow extends AbstractWidget {

    @FindBy(css = ".cl-table-cell--endedDate")
    private WebElement date;
    @FindBy(css = ".chats-list .cl-user-details")
    private WebElement chatNameCell;
    @FindBy(css = ".chats-list .cl-table-cell:nth-child(2)>svg")
    private WebElement channelIcon;

    @FindBy(css = ".chats-list .chat-tags-list")
    private WebElement tagName;

    @FindBy(css = "h6.user-details__name")
    private WebElement userName;

    private String scrollAreaCss = "[data-testid='chats-list-scroll-container'] [class='iScrollVerticalScrollbar iScrollLoneScrollbar']";

    protected SupervisorDeskClosedChatRow(WebElement element) {
        super(element);
    }

    public SupervisorDeskClosedChatRow setCurrentDriver(WebDriver currentDriver) {
        this.currentDriver = currentDriver;
        return this;
    }

    public String getName(){
        return  userName.getText();
    }

    public LocalDateTime getDate() {
        wheelScrollDownToElement(this.getCurrentDriver(),
                findElemByCSS(this.getCurrentDriver(), scrollAreaCss), date, 3);
        String stringDate = getTextFromElem(this.getCurrentDriver(),
                date, 5, "Date cell").replace("\n", " ");

        return parseDate(stringDate);
    }

    public void clickOnChat() {
        clickElem(this.getCurrentDriver(), chatNameCell, 3, "Closed Chat name Cell");
    }

    public String getIconName() {
        return channelIcon.getAttribute("name").trim();
    }

    public String getTagName() {
        return getTextFromElem(this.getCurrentDriver(), tagName, 2, "Tag Name");
    }

    public String getUserName(){
        return getTextFromElem(this.getCurrentDriver(), userName, 2, "User Name");
    }
}
