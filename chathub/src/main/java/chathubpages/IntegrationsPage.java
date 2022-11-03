package chathubpages;

import abstractclasses.UnityAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IntegrationsPage extends UnityAbstractPage {

    //Todo : Make this locator generic and take Integration card name as input
    @FindBy(xpath = "//*[@class='integrations-details_title u-s3 mb-10' and text()='Zendesk Support']")
    private WebElement zendeskIntegrationsCard;

    public IntegrationsPage(WebDriver driver) {

        super(driver);
    }

    //Todo : Make this function generic and take Integration card name as input
    public void clickOnZendeskSupport() {
        clickElem(this.getCurrentDriver(), zendeskIntegrationsCard, 3, "Zendesk Support");
    }
}
