package portaluielem;

import datamanager.model.NewUserModel;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.cl-wizzard.create-integration-container")
public class AddNewUserWindow extends BasePortalWindow {

    @FindBy(name = "firstName0")
    private WebElement firstNameInput;

    @FindBy(name = "lastName0")
    private WebElement lastNameInput;

    @FindBy(name = "email0")
    private WebElement emailInput;

    @Step(value = "Provide info about new user")
    public AddNewUserWindow provideNewUserDetailsInfo(NewUserModel userModel){
        inputText(this.getCurrentDriver(), firstNameInput, 3, "First Name input", userModel.getFirstName());
        inputText(this.getCurrentDriver(), lastNameInput, 3, "Last Name input", userModel.getLastName());
        inputText(this.getCurrentDriver(), emailInput, 3, "Email", userModel.getEmail());
        return this;
    }
}
