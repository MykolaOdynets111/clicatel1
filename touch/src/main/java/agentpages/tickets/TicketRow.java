package agentpages.tickets;


import abstractclasses.AbstractWidget;
import datetimeutils.DateTimeHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


public class TicketRow extends AbstractWidget {

    @FindBy(css = ".cl-checkbox")
    private WebElement checkbox;

    @FindBy(css = ".cl-agent-name")
    private WebElement currentAgent;

    @FindBy(css = ".user-details__name")
    private WebElement userName;

    @FindBy(css = ".cl-table-user-description__location")
    private WebElement location;

    @FindBy(css = ".cl-light-grey span")
    private WebElement status;

    @FindBy(xpath = "//div[@class = 'cl-table-user-data__description']/div[2]")
    private WebElement phone;

    @FindBy(css = ".cl-table-cell--ticketCreatedDate")
    private WebElement startDate;

    @FindBy(css = ".cl-table-cell--endedDate")
    private WebElement endDate;

    @FindBy(css = ".cl-table-cell--channelType svg")
    private WebElement channelImg;

    @FindBy(xpath = ".//a[contains(text(), 'Open')]")
    private List<WebElement> openBtns;

    @FindBy(xpath = ".//button[contains(text(), 'Assign')]")
    private WebElement assignButton;

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

    public String getName() {
        return userName.getText();
    }

    public String getCurrentAgent() {
        return getTextFromElem(this.getCurrentDriver(), currentAgent, 5, "Current agent");
    }

    public String getCurrentChannel() {
        return getAttributeFromElem(this.getCurrentDriver(), channelImg, 5, "Current channel", "name");
    }

    public LocalDateTime getOpenDate() {
        String stringDate = getTextFromElem(this.getCurrentDriver(), startDate, 5, "Date cell").trim();
        return DateTimeHelper.parseDate(stringDate);
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
        return DateTimeHelper.parseDate(stringDate);
    }

    public void clickOnUserName() {
        clickElem(this.getCurrentDriver(), userName, 5, "User Name");
    }

    public void hoverOnUserName() {
        hoverElem(this.getCurrentDriver(), userName, 5, "User Name");
    }

    public String getLocation() {
        return getTextFromElem(this.getCurrentDriver(), location, 2, "Location");
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

    public boolean assignManualButtonVisibility() {
        return isElementRemoved(this.getCurrentDriver(), assignButton, 5);
    }
}
