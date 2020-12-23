package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.util.List;

@FindBy(xpath = "//*[contains(@class, 'cl-r-roster-filter--form-opened') or contains(@class, 'search-filter-bar-agent')]")
public class FilterMenu extends AbstractUIElement {

    private final String channelInputByName = ".//label[text()='Channel']/parent::div//div[contains(@class, 'cl-r-select__menu-list')]/div[text()='%s']";

    @FindBy(xpath = ".//span[contains(text(), 'Flagged')]")
    private WebElement flaggedCheckbox;

    @FindBy(xpath = ".//button[contains(text(),'Apply Filters')]")
    private WebElement applyFiltersButton;

    @FindBy(xpath = ".//label[text()='Channel']/parent::div//div[contains(@class, 'cl-r-select__control')]//input")
    private WebElement channelInput;

    @FindBy(xpath = ".//label[text()='Channel']/parent::div//div[contains(@class, 'cl-r-select__indicators')]")
    private WebElement channelDeploy;

    @FindBy(xpath = ".//label[text()='Sentiment']/parent::div//div[contains(@class, 'cl-r-select__control')]//input")
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

    public FilterMenu selectFlaggedCheckbox() {
        clickElem(this.getCurrentDriver(), flaggedCheckbox, 1, "Flagged checkbox");
        return this;
    }

    public void clickApplyButton() {
        clickElem(this.getCurrentDriver(), applyFiltersButton, 3, "Apply Filters Button");
    }

    public void removeChannelsIfWereSelected() {
        if (!chanelRemoveButtons.isEmpty()) {
            for (WebElement remove : chanelRemoveButtons) {
                clickElem(this.getCurrentDriver(), remove, 1, "Chanel Remove Button");
            }
        }
    }

    public void deployChannels() {
        clickElem(this.getCurrentDriver(), channelDeploy, 3, "Channels deploy button");
    }

    public void chooseChannel(String channel) {
        deployChannels();
        clickElemByXpath(this.currentDriver, String.format(channelInputByName, channel), 2, "Channel input");
    }

    public void removeSentimentsIfWereSelected() {
        if (!sentimentRemoveButtons.isEmpty()) {
            for (WebElement remove : sentimentRemoveButtons) {
                clickElem(this.getCurrentDriver(), remove, 1, "Sentiment Remove Button");
            }
        }
    }

    public void fillSentimentsInputField(String sentiment) {
        inputText(this.getCurrentDriver(), sentimentsInput, 1, "Sentiments Input", sentiment);
        sentimentsInput.sendKeys(Keys.ENTER);
    }

    public void fillStartDate(LocalDate startDate) {
        fillDateInput(this.getCurrentDriver(), startDateInput, startDate, 1, "Start date");
    }

    public void fillEndDate(LocalDate endDate) {
        fillDateInput(this.getCurrentDriver(), endDateInput, endDate, 1, "End date");
    }
}
