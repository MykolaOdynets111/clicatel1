package portalpages.uielements;

import drivermanager.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.List;


@FindBy(css = "div.edit-brand-details-page")
public class ConfigureBrandWindow extends BasePortalWindow {


    @FindBy( xpath = "//span[contains(@class, 'color-picker-preview')]")
    private List<WebElement> colorButton;

    @FindBy(xpath = "//span[contains(@cl-color-picker, 'primary')]//span[contains(@class, 'color-picker')]")
    private WebElement primaryColor;

    @FindBy(xpath = "//span[contains(@cl-color-picker, 'second')]//span[contains(@class, 'color-picker')]")
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

    public void setPrimaryColor(String hex){
        waitForElementToBeVisibleAgent(primaryColor, 3, "admin");
        primaryColor.click();
        waitForElementToBeVisibleAgent(colorInputField, 3, "admin");
        colorInputField.clear();
        colorInputField.sendKeys(hex);
        waitFor(2000);
        acceptButton.click();
    }

    public void setSecondaryColor(String hex){
        waitForElementToBeVisibleAgent(secondaryColor, 3, "admin");
        secondaryColor.click();
        waitForElementToBeVisibleAgent(colorInputField, 3, "admin");
        colorInputField.clear();
        colorInputField.sendKeys(hex);
        waitFor(2000);
        acceptButton.click();
    }

    public String getPrimaryColor(){
        waitForElementToBeVisibleAgent(primaryColor, 3, "admin");            return Color.fromString(primaryColor.getCssValue("background-color")).asHex();
    }

    public String getSecondaryColor(){
        waitForElementToBeVisibleAgent(secondaryColor, 3, "admin");
        return Color.fromString(secondaryColor.getCssValue("background-color")).asHex();
    }

    public void uploadPhoto(String photoPath){
        waitForElementToBeVisibleAgent(uploadButton, 8, "main");
        String selectPictureButtonNGModel = uploadButton.getAttribute("ng-model");
        RemoteWebElement element = DriverFactory.getAgentDriverInstance().findElement(By.cssSelector(
                String.format(inputPhotoLocator, selectPictureButtonNGModel)
        ));
        element.setFileDetector(new LocalFileDetector());
        element.sendKeys(new File(photoPath).getAbsolutePath());
       // waitWhileProcessing();
    }
}
