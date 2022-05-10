package agentpages.survey.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(xpath = "//div[@class = 'setting-group' and .//h3[text()='Web Chat']]")
public class SurveyWebChatForm extends AbstractUIElement {

    @FindBy(xpath = ".//button[text()='Save configuration']")
    private WebElement saveSurveyButton;

    @FindBy(css = ".rate-input label")
    private List<WebElement> rateInputNumbers;

    @FindBy(css = "label .icon.svg-icon-staricon")
    private List<WebElement> starRateRepresentation;

    @FindBy(css = "label [class^='icon svg-icon-smile']")
    private List<WebElement> smileRateRepresentation;

    @FindBy(css = ".rate-input-label.number")
    private List<WebElement> numberRateRepresentation;

    @FindBy(css = ".ctl-rate-textarea-title")
    private WebElement commentFieldHeader;

    @FindBy(css = ".ctl-rate-input-title")
    private WebElement questionPreview;

    private SurveysInner surveysInner;

    public SurveysInner getSurveysInner() {
        surveysInner.setCurrentDriver(this.getCurrentDriver());
        return surveysInner;
    }

    public void clickSaveButton() {
        clickElem(this.getCurrentDriver(), saveSurveyButton, 2, "Save Survey Button");
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

    public boolean isCommentFieldShown() {
        return isElementShown(this.getCurrentDriver(), commentFieldHeader, 2);
    }

    public String getPreviewQuestion() {
        return getTextFromElem(this.getCurrentDriver(), questionPreview, 1, "Question Preview");
    }

}
