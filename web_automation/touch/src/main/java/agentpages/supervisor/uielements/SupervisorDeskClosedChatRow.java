package agentpages.supervisor.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SupervisorDeskClosedChatRow extends AbstractWidget {

    @FindBy(xpath = ".//br/parent::div")
    private WebElement date;
    @FindBy(css = ".chats-list .cl-user-details")
    private WebElement chatNameCell;
    @FindBy(css = ".chats-list .cl-table-cell:nth-child(2)>svg")
    private WebElement channelIcon;

    private String scrollAreaCss = "[selenium-id='roster-scroll-container']";

    protected SupervisorDeskClosedChatRow(WebElement element) {
        super(element);
    }

    public SupervisorDeskClosedChatRow setCurrentDriver(WebDriver currentDriver) {
        this.currentDriver = currentDriver;
        return this;
    }

    public LocalDateTime getDate() {
        wheelScrollDownToElement(this.getCurrentDriver(),
                findElemByCSS(this.getCurrentDriver(), scrollAreaCss), date, 3);
        String stringDate = getTextFromElem(this.getCurrentDriver(),
                date, 5, "Date cell").replace("\n", " ");
        return LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }

    public void clickOnChat() {
        chatNameCell.click();
    }

    public String getIconName() {
        return channelIcon.getAttribute("name").trim();
    }
}
