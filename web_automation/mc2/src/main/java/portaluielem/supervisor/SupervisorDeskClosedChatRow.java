package portaluielem.supervisor;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SupervisorDeskClosedChatRow extends AbstractWidget {

    protected SupervisorDeskClosedChatRow(WebElement element) {
        super(element);
    }

    public SupervisorDeskClosedChatRow setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    @FindBy (xpath = ".//br/parent::div")
    private WebElement date;

    public LocalDateTime getDate() {
        String stringDate = getTextFromElem(this.getCurrentDriver(), date, 1, "Date cell").replace("\n", " ");
         return LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }
}
