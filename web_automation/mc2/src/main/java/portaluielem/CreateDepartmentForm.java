package portaluielem;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.List;

@FindBy(css =".modal-dialog .modal-content")
public class CreateDepartmentForm extends BasePortalWindow{

    @FindBy(css =".cl-input.cl-input--text")
    private WebElement nameField;

    @FindBy(css =".cl-input.cl-input--textarea")
    private WebElement descriptionForm;

    @FindBy(css = ".checkbox-label")
    private List<WebElement> departmentAgentsCheckbox;

    @FindBy(xpath = ".//button[text() = 'Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = ".//button[text() = 'Create']")
    private WebElement createButton;


    @Step(value = "Set text to the Name field")
    public CreateDepartmentForm setNameField(String name){
       waitForElementToBeVisible(this.getCurrentDriver(), nameField, 1);
        nameField.sendKeys(name);
        return this;
    }

    @Step(value = "Set text to the Description Form")
    public CreateDepartmentForm setDescriptionForm(String name){
        waitForElementToBeVisible(this.getCurrentDriver(), descriptionForm, 1);
        descriptionForm.sendKeys(name);
        return this;
    }

    @Step(value = "Select Department Agents checkbox")
    public CreateDepartmentForm selectDepartmentAgentsCheckbox(String agentName){
        departmentAgentsCheckbox.stream().filter(a -> a.getText().trim().equals(agentName)).findFirst().orElseThrow(() -> new AssertionError("Cannot select '" + agentName + "' checkbox")).click();
        return this;
    }

    @Step(value = "Click Create button")
    public void clickCreateButton(){
        createButton.click();
    }

    @Step(value = "Click Cancel button")
    public void clickCancelButton(){
        cancelButton.click();
    }

}
