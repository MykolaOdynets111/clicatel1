package agentpages.survey.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SurveyForm extends AbstractWidget {

    @FindBy(xpath = ".//div[contains(@class, 'setting-group__secondary-control--toggle')]//label")
    private WebElement toggleButtons;

    @FindBy(xpath = ".//fieldset[contains(@class, 'surveys-fieldset')]")
    private WebElement formEnabled;

    @FindBy(css = ".cl-preview-box .survey-form-title")
    private WebElement surveyPreviewTitle;

    @FindBy(css = ".preview-message.preview-message--agent-message")
    private List<WebElement> previewMessage;

    @FindBy(xpath = ".//button[text()='Save configuration']")
    private WebElement saveSurveyButton;

    @FindBy(xpath = ".//label[contains(text(), 'CSAT')]")
    private WebElement csatRadioButton;

    @FindBy(xpath = ".//label[contains(text(), 'NPS')]")
    private WebElement npsRadioButton;

    @FindBy(xpath = ".//div[@class='cl-select__indicators css-1wy0on6']")
    private WebElement ratingNumbersDropdown;

    @FindBy(xpath = ".//div[contains(@id, 'react-select')]")
    private List<WebElement> ratingNumbersVariation;

    @FindBy(xpath = ".//button[text()='Save configuration']")
    private WebElement saveButtonElement;

    @FindBy(xpath = ".//div[@class='setting-group__collapse-wrapper']//div")
    private WebElement collapseChannelForm;

    @FindBy(xpath = ".//button[contains(@class, 'cl-expand-btn')]")
    private WebElement expandButtonElement;

    @FindBy(name = "surveyQuestionTitle")
    private WebElement questionInput;

    @FindBy(name = "ratingThanksMessage")
    private WebElement thankMessageForm;

    public SurveyForm(WebElement element) {
        super(element);
    }

    public SurveyForm setCurrentDriver(WebDriver currentDriver) {
        this.currentDriver = currentDriver;
        return this;
    }

    public SurveyForm clickToggleButton() {
        waitFor(1000);
        if (formEnabled.getAttribute("disabled") == null) {
            System.out.println("Toggle button is already clicked");
        } else {
            //WebElement toggleButton = toggleButtons.stream().filter(toggle -> toggle.getAttribute("for").contains(channelId)).findFirst().get();
            moveToElemAndClick(this.getCurrentDriver(), toggleButtons);
        }
        return this;
    }

    public SurveyForm clickExpandButton(String channelId) {
        if (collapseChannelForm.getAttribute("aria-hidden").trim().contains("false")) {
            System.out.println("Channel is already expanded");
        } else {
            moveToElemAndClick(this.getCurrentDriver(), expandButtonElement);
        }
        return this;
    }

    public void changeQuestion(String question) {
        questionInput.clear();
        inputText(this.getCurrentDriver(), questionInput, 1, "Question Input", question);
    }

    public void setThankMessage(String message) {
        inputText(this.getCurrentDriver(), thankMessageForm, 1, "Question Input", message);
    }

    public SurveyForm clickCSATRadioButton() {
        clickElem(this.getCurrentDriver(), csatRadioButton, 5, "CSAT Radio");
        return this;
    }

    public SurveyForm clickNPSRadioButton() {
        clickElem(this.getCurrentDriver(), npsRadioButton, 5, "CSAT Radio");
        return this;
    }

    public String getSurveyPreviewTitle() {
        return getTextFromElem(this.getCurrentDriver(), surveyPreviewTitle, 3, "Survey preview title");
    }

    public List<String> getAllMessagesInSurveyPreview() {
        List<String> messages = new ArrayList<>();
        for (WebElement elem : previewMessage) {
            messages.add(elem.getText());
        }
        return messages;
    }

    public void clickSaveButton() {
        if (isElementEnabled(this.getCurrentDriver(), saveButtonElement, 2) == true) {
            clickElem(this.getCurrentDriver(), saveButtonElement, 2, "Save Survey Button");
        } else {
            System.out.println("Value is already saved");
        }
    }

    public List<String> getVariationOfRatingCSATScale() {
        List<String> ratingNums;
        clickElem(this.getCurrentDriver(), ratingNumbersDropdown, 2, "Rating number dropdown");
        waitForElementsToBeVisible(this.getCurrentDriver(), ratingNumbersVariation, 2);
        ratingNums = ratingNumbersVariation.stream().map(e -> e.getText()).collect(Collectors.toList());
        clickElem(this.getCurrentDriver(), ratingNumbersDropdown, 2, "Rating number dropdown");
        return ratingNums;
    }

    public void selectDropdownOption(String number) {
        clickElem(this.getCurrentDriver(), ratingNumbersDropdown, 20, "Rating number dropdown");

        waitFor(5000);
        ratingNumbersVariation.stream().filter(e -> e.getText().trim().equals(number)).findFirst()
                .orElseThrow(() -> new AssertionError(number + " number was not found in dropdown.")).click();
    }

}