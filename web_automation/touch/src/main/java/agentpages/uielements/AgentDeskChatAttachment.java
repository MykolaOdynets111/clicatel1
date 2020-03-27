package agentpages.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

public class AgentDeskChatAttachment extends AbstractWidget {

    public AgentDeskChatAttachment(WebElement element) {
        super(element);
    }

    public AgentDeskChatAttachment setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    @FindBy(css =".file-name")
    private WebElement fileName;

    @FindBy(css = ".file-size")
    private WebElement fileSize;

    @FindBy(css = ".download-file-link")
    private WebElement downloadLink;

    public String getFileName(){
        return getTextFromElem(this.getCurrentDriver(), fileName, 3, "File Name");
    }

    public String getFileSize(){
        return getTextFromElem(this.getCurrentDriver(), fileSize, 3, "File Size");
    }

    public void clickDownloadLink(){
        if(this.getCurrentDriver() instanceof RemoteWebDriver){
            ((RemoteWebDriver) this.getCurrentDriver()).setFileDetector(new LocalFileDetector());
        }
        clickElem(this.getCurrentDriver(), downloadLink, 6, "Download Link");
    }

}
