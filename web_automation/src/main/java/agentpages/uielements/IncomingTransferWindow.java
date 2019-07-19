package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;

@FindBy(css = "div.touch-pop-up")
public class IncomingTransferWindow extends AbstractUIElement {

    @FindBy(css = "div.touch-header h2")
    private WebElement transferWindowHeader;

    @FindBy(xpath = "//button[text()='Accept transfer']")
    private WebElement acceptTransferButton;

    @FindBy(xpath = "//button[text()='Accept']")
    private WebElement acceptRejectedButton;

    @FindBy(xpath = "//button[text()='Reject transfer']")
    private WebElement rejectTransfetButton;

    @FindBy(xpath = "(//dt[text()='Transfer from:']/following-sibling::dd)[1]")
    private WebElement fromAgentName;

    @FindBy(xpath = "//dt[text()='Note:']/following-sibling::dd")
    private WebElement transferNotes;

    @FindBy(css = "div.client-name-text")
    private WebElement clientName;

    @FindBy(css = "div.chat-context")
    private WebElement clientMessage;

    @FindBy(css = "dl.dl-horizontal")
    private WebElement rejectedBy;

    @FindBy(xpath = "//div[@class='empty-icon no-border']")
    private WebElement transferPicture;

    @FindBy(xpath = "//div[@class='icons']/span/*")
    private WebElement transferChannel;

    @FindBy(xpath = "//div/div[@class='icons']/span[contains(@class,'icon icon-')]")
    private WebElement transferSentiment;

    public void acceptTransfer(){
        acceptTransferButton.click();
    }

    public void rejectTransfer(){
        rejectTransfetButton.click();
    }

    public void acceptRejectTransfer(String agent){
        clickElemAgent(acceptRejectedButton, 3, agent, "'Accept' rejected transfer button");
    }

    public String getTransferNotes(){
        // ToDo: update timeout after it is provided in System timeouts confluence page
        return getTextFromElemAgent(transferNotes, 10, "second agent", "Transfer notes");
    }

    public String getClientName(){
        return  clientName.getText();
    }

    public String getClientMessage(){
        return  clientMessage.getText();
    }

    public String getFromAgentName(){
        return  fromAgentName.getText();
    }

    public String getTransferWindowHeader(String agent){
        return getTextFromElemAgent(transferWindowHeader, 2, agent, "Transfer chat window header");
    }

    public boolean isTransferWindowHeaderNotShown(String agent){
        return isElementNotShownAgent(transferWindowHeader,2,agent);
    }

    public String getRejectedBy(String agent){
        return getTextFromElemAgent(rejectedBy, 2, agent, "Transfer chat window header");
    }

    public boolean isValidImgTransferPicture() {
        waitFor(1500);//it should be. transfer window apeared but not all(animation);
        File image = new File("src/test/resources/transferchatimg/transferPicture.png");
        return isWebElementEqualsImage(transferPicture,image, "second agent");
    }

    public boolean isValidImTransferChannel() {
        File image = new File("src/test/resources/transferchatimg/transferChannel.png");
        return isWebElementEqualsImage(transferChannel,image, "second agent");
    }

    public boolean isValidImgTransferSentiment() {
        File image = new File("src/test/resources/transferchatimg/transferSentiment.png");
        return isWebElementEqualsImage(transferSentiment,image, "second agent");
    }

    public boolean isRigthSideTransferChatWindow() {
        int xcordElement = transferWindowHeader.getLocation().getX();
        int xcordWindow = findElemByXPATHAgent("//body","second agent").getSize().getWidth()/2;
        return    xcordElement > xcordWindow;
    }

}
