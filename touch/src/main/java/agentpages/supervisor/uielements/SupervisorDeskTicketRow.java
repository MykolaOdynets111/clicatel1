package agentpages.supervisor.uielements;


import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class SupervisorDeskTicketRow extends AbstractWidget {

    private WebElement baseWebElem = this.getWrappedElement();

    @FindBy(css = ".cl-checkbox")
    private WebElement checkbox;

    @FindBy(css = ".cl-agent-name")
    private WebElement currentAgent;

    @FindBy(css = ".cl-user-name")
    private WebElement userName;

    @FindBy(css = ".cl-table-user-description__location")
    private WebElement location;

    @FindBy(css = ".cl-light-grey span")
    private WebElement status;

    @FindBy(xpath = "//div[@class = 'cl-table-user-data__description']/div[2]")
    private WebElement phone;

    @FindBy(xpath = ".//span[@class ='time-cell-content'][1]")
    private WebElement startDate;

    @FindBy(xpath = ".//span[@class ='time-cell-content'][2]")
    private WebElement endDate;

    @FindBy(css = ".cl-user-details-cell__top-section svg")
    private WebElement channelIcon;

    private String scrollAreaCss = "[data-testid='chatslist-scroll-container'] [class='iScrollVerticalScrollbar iScrollLoneScrollbar']";
    // private String chatConsoleInboxRowNameCss = ".cl-user-name";

    public SupervisorDeskTicketRow(WebElement element) {
        super(element);
    }

    public SupervisorDeskTicketRow setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public void selectCheckbox(){
       clickElem(this.getCurrentDriver(), checkbox,0, "Ticket checkbox");
    }

    public String getName(){
        //WebElement name = baseWebElem.findElement(By.cssSelector(chatConsoleInboxRowNameCss));
        return  userName.getText();
    }

    public String getCurrentAgent(){
        return getTextFromElem(this.getCurrentDriver(), currentAgent, 5, "Current agent");
    }

    public LocalDateTime getStartDate(){
        wheelScrollDownToElement(this.getCurrentDriver(),
                findElemByCSS(this.getCurrentDriver(), scrollAreaCss), startDate, 3);
        String stringDate = getTextFromElem(this.getCurrentDriver(), startDate, 5, "Date cell").trim() + " " + LocalDateTime.now().getYear();
        return LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern("d, MMM, HH:mm yyyy", Locale.US));
    }

    public LocalDateTime getEndDate(){
        wheelScrollDownToElement(this.getCurrentDriver(),
                findElemByCSS(this.getCurrentDriver(), scrollAreaCss), endDate, 3);
        String stringDate = getTextFromElem(this.getCurrentDriver(), endDate, 5, "Date cell").trim() + " " + LocalDateTime.now().getYear();
        return LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern("dd, MMM, HH:mm yyyy", Locale.US));
    }

    public void clickOnUserName(){
        clickElem(this.getCurrentDriver(), userName, 5, "User Name");
    }

    public String getLocation(){
        return  getTextFromElem(this.getCurrentDriver(), location, 2, "Location");
    }

    public String getStatus(){
        return  getTextFromElem(this.getCurrentDriver(), status, 2, "Status");
    }

    public String getPhone(){
        return  getTextFromElem(this.getCurrentDriver(), phone, 2, "Phone");
    }

    public String getIconName() {
        return channelIcon.getAttribute("name").trim();
    }
}