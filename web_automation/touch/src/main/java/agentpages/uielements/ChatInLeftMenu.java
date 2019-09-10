package agentpages.uielements;

import abstractclasses.AbstractWidget;
import apihelper.ApiHelperTie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.io.File;

public class ChatInLeftMenu extends AbstractWidget {

    @FindBy(css = "div.profile-img")
    private WebElement userIcon;

    @FindBy(css = "div.profile-info>h2")
    private WebElement userName;

    @FindBy(css = "div.location")
    private WebElement location;

    @FindBy(css = "div.context-info div.icons>span")
    private WebElement channelIcon;

    public WebElement getChannelIcon() {
        return channelIcon;
    }

    public WebElement getAdapterIcon() {
        return adapterIcon;
    }

    @FindAll({
        @FindBy(xpath = "//div[contains(@class,'context-info')]//span[contains(@class,'icon svg-icon-webchat')]/*"),
        @FindBy(xpath = "//span[contains(@class,'http-icon')]//span/*"),
        @FindBy(xpath = "//span[contains(@class,'http-icon')]/span[contains(@class,'icon icon')]")
    })
    private WebElement adapterIcon;

    @FindBy( css = "span.icon.svg-icon-flagged")
    private WebElement flagIcon;

    @FindBy(xpath = "//div[@class='profile-img']//div[@class='empty-icon no-border']")
    private WebElement usercImg;

    @FindBy(xpath = "//div/div[@class='icons']/span[contains(@class,'icon icon')]")
    private WebElement userSentiment;

    @FindBy(css = "span.text-parsed-by-emoji")
    private WebElement messageText;

    private String overnightTicketIcon = "span.icon>svg.overnight";

    public ChatInLeftMenu(WebElement element) {
        super(element);
    }

    public ChatInLeftMenu setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public void openConversation() {
        waitForElementToBeClickable(this.getCurrentDriver(), userIcon, 6);
        userIcon.click();
    }

    public String getUserName() {
        return userName.getAttribute("innerText");
    }

    public String getLocation() {
        return location.getText();
    }

    public String getLastMessageText() {
            return messageText.getAttribute("innerText").trim();
    }

    public boolean isValidImg(String adapter) {
        File image = new File(System.getProperty("user.dir")+"/touch/src/test/resources/adaptericons/" + adapter + ".png");
          return isWebElementEqualsImage(this.getCurrentDriver(), adapterIcon,image);
    }


    public boolean isValidIconSentiment(String message){
        String sentiment = ApiHelperTie.getTIESentimentOnMessage(message).toLowerCase();
        File image =new File(System.getProperty("user.dir")+"/touch/src/test/resources/sentimenticons/"+sentiment+".png");
        return isWebElementEqualsImage(this.getCurrentDriver(), userSentiment, image);
    }

    public String getChatsChannel(){
        String iconClass = channelIcon.getAttribute("class");
        switch (iconClass){
            case "icon svg-icon-webchat":
                return "touch";
            case "icon svg-icon-fbmsg":
                return "fb messenger";
            case "icon icon-fbpost":
                return "fb post";
            default:
                return "unknown icon with tag span[@class='"+iconClass+"']";
        }
    }

    public boolean isOvernightTicketIconShown(){
        return  isElementExistsInDOMCss(this.getCurrentDriver(), overnightTicketIcon, 10);
    }

    public boolean isFlagIconShown(){
        return isElementShown(this.getCurrentDriver(), flagIcon, 10);
    }

    public boolean isFlagIconRemoved(){
        return isElementRemoved(this.getCurrentDriver(), flagIcon, 3);
    }

    public boolean isProfileIconNotShown(){
        return isElementRemoved(this.getCurrentDriver(), usercImg, 3);
    }

    public boolean isOvernightTicketRemoved(){
        return isElementRemovedByCSS(this.getCurrentDriver(), overnightTicketIcon, 9);
    }
}