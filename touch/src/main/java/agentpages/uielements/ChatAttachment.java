package agentpages.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChatAttachment extends AbstractWidget {

    public ChatAttachment(WebElement element) {
        super(element);
    }

    public ChatAttachment setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    @FindBy(css =".media-message__file-name")
    private WebElement fileName;

    @FindBy(css = ".file-size")
    private WebElement fileSize;

    @FindBy(css = "svg[name='download']")
    private WebElement downloadLink;

    @FindBy(css = "div[data-vjs-player = true]")
    private WebElement mediaPlayer;

    @FindBy(css ="button.vjs-play-control")
    private WebElement playPauseButton;

    @FindBy(css = "button.vjs-big-play-button")
    private WebElement playMP4button;

    public String getFileName(){
        return getTextFromElem(this.getCurrentDriver(), fileName, 25, "File Name");
    }

    public String getFileSize(){
        return getTextFromElem(this.getCurrentDriver(), fileSize, 3, "File Size");
    }

    public void clickDownloadLink(){
        clickElem(this.getCurrentDriver(), downloadLink, 10, "Download Link");
    }

    public ChatAttachment clickPlayPauseButton(String fileType){
        if (fileType.equalsIgnoreCase("mp4")){
            clickElem(this.getCurrentDriver(), mediaPlayer, 30, "Play Button");
        } else {
            clickElem(this.getCurrentDriver(), playPauseButton, 20, "Play Button");
        }
        return this;
    }

    public boolean verifyIsFilePlaying(){
        boolean isPlaying = false;
        for (int i = 0; i<8; i++){
            isPlaying = mediaPlayer.getAttribute("class").contains("vjs-playing");
            if (isPlaying){
                break;
            }
            waitFor(1000);
        }
        return isPlaying;
    }

}
