package touchpages.uielements;

import abstractclasses.AbstractUIElement;
import drivermanager.ConfigManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "[class$=drag-zone]")
public class AttachmentWindow extends AbstractUIElement {

    @FindBy(css =".cl-btn--browse-files")
    private WebElement browseButton;

    @FindBy(css = "input[type='file']")
    private WebElement inputFile;

    public AttachmentWindow openBrowseWindow(){
        clickElem(this.getCurrentDriver(), browseButton, 3, "Browse button");
        waitFor(2000);
        return this;
    }

    public void setPathToFile(String pathToFile){
        if(ConfigManager.isRemote()){
            ((RemoteWebDriver) this.getCurrentDriver()).setFileDetector(new LocalFileDetector());
        }
        ((JavascriptExecutor) this.getCurrentDriver()).executeScript("arguments[0].style.display = 'block';", inputFile);
        inputFile.sendKeys(pathToFile);
    }
}
