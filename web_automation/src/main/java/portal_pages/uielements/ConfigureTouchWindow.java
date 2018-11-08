package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import driverManager.DriverFactory;
import org.openqa.selenium.TimeoutException;
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
    private List<WebElement> industriesAndCountriesChoicesList;

    @FindBy(css = "button.button.button-primary.ng-scope")
    private WebElement nextButton;

    @FindBy(name = "transcriptsEmail")
    private WebElement transcriptsEmailInput;

    public void createNewTenant(String tenantOrgName, String transcriptsEmail){
        waitForElementToBeVisibleAgent(businessNameInput, 3).sendKeys(tenantOrgName);
        selectIndustryField.click();
        int randomIndustryNumber = ThreadLocalRandom.current().nextInt(0, industriesAndCountriesChoicesList.size() - 1);
        industriesAndCountriesChoicesList.get(randomIndustryNumber).click();
        selectCountryField.click();
        int randomCountryNumber = ThreadLocalRandom.current().nextInt(0, industriesAndCountriesChoicesList.size() - 1);
        industriesAndCountriesChoicesList.get(randomCountryNumber).click();
        nextButton.click();
        nextButton.click();
        transcriptsEmailInput.sendKeys(transcriptsEmail);
        nextButton.click();
    }
}
