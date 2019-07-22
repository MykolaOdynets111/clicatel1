package portalpages.uielements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@FindBy(css = "div[ng-controller='SetupTenantCtrl']")
public class ConfigureTouchWindow extends BasePortalWindow {

    @FindBy(name = "businessName")
    private WebElement businessNameInput;

    @FindBy(css = "span[cl-options='businessTypes'] div.ui-select-match")
    private WebElement selectIndustryField;

    @FindBy(css = "div[placeholder='select country'] span[aria-label='Select box activate']>span.ui-select-placeholder")
    private WebElement selectCountryField;

    @FindBy(css = "ul.ui-select-choices div.ui-select-choices-row")
    private List<WebElement> industriesChoicesList;

    @FindBy(css = "ul.ui-select-choices")
    private WebElement industriesChoices;

    @FindBy(css = "li#ui-select-choices-1 span.ui-select-choices-row-inner")
    private List<WebElement> countryChoicesList;

    @FindBy(css = "li#ui-select-choices-1")
    private WebElement countryChoices;

    @FindBy(css = "button.button.button-primary.ng-scope")
    private WebElement nextButton;

    @FindBy(name = "transcriptsEmail")
    private WebElement transcriptsEmailInput;

    public void createNewTenant(String tenantOrgName, String transcriptsEmail){
        waitForElementToBeVisible(this.getCurrentDriver(), businessNameInput, 3).sendKeys(tenantOrgName);
        selectIndustryField.click();
        waitForElementToBeVisible(this.getCurrentDriver(), industriesChoices, 3);
        List<WebElement> sublist = industriesChoicesList.subList(0, 3);
        int randomIndustryNumber = ThreadLocalRandom.current().nextInt(0, sublist.size() - 1);
        sublist.get(randomIndustryNumber).click();
//        waitForElementToBeVisibleAgent(selectCountryField, 5,"main");
        waitFor(1000);
        clickElem(this.getCurrentDriver(), selectCountryField, 4, "Select country dropdown");
        waitForElementToBeVisible(this.getCurrentDriver(), countryChoices, 8);
        int randomCountryNumber = ThreadLocalRandom.current().nextInt(0, countryChoicesList.size() - 1);
        countryChoicesList.get(randomCountryNumber).click();
        nextButton.click();
        nextButton.click();
        transcriptsEmailInput.sendKeys(transcriptsEmail);
        clickElem(this.getCurrentDriver(), nextButton, 5,"Finish button");
    }
}
