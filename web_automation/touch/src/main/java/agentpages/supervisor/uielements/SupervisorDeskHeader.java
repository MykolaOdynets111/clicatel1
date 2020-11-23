package agentpages.supervisor.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.util.List;

@FindBy(css = ".supervisor-view-header")
public class SupervisorDeskHeader extends AbstractUIElement {

    @FindBy(id = "nameOrPhone")
    private WebElement searchInput;

    @FindBy(css = "[type='submit']")
    private WebElement applyFiltersButton;

    @FindBy(css = ".cl-r-checkbox__label")
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

    public void clickApplyFilterButton(){
        clickElem(this.getCurrentDriver(), applyFiltersButton, 1, "Apply Filters");
    }

    public SupervisorDeskHeader setSearchInput(String text){
        inputText(this.getCurrentDriver(), searchInput, 1, "Search input", text);
        return this;
    }

    public SupervisorDeskHeader clickFlaggedOnlyCheckbox(){
        clickElem(this.getCurrentDriver(), flaggedOnlyCheckbox, 1, "Flagged Only");
        return this;
    }

    public SupervisorDeskHeader selectChanel(String chanelName){
        clickElem(this.getCurrentDriver(), channelsDropdown, 1, "Channels Dropdown");
        dropdownOptions.stream().filter(e -> e.getText().equalsIgnoreCase(chanelName))
                .findFirst().orElseThrow(() ->
                        new AssertionError(chanelName + " chanel is not found")).click();
    return this;
    }

    public SupervisorDeskHeader selectSentiment(String sentimentName){
        clickElem(this.getCurrentDriver(), sentimentDropdown, 1, "Sentiment Dropdown");
        dropdownOptions.stream().filter(e -> e.getText().equalsIgnoreCase(sentimentName))
                .findFirst().orElseThrow(() ->
                new AssertionError(sentimentName + " sentiment is not found")).click();
        return this;
    }

    public SupervisorDeskHeader selectStartDate(LocalDate startDate) {
        clickElem(this.getCurrentDriver(), startDateInput, 1, "Start Date Input");
        while (!getAttributeFromElem(getCurrentDriver(), startDateInput, 1,
                "Filter start date", "value").isEmpty()) {
            startDateInput.sendKeys("");
            startDateInput.sendKeys(Keys.BACK_SPACE);
        }
        startDateInput.sendKeys(startDate.toString());
        startDateInput.sendKeys(Keys.ENTER);
        return this;
    }

    public SupervisorDeskHeader selectEndDate(LocalDate endDate) {
        clickElem(this.getCurrentDriver(), endDateInput, 1, "Start Date Input");
        while (!getAttributeFromElem(getCurrentDriver(), endDateInput, 1,
                "Filter start date", "value").isEmpty()) {
            endDateInput.sendKeys("");
            endDateInput.sendKeys(Keys.BACK_SPACE);
        }
        endDateInput.sendKeys(endDate.toString());
        endDateInput.sendKeys(Keys.ENTER);
        return this;
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
}
