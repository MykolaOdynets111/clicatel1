package portal_pages;

import driverManager.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PortalUserManagementPage extends PortalAbstractPage {

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

    private String inputPhotoLocator = "input[ngf-select][ng-model='%s']";

    public void clickUploadPhotoButton(){
        waitForElementToBeVisibleAgent(uploadPhoto, 8, "main");
        uploadPhoto.click();
    }

    public void uploadPhoto(String photoPath){
        waitForElementToBeVisibleAgent(uploadPhotoWindow, 8, "main");
        String selectPictureButtonNGModel = selectPictureButton.getAttribute("ng-model");
        DriverFactory.getAgentDriverInstance().findElement(By.cssSelector(
                String.format(inputPhotoLocator, selectPictureButtonNGModel)
        )).sendKeys(photoPath);
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
}
