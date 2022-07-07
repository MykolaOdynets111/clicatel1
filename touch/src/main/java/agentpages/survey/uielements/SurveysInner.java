package agentpages.survey.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".surveys-inner")
public class SurveysInner extends AbstractUIElement {
    @FindBy(xpath = ".//label[contains(text(), 'CSAT')]/span[@class='cl-radio__checkmark']")
    private WebElement csatRadioButton;

    @FindBy(xpath = ".//label[contains(text(), 'NPS')]/span[@class='cl-radio__checkmark']")
    private WebElement npsRadioButton;

    @FindBy(css = "div.cl-select__indicators.css-1wy0on6")
    private WebElement ratingNumbersDropdown;

    @FindBy(css = "div[id^='react-select']")
    private List<WebElement> ratingNumbersVariation;

    @FindBy(css = ".number-block")
    private WebElement numberButton;

    @FindBy(css = "[data-selenium-id='sentiment-positive']")
    private WebElement smileButton;

    @FindBy(name = "star")
    private WebElement starButton;

    @FindBy(xpath = "//div[text()='Smiles and stars available only in CSAT mode']")
    private WebElement ratingTypesUnavailableMessage;

    @FindBy(name = "surveyQuestionTitle")
    private WebElement questionInput;

    @FindBy(xpath = "//span[text()='Prompt customer to leave a note:']/following-sibling::label")
    private WebElement commentSwitcher;

    @FindBy(name = "ratingThanksMessage")
    private WebElement thankMessageForm;

    public SurveysInner clickCSATRadioButton() {
        clickElem(this.getCurrentDriver(), csatRadioButton, 2, "CSAT radio button");
        return this;
    }

    public SurveysInner clickNPSRadioButton() {
        clickElem(this.getCurrentDriver(), npsRadioButton, 2, "NPS radio button");
        return this;
    }

    public void clickCommentSwitcher() {
        clickElem(this.getCurrentDriver(), commentSwitcher, 2, "Comment switcher");
    }

    public void setThankMessage(String message) {
        inputText(this.getCurrentDriver(), thankMessageForm, 1, "Question Input", message);
    }

    public void changeQuestion(String guestion) {
        questionInput.clear();
        inputText(this.getCurrentDriver(), questionInput, 1, "Question Input", guestion);
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
        waitForElementToBeVisible(this.getCurrentDriver(), ratingNumbersDropdown, 8);
        clickElem(this.getCurrentDriver(), ratingNumbersDropdown, 20, "Rating number dropdown");
        waitForElementsToBeVisible(this.getCurrentDriver(), ratingNumbersVariation, 8);
        ratingNumbersVariation.stream().filter(e -> e.getText().equals(number)).findFirst()
                .orElseThrow(() -> new AssertionError(number + " number was not found in dropdown.")).click();
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
}
