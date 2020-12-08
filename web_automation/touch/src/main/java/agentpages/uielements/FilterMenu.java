package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import agentpages.supervisor.uielements.SupervisorDeskHeader;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.util.List;

@FindBy(css = ".cl-r-roster-filter.cl-r-roster-filter--form-opened")
public class FilterMenu extends AbstractUIElement {

    @FindBy (xpath = ".//span[contains(text(), 'Flagged')]")
    private WebElement flaggedCheckbox;

    @FindBy(css ="[selenium-id=apply-filter-btn]")
    private WebElement applyFiltersButton;

    @FindBy (xpath = ".//label[text()='Channel']/parent::div//div[contains(@class, 'cl-r-select__control')]//input")
    private WebElement channelInput;

    @FindBy (xpath = ".//label[text()='Sentiment']/parent::div//div[contains(@class, 'cl-r-select__control')]//input")
    private WebElement sentimentsInput;

    @FindBy(xpath = ".//label[text()='Channel']/parent::div//*[contains(@class, 'cl-r-icon-arrow-up')]")
    private WebElement ChanelArrow;

    @FindBy(xpath = ".//label[text()='Channel']/parent::div//div[@class='css-xb97g8 cl-r-select__multi-value__remove']/*")
    private List<WebElement> chanelRemoveButtons;

    @FindBy(xpath = ".//label[text()='Sentiment']/parent::div//div[@class='css-xb97g8 cl-r-select__multi-value__remove']/*")
    private List<WebElement> sentimentRemoveButtons;

    @FindBy(name = "startDate")
    private WebElement startDateInput;

    @FindBy(name = "endDate")
    private WebElement endDateInput;

    public FilterMenu selectFlaggedCheckbox(){
        clickElem(this.getCurrentDriver(), flaggedCheckbox, 1, "Flagged checkbox");
        return this;
    }

    public void clickApplyButton(){
        clickElem(this.getCurrentDriver(), applyFiltersButton, 3, "Apply Filters Button");
    }

    public void removeChannelsIfWereSelected(){
        if(!chanelRemoveButtons.isEmpty()){
            for (WebElement remove : chanelRemoveButtons){
                    clickElem(this.getCurrentDriver(), remove, 1, "Chanel Remove Button");
            }
        }
    }

    public void fillChannelInputField(String chanel){
        inputText(this.getCurrentDriver(), channelInput, 1, "Chanel Input", chanel);
        channelInput.sendKeys(Keys.ENTER);
    }

    public void removeSentimentsIfWereSelected(){
        if(!sentimentRemoveButtons.isEmpty()){
            for (WebElement remove : sentimentRemoveButtons){
                clickElem(this.getCurrentDriver(), remove, 1, "Sentiment Remove Button");
            }
        }
    }

    public void fillSentimentsInputField(String sentiment){
        inputText(this.getCurrentDriver(), sentimentsInput, 1, "Sentiments Input", sentiment);
        sentimentsInput.sendKeys(Keys.ENTER);
    }

    public void fillStartDate(LocalDate startDate) {
        clickElem(this.getCurrentDriver(), startDateInput, 1, "Start Date Input");
        //10 is the length of date in format yyyy-mm-dd
        for (int i = 0; i < 10; i++) {
            if(getAttributeFromElem(getCurrentDriver(), startDateInput, 1,
                    "Filter start date", "value").isEmpty())
                break;
            startDateInput.sendKeys("");
            startDateInput.sendKeys(Keys.BACK_SPACE);
        }
        startDateInput.sendKeys(startDate.toString());
        startDateInput.sendKeys(Keys.ENTER);
    }

    public void fillEndDate(LocalDate endDate) {
        clickElem(this.getCurrentDriver(), endDateInput, 1, "Start Date Input");
        //10 is the length of date in format yyyy-mm-dd
        for (int i = 0; i < 10; i++) {
            if(getAttributeFromElem(getCurrentDriver(), endDateInput, 1,
                    "Filter start date", "value").isEmpty())
                break;
            endDateInput.sendKeys("");
            endDateInput.sendKeys(Keys.BACK_SPACE);
        }
        endDateInput.sendKeys(endDate.toString());
        endDateInput.sendKeys(Keys.ENTER);
    }
}
