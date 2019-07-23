package portalpages;

import driverfactory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.EditUserRolesWindow;

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

    // == Constructors == //

    public PortalUserEditingPage() {
        super();
    }
    public PortalUserEditingPage(String agent) {
        super(agent);
    }
    public PortalUserEditingPage(WebDriver driver) {
        super(driver);
    }

    public EditUserRolesWindow getEditUserRolesWindow() {
        editUserRolesWindow.setCurrentDriver(this.getCurrentDriver());
        return editUserRolesWindow;
    }

    public void clickUploadPhotoButton(){
        clickElem(this.getCurrentDriver(), uploadPhoto, 8, "Upload photo");
    }

    public void uploadPhoto(String photoPath){
        waitForElementToBeVisible(this.getCurrentDriver(), uploadPhotoWindow, 8);
        String selectPictureButtonNGModel = selectPictureButton.getAttribute("ng-model");
        RemoteWebElement element = DriverFactory.getAgentDriverInstance().findElement(By.cssSelector(
                String.format(inputPhotoLocator, selectPictureButtonNGModel)
        ));
        element.setFileDetector(new LocalFileDetector());
        element.sendKeys(new File(photoPath).getAbsolutePath());
        savePhotoButton.click();

        waitWhileProcessing(14, 20);
    }

    public String getImageURL(){
        if(isElementShown(this.getCurrentDriver(), profileImage, 3)){
            return profileImage.getAttribute("src");
        } else{
            return"";
        }
    }

    public void clickEditUserRolesButton(){
        clickElem(this.getCurrentDriver(), editUserButton, 3, "'Edit user roles'");
    }

    public void clickDeleteButton(){
        clickElem(this.getCurrentDriver(), deleteButton, 3,"'Delete button'");
    }

    public void updateAgentPersonalDetails(Map<String, String> updatedAgentInfo){
        inputText(this.getCurrentDriver(), firstNameInput, 2, "First name input", updatedAgentInfo.get("firstName"));
        inputText(this.getCurrentDriver(), lastNameInput, 2, "Last name input", updatedAgentInfo.get("lastName"));
        clickElem(this.getCurrentDriver(), saveChangesButton, 5,"Save changes");
    }

    public void clickDeactivateButton(){
        clickElem(this.getCurrentDriver(), deactivateButton, 3,"'Deactivate User' button");
    }

}
