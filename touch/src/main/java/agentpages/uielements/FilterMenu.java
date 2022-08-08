package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@FindBy(xpath = "//*[contains(@class, 'cl-r-roster-filter--form-opened') or contains(@class, 'search-filter-bar-agent')]")
public class FilterMenu extends AbstractUIElement {

    private final String channelInputByName = ".//div[text()='%s']";

    @FindBy(xpath = ".//span[contains(text(), 'Flagged')]")
    private WebElement flaggedCheckbox;

    @FindBy(xpath = ".//button[contains(text(),'Apply Filters')]")
    private WebElement applyFiltersButton;

    @FindBy(xpath = "//button[contains(@data-testid, 'close-filter-tab-btn')]")
    private WebElement closeFilterButton;

    @FindBy(xpath = ".//label[text()='Channel']/parent::div//div[contains(@class, 'cl-r-select__control')]//input")
    private WebElement channelInput;

    @FindBy(xpath = ".//label[text()='Channel']/parent::div//div[contains(@class, 'cl-select__indicators')]")
    private WebElement channelExpandColapsButton;

    @FindBy(css="div[class^='cl-select__menu-list'] div[id^='react-select']")
    private List<WebElement> dropdownValues;

    @FindBy(xpath = ".//label[text()='Sentiment']/parent::div//div[contains(@class, 'cl-select__indicators')]")
    private WebElement sentimentsExpandColapsButton;

    @FindBy(xpath = ".//label[text()='Sentiment']/parent::div//div[contains(@class, 'cl-r-select__control')]//input")
    private WebElement sentimentsInput;

    @FindBy(xpath = ".//label[text()='Channel']/parent::div//*[contains(@class, 'cl-r-icon-arrow-up')]")
    private WebElement ChanelArrow;

    @FindBy(xpath = ".//label[text()='Channel']/parent::div//div[@class='css-xb97g8 cl-r-select__multi-value__remove']/*")
    private List<WebElement> chanelRemoveButtons;

    @FindBy(xpath = ".//label[text()='Sentiment']/parent::div//div[@class='css-xb97g8 cl-r-select__multi-value__remove']/*")
    private List<WebElement> sentimentRemoveButtons;

    @FindBy(css = "[class='cl-form-control cl-form-control--input']")
    private WebElement startDateInput;

    @FindBy(css = "[class='cl-form-control cl-form-control--input react-datepicker-ignore-onclickoutside']")
    private WebElement startDateInputCalendar;
    @FindBy(xpath = "//input[contains(@class, 'cl-form-control cl-form-control--input cl-end-date')]")
    private WebElement endDateInput;

    public FilterMenu selectFlaggedCheckbox() {
        clickElem(this.getCurrentDriver(), flaggedCheckbox, 1, "Flagged checkbox");
        return this;
    }

    public void clickApplyButton() {
        clickElem(this.getCurrentDriver(), applyFiltersButton, 3, "Apply Filters Button");
    }

    public void clickCloseFiltersButton() {
        clickElem(this.getCurrentDriver(), closeFilterButton, 3, "Close Filters Button");
    }

    public FilterMenu expandChannels() {
        clickElem(this.getCurrentDriver(), channelExpandColapsButton, 3, "Channels expand button");
        return this;
    }

    public FilterMenu expandSentiment() {
        clickElem(this.getCurrentDriver(), sentimentsExpandColapsButton, 3, "Sentiment expand button");
        return this;
    }


    public List<String> getDropdownOptions(){
        return dropdownValues.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }

    public void chooseChannel(String channel) {
        expandChannels();
        clickElemByXpath(this.currentDriver, String.format(channelInputByName, channel), 2, "Channel input");
    }

    public void fillSentimentsInputField(String sentiment) {
        inputText(this.getCurrentDriver(), sentimentsInput, 1, "Sentiments Input", sentiment);
        sentimentsInput.sendKeys(Keys.ENTER);
    }

    public boolean isStartDateIsPresent(){
        return isElementShown(this.getCurrentDriver(), startDateInput, 1);
    }

    public boolean isEndDateIsPresent(){
        return isElementShown(this.getCurrentDriver(), endDateInput, 1);
    }

    public String isStartDateIsEmpty(){
        String value = getAttributeFromElem(this.getCurrentDriver(), startDateInput, 2, "Start Date Element", "value");
        clickCloseFiltersButton();
        return value;
    }

    public void fillStartDate(LocalDate startDate) {
        fillDateInput(this.getCurrentDriver(), startDateInput, startDateInputCalendar, startDate, 1, "Start date");
    }

    public void fillEndDate(LocalDate endDate) {
        fillDateInput(this.getCurrentDriver(), endDateInput, endDate, 1, "End date");
    }
}
