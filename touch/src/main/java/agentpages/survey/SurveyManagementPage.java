package agentpages.survey;

import agentpages.survey.uielements.SurveyWAForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.PortalAbstractPage;
import agentpages.survey.uielements.SurveyAbcForm;
import agentpages.survey.uielements.SurveyWebChatForm;

public class SurveyManagementPage extends PortalAbstractPage {

    @FindBy(xpath = "//h2[contains(text(), 'Customer Surveys Customization')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//div[text()='You have successfully saved your survey']")
    private WebElement saveMessage;

    @FindBy(css = ".svg-icon-whatsappwhite")
    private WebElement whatsappTab;

    private SurveyWebChatForm surveyWebChatForm;
    private SurveyAbcForm surveyAbcForm;
    private SurveyWAForm surveyWAForm;

    public SurveyWebChatForm getSurveyWebChatForm(){
        surveyWebChatForm.setCurrentDriver(this.getCurrentDriver());
        return surveyWebChatForm;
    }

    public SurveyAbcForm getSurveyAbcForm() {
        surveyAbcForm.setCurrentDriver(this.getCurrentDriver());
        return surveyAbcForm;
    }

    public SurveyWAForm getSurveyWAForm() {
        surveyWAForm.setCurrentDriver(this.getCurrentDriver());
        return surveyWAForm;
    }

    public SurveyManagementPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSurveyManagementPage() {
        return isElementShown(this.getCurrentDriver(), pageTitle, 40);
    }

    public void switchToWhatsappTab(){
        clickElem(this.getCurrentDriver(), whatsappTab, 1, "Whatsapp tab");
    }

    public void waitSaveMessage(){
        waitForElementToBeVisible(this.getCurrentDriver(), saveMessage, 5);
        waitUntilElementNotDisplayed(this.getCurrentDriver(), saveMessage, 5);
    }
}
