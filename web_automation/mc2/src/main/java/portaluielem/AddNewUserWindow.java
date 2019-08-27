package portaluielem;

import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

@FindBy(css = "div.cl-wizzard.create-integration-container")
public class AddNewUserWindow extends BasePortalWindow {

    @FindBy(name = "firstName0")
    private WebElement firstNameInput;

    @FindBy(name = "lastName0")
    private WebElement lastNameInput;

    @FindBy(name = "email0")
    private WebElement emailInput;


}
