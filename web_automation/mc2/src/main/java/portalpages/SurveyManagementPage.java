package portalpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.SurveyWebChatForm;

public class SurveyManagementPage extends PortalAbstractPage{

    @FindBy(xpath = "//h2[contains(text(), 'Customer Surveys Customization')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//div[text()='You have successfully saved your survey']")
    private WebElement saveMessage;

    @FindBy(css = ".svg-icon-whatsappwhite")
    private WebElement whatsappTab;

    private SurveyWebChatForm surveyWebChatForm;

    public SurveyWebChatForm getSurveyWebChatForm(){
        surveyWebChatForm.setCurrentDriver(this.getCurrentDriver());
        return surveyWebChatForm;
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
