package agentpages.commonelements;
import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".view-header")
public class SupervisorAndTicketsHeader extends AbstractUIElement {

    @FindBy(id = "nameOrPhone")
    private WebElement searchInput;

    @FindBy(css = "[type='submit']")
    private WebElement applyFiltersButton;

    @FindBy(xpath = "//*[text()='Flagged Only']")
    private WebElement flaggedOnlyCheckbox;

    @FindBy(id = "channel")
    private WebElement channelsDropdown;

    @FindBy(id = "sentiments")
    private WebElement sentimentDropdown;

    @FindBy(id = "start_date")
    private WebElement startDateInput;

    @FindBy(id = "end_date")
    private WebElement endDateInput;

    @FindBy(xpath=".//div[contains(@id, 'react-select')]")
    private List<WebElement> dropdownOptions;

    @FindBy(css = "#channel .cl-r-select__placeholder")
    private WebElement channelFilterValue;

    @FindBy(css = "#sentiments .cl-r-select__placeholder")
    private WebElement sentimentsFilterValue;

    @FindBy(css="div[class^='cl-select__menu-list'] div[id^='react-select']")
    private List<WebElement> dropdownValues;

    public void clickApplyFilterButton(){
        scrollToElem(this.getCurrentDriver(), applyFiltersButton, "Apply Filters");
        clickElem(this.getCurrentDriver(), applyFiltersButton, 1, "Apply Filters");
    }

    public SupervisorAndTicketsHeader setSearchInput(String text){
        inputText(this.getCurrentDriver(), searchInput, 1, "Search input", text);
        return this;
    }

    public SupervisorAndTicketsHeader clickFlaggedOnlyCheckbox(){
        clickElem(this.getCurrentDriver(), flaggedOnlyCheckbox, 1, "Flagged Only");
        return this;
    }

    public SupervisorAndTicketsHeader selectChanel(String chanelName){
        clickElem(this.getCurrentDriver(), channelsDropdown, 1, "Channels Dropdown");
        dropdownOptions.stream().filter(e -> e.getText().equalsIgnoreCase(chanelName))
                .findFirst().orElseThrow(() ->
                        new AssertionError(chanelName + " chanel is not found")).click();
    return this;
    }

    public SupervisorAndTicketsHeader selectSentiment(String sentimentName){
        clickElem(this.getCurrentDriver(), sentimentDropdown, 4, "Sentiment Dropdown");
        dropdownOptions.stream().filter(e -> e.getText().equalsIgnoreCase(sentimentName))
                .findFirst().orElseThrow(() ->
                new AssertionError(sentimentName + " sentiment is not found")).click();
        return this;
    }

    public SupervisorAndTicketsHeader selectStartDate(LocalDate startDate) {
        fillDateInput(this.getCurrentDriver(), startDateInput, startDate, 1, "Start date");
        return this;
    }

    public SupervisorAndTicketsHeader selectEndDate(LocalDate endDate) {
        fillDateInput(this.getCurrentDriver(), endDateInput, endDate, 1, "End date");
        return this;
    }

    public String checkStartDateFilterIsEmpty() {
        return getAttributeFromElem(this.getCurrentDriver(), startDateInput, 2, "Start Date Element", "value");
    }

    public String getChannelFilterValue(){
        return getTextFromElem(this.getCurrentDriver(), channelFilterValue, 1, "Channels Filter Value");
    }

    public String getSentimentsFilterValue(){
        return getTextFromElem(this.getCurrentDriver(), sentimentsFilterValue, 1, "Sentiments Filter Value");
    }

    public void filterByOptions(String chatName, String chanellName, String sentimentName){
        if(!chatName.equalsIgnoreCase("no")){
            setSearchInput(chatName);
        }
        if(!chanellName.equalsIgnoreCase("no")){
            selectChanel(chanellName);
        }
        if(!sentimentName.equalsIgnoreCase("no")){
            selectSentiment(sentimentName);
        }
    }

    public LocalDate getStartDateFilterValue() {
        String stringDate = getAttributeFromElem(getCurrentDriver(), startDateInput, 1,
                "Filter start date", "value");
        return LocalDate.parse(stringDate);
    }

    public LocalDate getEndDateFilterValue() {
        String stringDate = getAttributeFromElem(getCurrentDriver(), endDateInput, 1,
                "Filter start date", "value");
        return LocalDate.parse(stringDate);
    }
    public List<String> getDropdownOptions(){
        return dropdownValues.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }
    public SupervisorAndTicketsHeader expandChannels() {
        clickElem(this.getCurrentDriver(), channelsDropdown, 3, "Channels expand button");
        return this;
    }
    public SupervisorAndTicketsHeader expandSentiments() {
        clickElem(this.getCurrentDriver(), sentimentDropdown, 3, "Channels expand button");
        return this;
    }

    public boolean isStartDateIsPresent(){ return isElementShown(this.getCurrentDriver(), startDateInput, 1);
    }

    public boolean isEndDateIsPresent(){
        return isElementShown(this.getCurrentDriver(), endDateInput, 1);
    }
}
