package agentpages.tickets;


import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static datetimeutils.DateTimeHelper.parseDate;

@FindBy(css = ".extended-chat-list-item")
public class TicketRow extends AbstractWidget {

    @FindBy(css = ".cl-checkbox")
    private WebElement checkbox;

    @FindBy(css = ".[type='checkbox']")
    private WebElement checkboxStatus;

    @FindBy(css = ".cl-agent-name")
    private WebElement currentAgent;

    @FindBy(css = ".user-details__name")
    private WebElement userName;

    @FindBy(css = ".cl-table-user-description__location")
    private WebElement location;

    @FindBy(xpath = ".//button[text() = 'Close']")
    private WebElement closeTicketButton;

    @FindBy(css = ".cl-light-grey span")
    private WebElement status;

    @FindBy(xpath = ".//div[@class = 'cl-table-user-data__description']/div[2]")
    private WebElement phone;

    @FindBy(css = ".cl-table-cell--ticketCreatedDate")
    private WebElement startDate;

    @FindBy(css = ".cl-table-cell--endedDate")
    private WebElement endDate;

    @FindBy(css = ".cl-table-cell--channelType svg")
    private WebElement channelImg;

    @FindBy(xpath = ".//a[contains(text(), 'Open')]")
    private List<WebElement> openBtns;

    @FindBy(xpath = ".//button[contains(text(), 'Accept')]")
    private List<WebElement> acceptBtns;

    @FindBy(xpath = ".//button[contains(text(), 'Assign')]")
    private WebElement assignButton;

    private static String checkboxStatusString = "[type='checkbox']";

    public TicketRow(WebElement element) {
        super(element);
    }

    public TicketRow setCurrentDriver(WebDriver currentDriver) {
        this.currentDriver = currentDriver;
        return this;
    }

    public void selectCheckbox() {
        clickElem(this.getCurrentDriver(), checkbox, 0, "Ticket checkbox");
    }

    public boolean getCheckboxStatus() {
        return Boolean.parseBoolean(getAttributeFromElem(this.getCurrentDriver(), checkboxStatusString, 8,
                "Checkbox ticket row", "aria-checked"));
    }

    public String getName() {
        return getTextFromElem(this.getCurrentDriver(), userName, 10, "User name field");
    }

    public String getCurrentAgent() {
        return getTextFromElem(this.getCurrentDriver(), currentAgent, 5, "Current agent");
    }

    public String getCurrentChannel() {
        return getAttributeFromElem(this.getCurrentDriver(), channelImg, 5, "Current channel", "name");
    }

    public LocalDateTime getOpenDate() {
        String stringDate = getTextFromElem(this.getCurrentDriver(), startDate, 5, "Date cell").trim();
        return parseDate(stringDate);
    }

    public String getOpenDateText() {
        return getTextFromElem(this.getCurrentDriver(), startDate, 5, "Start Date cell").trim();
    }

    public String getEndDateText() {
        return getTextFromElem(this.getCurrentDriver(), endDate, 5, "End Date cell").trim();
    }

    public String getDateByName(String dateType) {
        String dateText = null;
        if (dateType.equalsIgnoreCase("start date")) {
            dateText = getOpenDateText();
        } else if (dateType.equalsIgnoreCase("end date")) {
            dateText = getEndDateText();
        }
        return dateText;
    }

    public LocalDateTime getEndDate() {
        String stringDate = getTextFromElem(this.getCurrentDriver(), endDate, 5, "Date cell").trim();
        return parseDate(stringDate);
    }

    public void clickOnUserName() {
        clickElem(this.getCurrentDriver(), userName, 5, "User Name");
    }

    public void hoverOnUserName() {
        hoverElem(this.getCurrentDriver(), userName, 5, "User Name");
    }

    public String getStatus() {
        return getTextFromElem(this.getCurrentDriver(), status, 2, "Status");
    }

    public String getPhone() {
        return getTextFromElem(this.getCurrentDriver(), phone, 2, "Phone");
    }

    public boolean isValidChannelImg(String channelPictureName) {
        File image = new File(System.getProperty("user.dir") + "/src/test/resources/adaptericons/" + channelPictureName + ".png");
        return isWebElementEqualsImage(this.getCurrentDriver(), channelImg, image);
    }

    public void openTicket(int ticketNum) {
        clickElem(this.getCurrentDriver(), openBtns.get(ticketNum - 1), 5, "openTicketBtn");
    }

    public void acceptTicket(int ticketNum) {
        clickElem(this.getCurrentDriver(), acceptBtns.get(ticketNum - 1), 5, "Accept Ticket Btn");
    }

    public boolean assignManualButtonVisibility() {
        return isElementRemoved(this.getCurrentDriver(), assignButton, 5);
    }

    public void clickCloseButton() {
        clickElem(this.getCurrentDriver(), closeTicketButton, 5, "Close ticket button");
    }
}