package portalpages.uielements;

import drivermanager.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;


@FindBy(css = "div.edit-brand-details-page")
public class ConfigureBrandWindow extends BasePortalWindow {

    @FindBy(name = "ui-select-choices-0")
    private WebElement primaryColor;

    @FindBy(id = "ui-select-choices-1")
    private WebElement secondaryColor;

    @FindBy(xpath = "//button[text()=' Upload ']")
    private WebElement uploadButton ;

    @FindBy(xpath = "//button[text()='Accept']")
    private WebElement acceptButton ;

    @FindBy(name = "hexValue")
    private WebElement colorInputField ;

    private String inputPhotoLocator = "input[ngf-select][ng-model='%s']";

    public void clickuploadButton(){
        clickElemAgent(uploadButton, 1, "admin", "Upload button");
    }

    public void clickacceptButton(){
        waitForElementToBeVisibleAgent(acceptButton, 5, "admin");
        acceptButton.click();
    }

    public void setColor(){
        waitForElementToBeVisibleAgent(colorInputField, 5, "admin");
        colorInputField.sendKeys();
    }

    public void uploadPhoto(String photoPath){
      //  waitForElementToBeVisibleAgent(uploadPhotoWindow, 8, "main");
        String selectPictureButtonNGModel = uploadButton.getAttribute("ng-model");
        RemoteWebElement element = DriverFactory.getAgentDriverInstance().findElement(By.cssSelector(
                String.format(inputPhotoLocator, selectPictureButtonNGModel)
        ));
        element.setFileDetector(new LocalFileDetector());
        element.sendKeys(new File(photoPath).getAbsolutePath());
       // waitWhileProcessing();
    }
}
