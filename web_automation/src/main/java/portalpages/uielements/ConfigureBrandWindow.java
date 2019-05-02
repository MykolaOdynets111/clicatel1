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

    public void setColor(String color, String hex){
        if (color.toLowerCase().contains("second")){
            waitForElementToBeVisibleAgent(colorButton.get(1), 3, "admin");
            colorButton.get(1).click();
        }else {
            waitForElementToBeVisibleAgent(colorButton.get(0), 3, "admin");
            colorButton.get(0).click();
        }
        waitForElementToBeVisibleAgent(colorInputField, 3, "admin");
        colorInputField.clear();
        colorInputField.sendKeys(hex);
        waitFor(2000);
        acceptButton.click();
    }

    public String getColor(String color){
        if (color.toLowerCase().contains("second")){
            waitForElementToBeVisibleAgent(colorButton.get(1), 3, "admin");
            String hex = Color.fromString(colorButton.get(1).getCssValue("background-color")).asHex();
            return hex;
        }else {
            waitForElementToBeVisibleAgent(colorButton.get(0), 3, "admin");
            String hex = Color.fromString(colorButton.get(0).getCssValue("background-color")).asHex();
            return hex;
        }
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
