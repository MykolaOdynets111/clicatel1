package chathubpages;

import abstractclasses.UnityAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IntegrationsPage extends UnityAbstractPage {

    //Todo : Make this locator generic and take Integration card name as input
    @FindBy(xpath = "//*[@class='integrations-details_title u-s3 mb-10' and text()='Zendesk Support']")
    private WebElement zendeskIntegrationsCard;

    @FindBy(xpath = "//span[text()='4 Available Integrations ']")
    private WebElement availableIntegrations;

    @FindBy(xpath = "//*[@class ='mat-card-title info-card__title'][1]")
    private WebElement firstCard;

    public IntegrationsPage(WebDriver driver) {

        super(driver);
    }

    //Todo : Make this function generic and take Integration card name as input
    public void clickOnZendeskSupport() {
        clickElem(this.getCurrentDriver(), zendeskIntegrationsCard, 3, "Zendesk Support");
    }

    public boolean availableIntegrationsDisplayed(){
        return isElementShown(this.getCurrentDriver(), availableIntegrations,3);
    }

    public String integrationsIsFirstCard(){
        return getTextFromElem(this.getCurrentDriver(), firstCard,3,"First Card of Numbers & Integrations");
    }

}
