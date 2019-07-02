package portalpages;

import drivermanager.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.uielements.EditUserRolesWindow;

import java.io.File;
import java.util.Map;

public class PortalUserEditingPage extends PortalAbstractPage {

    @FindBy(xpath = "//button[@cl-permission='update-agent']")
    private WebElement uploadPhoto;

    @FindBy(css = "div.create-integration-container")
    private WebElement uploadPhotoWindow;

    @FindBy(xpath = "//button[@ngf-select]")
    private WebElement selectPictureButton;

    @FindBy(xpath = "//button[text()='Save'][not(@id='integration-save')]")
    private WebElement savePhotoButton;

    @FindBy(css = "button[ng-click='save()']")
    private WebElement saveChangesButton;

    @FindBy(css = "form[name='userForm'] div[ng-if='profileIcon']>img")
    private WebElement profileImage;

    @FindBy(xpath = "//button[text()='Edit user roles']")
    private WebElement editUserButton;

    @FindBy(css = "button[ng-click='delete()']")
    private WebElement deleteButton;

    @FindBy(name = "firstName")
    private WebElement firstNameInput;

    @FindBy(name = "lastName")
    private WebElement lastNameInput;

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(css = "button[ng-click='changeUserStatus(false)']")
    private WebElement deactivateButton;

    private EditUserRolesWindow editUserRolesWindow;

    private String inputPhotoLocator = "input[ngf-select][ng-model='%s']";

    private String saveButtonXPATH = "//button[text()='Save'][not(@id='integration-save')]";

    public EditUserRolesWindow getEditUserRolesWindow() {
        return editUserRolesWindow;
    }

    public void clickUploadPhotoButton(){
        waitForElementToBeVisibleAgent(uploadPhoto, 8, "main");
        uploadPhoto.click();
    }

    public void uploadPhoto(String photoPath){
        waitForElementToBeVisibleAgent(uploadPhotoWindow, 8, "main");
        String selectPictureButtonNGModel = selectPictureButton.getAttribute("ng-model");
        RemoteWebElement element = DriverFactory.getAgentDriverInstance().findElement(By.cssSelector(
                String.format(inputPhotoLocator, selectPictureButtonNGModel)
        ));
        element.setFileDetector(new LocalFileDetector());
        element.sendKeys(new File(photoPath).getAbsolutePath());
        savePhotoButton.click();

        waitWhileProcessing();
    }

    public String getImageURL(){
        if(isElementShownAgent(profileImage, 3, "main")){
            return profileImage.getAttribute("src");
        } else{
            return"";
        }
    }

    public void clickEditUserRolesButton(){
        clickElemAgent(editUserButton, 3, "main", "'Edit user roles'");
    }

    public void clickDeleteButton(){
        clickElemAgent(deleteButton, 3, "main", "'Delete button'");
    }

    public void updateAgentPersonalDetails(Map<String, String> updatedAgentInfo){
        firstNameInput.clear();
        inputTextAgent(firstNameInput, 2, "admin", "First name input", updatedAgentInfo.get("firstName"));
        lastNameInput.clear();
        inputTextAgent(lastNameInput, 2, "admin", "Last name input", updatedAgentInfo.get("lastName"));
        clickElemAgent(saveChangesButton, 5,"admin", "Save changes");
    }

    public void clickDactivateButton(){
        clickElemAgent(deactivateButton, 3, "main", "'Deactivate User' button");
    }

}
