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
        waitForElementToBeVisibleAgent(businessNameInput, 3).sendKeys(tenantOrgName);
        selectIndustryField.click();
        waitForElementToBeVisibleAgent(industriesChoices, 3,"main");
        List<WebElement> sublist = industriesChoicesList.subList(0, 3);
        int randomIndustryNumber = ThreadLocalRandom.current().nextInt(0, sublist.size() - 1);
        sublist.get(randomIndustryNumber).click();
//        waitForElementToBeVisibleAgent(selectCountryField, 5,"main");
        waitFor(1000);
        clickElemAgent(selectCountryField, 4, "admin", "Select country dropdown");
        waitForElementToBeVisibleAgent(countryChoices, 8,"main");
        int randomCountryNumber = ThreadLocalRandom.current().nextInt(0, countryChoicesList.size() - 1);
        countryChoicesList.get(randomCountryNumber).click();
        nextButton.click();
        nextButton.click();
        transcriptsEmailInput.sendKeys(transcriptsEmail);
        clickElemAgent(nextButton, 5, "admin", "Finish button");
    }
}
