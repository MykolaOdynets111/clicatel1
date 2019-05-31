package agentpages.uielements;

import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ChatInLeftMenu extends Widget implements WebActions, ActionsHelper, JSHelper {

    @FindBy(css = "div.profile-img")
    private WebElement userIcon;

    @FindBy(css = "div.profile-info>h2")
    private WebElement userName;

    @FindBy(css = "div.location")
    private WebElement location;

    @FindBy(css = "div.context-info div.icons>span")
    private WebElement channelIcon;
    @FindAll({
        @FindBy(xpath = "//div[@class='icons']/span/span[1]"),
        @FindBy(xpath = "//div[@class='icons']/span[1]")
    })
    private WebElement adapterIcon;

    @FindBy( css = "span.icon.svg-icon-flagged")
    private WebElement flagIcon;

    @FindBy(xpath = "//div[@class='profile-img']//div[@class='empty-icon no-border']")
    private WebElement usercImg;

    @FindBy(xpath = "//div[@class='icons']/span[2]")
    private WebElement usercSentiment;

    @FindBy(css = "span.text-parsed-by-emoji")
    private WebElement messageText;

    private String overnightTicketIcon = "span.icon>svg.overnight";

    public ChatInLeftMenu(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public void openConversation() {
        waitForElementToBeClickableAgent(userIcon, 6, "main");
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

    public boolean isValidIcon(String adapter) {
        String iconClass = adapterIcon.getAttribute("class");
        switch (adapter){
            case "fbmsg":
                return iconClass.equals("icon svg-icon-fbmsg");
            case "whatsapp":
                return iconClass.equals("icon svg-icon-whatsapp");
            case "fbpost":
                return iconClass.equals("icon icon-fbpost");
            case "twdm":
                return iconClass.equals("icon svg-icon-twdm");
            case "twmention":
                return iconClass.equals("icon icon-twmention");
            case "webchat":
                return iconClass.equals("icon svg-icon-webchat");
            default:
                Assert.assertTrue(false,"Can not verify icon for "+adapter+" adapter");
                return false;
        }
    }

    public boolean isValidIconSentiment(String sentiment) {
        String iconClass = usercSentiment.getAttribute("class");
        switch (sentiment){
            case "negativ":
                return iconClass.equals("icon icon-negative");
            case "neutral":
                return iconClass.equals("icon icon-neutral");
            case "positiv":
                return iconClass.equals("icon icon-positiv");
            default:
                Assert.assertTrue(false,"Can not verify icon for "+sentiment+" sentiment");
                return false;
        }
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
        return  isElementExistsInDOMAgentCss(overnightTicketIcon, 10, "main");
    }

    public boolean isFlagIconShown(){
        return isElementShownAgent(flagIcon, 10, "main");
    }

    public boolean isFlagIconRemoved(){
        return isElementNotShown(flagIcon, 1);
    }

    public boolean isProfileIconNotShown(){
        return isElementNotShown(usercImg, 1);
    }

    public boolean isOvernightTicketRemoved(){
        return isElementNotShownAgentByCSS(overnightTicketIcon, 9);
    }
}