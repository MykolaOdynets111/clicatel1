package agentpages.uielements;

import abstractclasses.AbstractWidget;
import apihelper.ApiHelperTie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.io.File;

public class ChatInLeftMenu extends AbstractWidget {

    @FindAll({
    @FindBy(css = "div.profile-img"), //old locator
    @FindBy(css = ".cl-r-avatar.cl-r-avatar--medium")
    })
    private WebElement userIcon;

    @FindAll({
            @FindBy(css = "div.profile-info>h2"), //old locator
            @FindBy(css = "h2.cl-r-roster-item-user-name")
    })
    private WebElement userName;

    @FindAll({
            @FindBy(css = "div.location"), //old locator
            @FindBy(css = ".cl-r-roster-item-location")
    })
    private WebElement location;

    @FindAll({
            @FindBy(css = "div.context-info div.icons>span"), //old locator
            @FindBy(css = "span svg.cl-r-icon.cl-r-icon-widget.cl-r-icon--undefined")
    })
    private WebElement channelIcon;

    public WebElement getChannelIcon() {
        return channelIcon;
    }

    public WebElement getAdapterIcon() {
        return adapterIcon;
    }

    @FindBy(css = ".cl-r-roster-item__footer__left svg") //.//div[@class='icons']/span[contains(@class,'icon')][child::*]/*") old locator
    private WebElement adapterIcon;

    @FindAll({
            @FindBy( css = "span.icon.svg-icon-flagged"), //old locator
            @FindBy(name = "flag-indicator")
    })
    private WebElement flagIcon;

    private String flagIconCss =  "svg[name=flag-indicator]"; //""span.icon.svg-icon-flagged";

    @FindBy(xpath = "//div[@class='profile-img']//div[@class='empty-icon no-border']")
    private WebElement usercImg;

    @FindAll({
            @FindBy(xpath = "//div/div[@class='icons']/span[contains(@class,'icon icon')]"), //old locator
            @FindBy(css = ".cl-r-icon.cl-r-icon-emoji-happy.cl-r-icon--fill-green-caterpillar")
    })
    private WebElement userSentiment;

    @FindBy(css = "span.text-parsed-by-emoji")
    private WebElement messageText;

    private String overnightTicketIcon = ".cl-r-icon.cl-r-icon-tickets.cl-r-icon--fill-bright-orange"; //""span.icon>svg.overnight"; old locator

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
        return isElementRemoved(this.getCurrentDriver(), findElemByCSS(this.getCurrentDriver(), flagIconCss), 3);
    }

    public boolean isProfileIconNotShown(){
        return isElementRemoved(this.getCurrentDriver(), usercImg, 3);
    }

    public boolean isOvernightTicketRemoved(){
        return isElementRemovedByCSS(this.getCurrentDriver(), overnightTicketIcon, 9);
    }
}