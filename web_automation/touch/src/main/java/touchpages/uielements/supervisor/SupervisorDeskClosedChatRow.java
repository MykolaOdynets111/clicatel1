package touchpages.uielements.supervisor;

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

    protected SupervisorDeskClosedChatRow(WebElement element) {
        super(element);
    }

    public SupervisorDeskClosedChatRow setCurrentDriver(WebDriver currentDriver) {
        this.currentDriver = currentDriver;
        return this;
    }

    public LocalDateTime getDate() {
        scrollToElem(this.getCurrentDriver(), date, "Date cell");
        String stringDate = getTextFromElem(this.getCurrentDriver(), date, 5, "Date cell").replace("\n", " ");
        return LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }

    public void clickOnChat() {
        chatNameCell.click();
    }
}
