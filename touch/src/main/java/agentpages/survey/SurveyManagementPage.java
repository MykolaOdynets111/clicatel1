package agentpages.survey;

import agentpages.survey.uielements.SurveyForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.PortalAbstractPage;

public class SurveyManagementPage extends PortalAbstractPage {

    @FindBy(xpath = "//h2[contains(text(), 'Customer Surveys Customization')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//div[text()='You have successfully saved your survey']")
    private WebElement saveMessage;

    @FindBy(css = ".svg-icon-whatsappwhite")
    private WebElement whatsappTab;

    private String surveyFormMainElement = "//label[contains(@for, '%s')]//ancestor::div[@class='setting-group']";

    public SurveyForm getSurveyForm(String channelId) {
        waitFor(3000);
        return new SurveyForm(this.getCurrentDriver().findElement(By.xpath(String.format(surveyFormMainElement, channelId))))
                .setCurrentDriver(this.getCurrentDriver());
    }

    public SurveyManagementPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSurveyManagementPage() {
        return isElementShown(this.getCurrentDriver(), pageTitle, 40);
    }

    public void switchToWhatsappTab() {
        clickElem(this.getCurrentDriver(), whatsappTab, 1, "Whatsapp tab");
    }

}
