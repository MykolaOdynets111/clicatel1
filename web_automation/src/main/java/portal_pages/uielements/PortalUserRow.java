package portal_pages.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PortalUserRow extends Widget implements WebActions {

    @FindBy(css = "td.cl-name-item.limited-table-cell-small span")
    private WebElement agentFirstName;

    @FindBy(css = "td.cl-name-item.limited-table-cell-medium span")
    private WebElement agentLastName;

    @FindBy(css = "a.button")
    private WebElement manageButton;

    public PortalUserRow(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }


    public String getAgentFullName(){
        return agentFirstName.getText() + " " + agentLastName.getText();
    }

    public void clickManageButton(){
        manageButton.click();
    }

}
