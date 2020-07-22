package portaluielem;

import com.github.javafaker.Faker;
import drivermanager.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.List;


@FindBy(css = ".business-profile")
public class BusinessProfileWindow extends BasePortalWindow {

    private Faker faker = new Faker();


    @FindBy( xpath = "//span[contains(@class, 'color-picker-preview')]")
    private List<WebElement> colorButton;

    @FindBy(xpath = "//span[contains(@cl-color-picker, 'primary')]//span[contains(@class, 'color-picker')]")
    private WebElement primaryColor;

    @FindBy(xpath = "//span[contains(@cl-color-picker, 'second')]//span[contains(@class, 'color-picker')]")
    private WebElement secondaryColor;

    @FindBy(css = "[for='file-input-avatar']")
    private WebElement uploadButton ;

    @FindBy(css = "[for='file-input-avatar'] input")
    private WebElement inputFile;

    @FindBy(css = "ul.color-picker-scroll button.button-primary")
    private WebElement acceptButton ;

    @FindBy(name = "hexValue")
    private WebElement colorInputField ;

    private String inputPhotoLocator = "input[ngf-select][ng-model='%s']";

    public void clickUploadButton(){
        clickElem(this.getCurrentDriver(), uploadButton, 1,"Upload button");
    }

    public void clickacceptButton(){
        waitForElementToBeVisible(this.getCurrentDriver(), acceptButton, 5);
        acceptButton.click();
    }

    public void setPrimaryColor(String hex){
        waitForElementToBeVisible(this.getCurrentDriver(), primaryColor, 3);
        primaryColor.click();
        waitForElementToBeVisible(this.getCurrentDriver(), colorInputField, 3);
        colorInputField.clear();
        colorInputField.sendKeys(hex);
        waitFor(2000);
        acceptButton.click();
    }

    public void setSecondaryColor(String hex){
        waitForElementToBeVisible(this.getCurrentDriver(), secondaryColor, 3);
        secondaryColor.click();
        waitForElementToBeVisible(this.getCurrentDriver(), colorInputField, 3);
        colorInputField.clear();
        colorInputField.sendKeys(hex);
        waitFor(2000);
        acceptButton.click();
    }

    public String setRandomPrimaryColor(String color){
        waitForElementToBeVisible(this.getCurrentDriver(), primaryColor, 3);
        primaryColor.click();
        waitForElementToBeVisible(this.getCurrentDriver(), colorInputField, 3);
        colorInputField.clear();
        String newColor;
        do  {
            newColor = faker.color().hex().toLowerCase();
        } while (color.contains(newColor));
        colorInputField.sendKeys(newColor);
        waitFor(2000);
        acceptButton.click();
        return newColor;
    }

    public String setRandomSecondaryColor(String color){
        waitForElementToBeVisible(this.getCurrentDriver(), secondaryColor, 3);
        secondaryColor.click();
        waitForElementToBeVisible(this.getCurrentDriver(), colorInputField, 3);
        colorInputField.clear();
        String newColor;
        do  {
             newColor = faker.color().hex().toLowerCase();
        } while (color.contains(newColor));
        colorInputField.sendKeys(newColor);
        waitForElementToBeClickable(this.getCurrentDriver(), acceptButton, 6);
        acceptButton.click();
        return newColor;
    }

    public String getPrimaryColor(){
        waitForElementToBeVisible(this.getCurrentDriver(), primaryColor, 3);
        return Color.fromString(primaryColor.getCssValue("background-color")).asHex();
    }

    public String getSecondaryColor(){
        return Color.fromString(waitForElementToBeVisible(this.getCurrentDriver(), secondaryColor, 3)
                .getCssValue("background-color")).asHex();
    }

    public void uploadPhoto(String photoPath){
        waitForElementToBeVisible(this.getCurrentDriver(), uploadButton, 8);
        if(ConfigManager.isRemote()){
            ((RemoteWebDriver) this.getCurrentDriver()).setFileDetector(new LocalFileDetector());
        }
        inputFile.sendKeys(new File(photoPath).getAbsolutePath());
    }
}
