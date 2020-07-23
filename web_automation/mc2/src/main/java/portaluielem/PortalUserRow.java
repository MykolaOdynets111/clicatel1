package portaluielem;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class PortalUserRow extends AbstractWidget {

    @FindBy(xpath = ".//td[contains(@class, 'cl-name-item')][not(contains(@class, 'user-management-table-cell'))]//a/b")
    private WebElement agentFirstName;

    @FindBy(xpath = ".//td[contains(@class, 'user-management-table-cell')]//a/b")
    private WebElement agentLastName;

    public PortalUserRow(WebElement element) {
        super(element);
    }

    public PortalUserRow setCurrentDriver(WebDriver driver){
        this.currentDriver = driver;
        return this;
    }

    public String getAgentFullName(){
        waitForElementToBeVisible(this.getCurrentDriver(), agentFirstName, 6);
        return agentFirstName.getText() + " " + agentLastName.getText();
    }

    public void clickOnUserName(){
        agentFirstName.click();
    }

}
