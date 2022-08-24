package agentpages.survey.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.TimeoutException;
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

    @FindBy(xpath = ".//label[contains(@for, 'thanksMessageEnabled')]//div")
    private WebElement toggleButtonThankMessage;

    @FindBy(xpath = ".//div[@class='setting-group__collapse-wrapper']//div")
    private WebElement collapseChannelForm;

    @FindBy(xpath = ".//button[contains(@class, 'cl-expand-btn')]")
    private WebElement expandButtonElement;

    @FindBy(name = "surveyQuestionTitle")
    private WebElement questionInput;

    @FindBy(name = "ratingThanksMessage")
    private WebElement thankMessageForm;

    @FindBy(css = ".number-block")
    private WebElement numberButton;

    @FindBy(css = "[data-selenium-id='sentiment-positive']")
    private WebElement smileButton;

    @FindBy(name = "star")
    private WebElement starButton;

    @FindBy(xpath = "//div[text()='Smiles and stars available only in CSAT mode']")
    private WebElement ratingTypesUnavailableMessage;

    @FindBy(xpath = ".//span[text()='Prompt customer to leave a note:']/following-sibling::label")
    private WebElement commentSwitcher;

    @FindBy(xpath = ".//div[@class='surveys-inner']/div[contains(@class, 'form-content')]")
    private WebElement noteFormEnabled;

    @FindBy(xpath = ".//textarea[@name='customerNoteTitle']")
    private WebElement commentFieldHeader;

    @FindBy(css = ".rate-input label")
    private List<WebElement> rateInputNumbers;

    @FindBy(css = "label .icon.svg-icon-staricon")
    private List<WebElement> starRateRepresentation;

    @FindBy(css = "label [class^='icon svg-icon-smile']")
    private List<WebElement> smileRateRepresentation;

    @FindBy(css = ".rate-input-label.number")
    private List<WebElement> numberRateRepresentation;

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

    public SurveyForm clickThankMessageToggle() {
        moveToElemAndClick(this.getCurrentDriver(), toggleButtonThankMessage);
        return this;
    }

    public void changeQuestion(String question) {
        inputText(this.getCurrentDriver(), questionInput, 1, "Question Input", question);
    }

    public void setThankMessage(String message) {
        thankMessageForm.clear();
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

    public List<String> getAllMessagesInSurveyPreview() {
        List<String> messages = new ArrayList<>();
        for (WebElement elem : previewMessage) {
            messages.add(elem.getText());
        }
        return messages;
    }

    public void clickSaveButton() {
        if (isElementEnabled(this.getCurrentDriver(), saveButtonElement, 2)) {
            clickElem(this.getCurrentDriver(), saveButtonElement, 2, "Save Survey Button");
            if (isElementEnabled(this.getCurrentDriver(), saveButtonElement, 2)) {
                waitFor(5000);
            }
            else{
                System.out.println("Button is successfully clicked and value is correctly saved");
            }
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

    public boolean checkRatingDropdownVisibility() {
        boolean flag = false;
        try {
            if (waitForElementToBeVisible(this.getCurrentDriver(), ratingNumbersDropdown, 5).isDisplayed()) {
                flag = false;
            }
        } catch (TimeoutException e) {
            flag = true;
        }
        return flag;
    }

    public void clickCommentSwitcher() {
        if (noteFormEnabled.getAttribute("class").contains("disabled")) {
            clickElem(this.getCurrentDriver(), commentSwitcher, 2, "Comment switcher");
        } else {
            System.out.println("Comment switcher toggle is already clicked");
        }
    }

    public void selectRateIcon(String icon) {
        if (icon.equals("number")) {
            clickElem(this.getCurrentDriver(), numberButton, 2, "Number Button");
        } else if (icon.equals("star")) {
            clickElem(this.getCurrentDriver(), starButton, 2, "Star Button");
        } else if (icon.equals("smile")) {
            clickElem(this.getCurrentDriver(), smileButton, 2, "Smile Button");
        } else {
            throw new AssertionError("Incorrect Icon tipe was provided");
        }
    }

    public boolean isSmileButtonDisabled() {
        scrollToElem(this.getCurrentDriver(), smileButton, "Smile button");
        moveToElement(this.getCurrentDriver(), smileButton);
        return isElementShown(this.getCurrentDriver(), ratingTypesUnavailableMessage, 1);
    }

    public boolean isStarButtonDisabled() {
        scrollToElem(this.getCurrentDriver(), starButton, "Star button");
        moveToElement(this.getCurrentDriver(), starButton);
        return isElementShown(this.getCurrentDriver(), ratingTypesUnavailableMessage, 1);
    }

    public boolean isCommentFieldShown() {
        return isElementShown(this.getCurrentDriver(), commentFieldHeader, 2);
    }

    public String getSizeOfRateInputNumbers() {
        return rateInputNumbers.size() + "";
    }

    public boolean isRateHasCorrectIcons(String icon) {
        if (icon.equals("number")) {
            return !numberRateRepresentation.isEmpty();
        } else if (icon.equals("star")) {
            return !starRateRepresentation.isEmpty();
        } else if (icon.equals("smile")) {
            return !smileRateRepresentation.isEmpty();
        } else {
            new AssertionError("Incorrect Icon tipe was provided");
        }
        return false;
    }
}