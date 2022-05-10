package agentpages.survey.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(xpath = ".//div[@class = 'setting-group' and .//h3[text()='Apple Business Chat']]")
public class SurveyAbcForm extends AbstractUIElement {

    @FindBy(css = ".cl-preview-box .survey-form-title")
    private WebElement surveyPreviewTitle;

    @FindBy(css = ".preview-message")
    private List<WebElement> previewMessage;

    @FindBy(xpath = ".//button[text()='Save configuration']")
    private WebElement saveSurveyButton;

    private SurveysInner surveysInner;

    public SurveysInner getSurveysInner() {
        surveysInner.setCurrentDriver(this.getCurrentDriver());
        return surveysInner;
    }

    public String getSurveyPreviewTitle() {
        return getTextFromElem(this.getCurrentDriver(), surveyPreviewTitle, 3, "Survey preview title");
    }

    public List<String> getAllMessagesInSurveyPreview() {
        return previewMessage.stream()
                .map(message -> getTextFromElem(this.getCurrentDriver(), message, 3, "Survey preview message"))
                .collect(Collectors.toList());
    }

    public void clickSaveButton() {
        clickElem(this.getCurrentDriver(), saveSurveyButton, 2, "Save Survey Button");
    }
}
