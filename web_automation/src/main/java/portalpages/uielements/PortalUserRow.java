package portalpages.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PortalUserRow extends Widget implements WebActions {

    @FindAll({
            @FindBy(css = "td.limited-table-cell-small b.agent-initials"),
            @FindBy(xpath = "//td[contains(@class, 'cl-name-item')][not(contains(@class, 'limited-cell-table'))]//a/b")
    })
    private WebElement agentFirstName;

    @FindAll({
            @FindBy(css = "td.limited-table-cell-medium b.agent-initials"),
            @FindBy(xpath = "//td[contains(@class, 'limited-cell-table')]//a/b")
    })
    private WebElement agentLastName;

    @FindBy(css = "a.button")
    private WebElement manageButton;

    public PortalUserRow(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }


    public String getAgentFullName(){
        waitForElementToBeVisibleAgent(agentFirstName, 6);
        return agentFirstName.getText() + " " + agentLastName.getText();
    }

    public void clickManageButton(){
        manageButton.click();
    }

    public void clickOnUserName(){
        agentFirstName.click();
    }

}
