package agentpages.survey.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
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

    @FindBy(css = ".cl-charters-count")
    private WebElement questionTitleCharacterLimit;

    @FindBy(xpath = "//div[@data-testid='spinner']")
    private WebElement saveSurveySpinner;

    @FindBy(xpath = ".//label[contains(@for, 'thanksMessageEnabled')]//div")
    private WebElement toggleButtonThankMessage;

    @FindBy(xpath = ".//label[contains(@for, 'commentEnabled')]//div")
    private WebElement toggleButtonNotes;

    @FindBy(xpath = ".//div[@class='setting-group__collapse-wrapper']//div")
    private WebElement collapseChannelForm;

    @FindBy(xpath = ".//button[contains(@class, 'cl-expand-btn')]")
    private WebElement expandButtonElement;

    @FindBy(name = "surveyQuestionTitle")
    private WebElement questionInput;

    @FindBy(xpath = ".//textarea[contains(@id, 'thanksMessageEnabled')]")
    private WebElement thankMessageForm;

    @FindBy(xpath = ".//textarea[contains(@id, 'customerNoteTitle')]")
    private WebElement notesForm;

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

    @FindBy(xpath = ".//span[text()='Follow-up thank you message:']/following-sibling::label")
    private WebElement thankMessageSwitcher;

    @FindBy(xpath = ".//div[@class='surveys-inner']/div[contains(@class, 'form-content')]")
    private WebElement noteFormEnabled;

    @FindBy(xpath = ".//div[@class='surveys-inner']/div[contains(@class, 'form-content')][2]")
    private WebElement thankFormEnabled;

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

    @FindBy(css = ".cl-form-group__error-text")
    private WebElement errorMessage;

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

    public SurveyForm clickNotesToggle() {
        moveToElemAndClick(this.getCurrentDriver(), toggleButtonNotes);
        return this;
    }

    public void changeQuestion(String question) {
        handleQuestionFormClearing();
        inputText(this.getCurrentDriver(), questionInput, 1, "Question Input", question);
    }

    public void handleQuestionFormClearing() {
        for (int i = 0; i < 5; i++) {
            if (isElementShown(this.getCurrentDriver(), errorMessage, 5) &&
                    getTextFromElem(this.getCurrentDriver(), errorMessage, 5, "Error message").contains("Survey question text is required")) {
                break;
            }
            questionInput.sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
            waitFor(1000);
        }
    }

    public void changeQuestionEmoji(String question) {
        handleQuestionFormClearing();
        StringSelection stringSelection = new StringSelection(question);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        questionInput.sendKeys(Keys.chord(Keys.CONTROL, "V"));
    }

    public void setThankMessage(String message) {
        thankMessageForm.clear();
        inputText(this.getCurrentDriver(), thankMessageForm, 1, "Question Input", message);
    }

    public void setNotesMessage(String message) {
        notesForm.clear();
        inputText(this.getCurrentDriver(), notesForm, 4, "Notes form Input", message);
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
        if (isElementEnabled(this.getCurrentDriver(), saveButtonElement, 5)) {
            clickElem(this.getCurrentDriver(), saveButtonElement, 2, "Save Survey Button");
            waitForAppearAndDisappear(this.getCurrentDriver(), saveSurveySpinner, 3, 4);
        } else {
            System.out.println("Value is already saved");
        }
    }

    public String checkInputQuestionCharacterCount() {
        return getTextFromElem(this.getCurrentDriver(), questionTitleCharacterLimit, 5, "Question Input character limit title");
    }

    public boolean checkUpperQuestionCharactersLimit(int expectedValue) {
        String actualText = getTextFromElem(this.getCurrentDriver(), questionTitleCharacterLimit, 5, "Question Input character limit title");
        String[] splitArray = actualText.split(" ");
        int upperLimit = Integer.parseInt(splitArray[0]);
        return upperLimit > expectedValue;
    }

    public boolean checkCharacterCountCompWithEnteredText() {
        String actualText = getTextFromElem(this.getCurrentDriver(), questionTitleCharacterLimit, 5, "Question Input character limit title");
        String expectedTextSize = String.valueOf(getTextFromElem(this.getCurrentDriver(), questionInput, 5, "Question Input").length());
        return actualText.contains(expectedTextSize);
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

    public void clickThankMessageSwitcher() {
        if (thankFormEnabled.getAttribute("class").contains("disabled")) {
            clickElem(this.getCurrentDriver(), thankMessageSwitcher, 2, "Thank message form switcher");
        } else {
            System.out.println("Thank Message toggle is already clicked");
        }
    }

    public String getSurveyPreviewTitle(){
        return getTextFromElem(this.getCurrentDriver(), surveyPreviewTitle, 4,
                "Survey preview header");
    }

    public boolean checkThankFormStatus() {
        return thankFormEnabled.getAttribute("class").contains("disabled");
    }

    public boolean checkNotesFormStatus() {
        return noteFormEnabled.getAttribute("class").contains("disabled");
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

    public boolean isErrorMessageShown() {
        return isElementShown(this.getCurrentDriver(), errorMessage, 2);
    }

    public boolean isErrorMessageHavingText(String expectedText) {
        String actualText = getTextFromElem(this.getCurrentDriver(), errorMessage, 5, "Error message");
        return actualText.equalsIgnoreCase(expectedText);
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