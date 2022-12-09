package myworkspacepages;

import abstractclasses.AgentAbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyWorkspacePage extends AgentAbstractPage {

    // NUMBERS AND INTEGRATIONS
    @FindBy(xpath = "//*[@class='mat-card-title info-card__title' and text()=' Integrations ']")
    private WebElement integrationsTab;

    @FindBy(xpath = "//*[@class='mat-card-title info-card__title' and text()=' Long Numbers & Short Codes ']")
    private WebElement lnscTab;

    @FindBy(xpath = "//*[@class='mat-card-title info-card__title' and text()=' Test Phones ']")
    private WebElement testPhonesTab;

    @FindBy(xpath = "//*[@class='mat-card-title info-card__title' and text()=' API Integrations ']")
    private WebElement apiIntegrationsTab;

    public MyWorkspacePage() {
        super();
    }

    public void clickOnIntegrationsTab() {

        clickElem(this.getCurrentDriver(), integrationsTab, 3, "Integrations Tab");
    }

    public void clickOnLNSCTab() {

        clickElem(this.getCurrentDriver(), lnscTab, 3, "Long Numbers & Short Codes Tab");
    }

    public void clickOnTestPhonesTab() {

        clickElem(this.getCurrentDriver(), testPhonesTab, 3, "Test Phones Tab");
    }

    public void clickOnApiIntegrationsTab() {

        clickElem(this.getCurrentDriver(), apiIntegrationsTab, 3, "API Integrations Tab");
    }

}
