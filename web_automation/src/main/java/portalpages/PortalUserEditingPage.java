package portalpages;

import drivermanager.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.uielements.EditUserRolesWindow;

import java.io.File;
import java.util.List;

public class PortalUserEditingPage extends PortalAbstractPage {

    @FindBy(xpath = "//button[@cl-permission='update-agent']")
    private WebElement uploadPhoto;

    @FindBy(css = "div.create-integration-container")
    private WebElement uploadPhotoWindow;

    @FindBy(xpath = "//button[@ngf-select]")
    private WebElement selectPictureButton;

    @FindBy(xpath = "//button[text()='Save'][not(@id='integration-save')]")
    private WebElement saveButton;

    @FindBy(css = "form[name='userForm'] div[ng-if='profileIcon']>img")
    private WebElement profileImage;

    @FindBy(xpath = "//button[text()='Edit user roles']")
    private WebElement editUserButton;

    @FindBy(css = "button[ng-click='delete()']")
    private WebElement deleteButton;

    private EditUserRolesWindow editUserRolesWindow;

    private String inputPhotoLocator = "input[ngf-select][ng-model='%s']";

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
        saveButton.click();

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

}
