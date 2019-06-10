package portalpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@FindBy(css = "div[ng-controller='SetupTenantCtrl']")
public class ConfigureTouchWindow extends AbstractUIElement {

    @FindBy(name = "businessName")
    private WebElement businessNameInput;

    @FindBy(css = "span[cl-options='businessTypes'] div.ui-select-match")
    private WebElement selectIndustryField;

    @FindBy(css = "div[ng-model-key=\"'country'\"] div.ui-select-match")
    private WebElement selectCountryField;

    @FindBy(css = "ul.ui-select-choices div.ui-select-choices-row")
    private List<WebElement> industriesChoicesList;

    @FindBy(css = "li#ui-select-choices-1 span.ui-select-choices-row-inner")
    private List<WebElement> countryChoicesList;

    @FindBy(css = "button.button.button-primary.ng-scope")
    private WebElement nextButton;

    @FindBy(name = "transcriptsEmail")
    private WebElement transcriptsEmailInput;

    public void createNewTenant(String tenantOrgName, String transcriptsEmail){
        waitForElementToBeVisibleAgent(businessNameInput, 3).sendKeys(tenantOrgName);
        selectIndustryField.click();
        int randomIndustryNumber = ThreadLocalRandom.current().nextInt(0, industriesChoicesList.size() - 1);
        industriesChoicesList.get(randomIndustryNumber).click();
        clickElemAgent(selectCountryField, 5, "admin", "Select country dropdown");
        int randomCountryNumber = ThreadLocalRandom.current().nextInt(0, countryChoicesList.size() - 1);
        countryChoicesList.get(randomCountryNumber).click();
        nextButton.click();
        nextButton.click();
        transcriptsEmailInput.sendKeys(transcriptsEmail);
        clickElemAgent(nextButton, 5, "admin", "Finish button");
    }
}
