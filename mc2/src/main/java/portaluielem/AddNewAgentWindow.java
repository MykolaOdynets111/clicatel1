package portaluielem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(css = "div[ng-controller='AddAgentsCtrl']")
public class AddNewAgentWindow extends BasePortalWindow {

    @FindBy(css = "input[name='firstName0']")
    public WebElement firstNameInput;

    @FindBy(css = "input[name='lastName0']")
    public WebElement lastNameInput;

    @FindBy(css = "input[name='email0']")
    public WebElement emailInput;

    public void createNewAgent(String firstName, String lastName, String email){
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        emailInput.sendKeys(email);

        clickPrimaryBindingButton("Add Agent users");
    }
}
