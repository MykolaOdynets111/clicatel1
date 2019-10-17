package portaluielem;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class PortalUserRow extends AbstractWidget {

    @FindAll({
            @FindBy(css = "td.limited-table-cell-small b.agent-initials"),
            @FindBy(xpath = ".//td[contains(@class, 'cl-name-item')][not(contains(@class, 'user-management-table-cell'))]//a/b")
    })
    private WebElement agentFirstName;

    @FindAll({
            @FindBy(css = "td.limited-table-cell-medium b.agent-initials"),
            @FindBy(xpath = ".//td[contains(@class, 'user-management-table-cell')]//a/b")
    })
    private WebElement agentLastName;

    @FindBy(css = "a.button")
    private WebElement manageButton;

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

    public void clickManageButton(){
        manageButton.click();
    }

    public void clickOnUserName(){
        agentFirstName.click();
    }

}
