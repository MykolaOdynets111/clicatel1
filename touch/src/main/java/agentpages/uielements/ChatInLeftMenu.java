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
        @FindBy(css = "[data-testid='avatar']"),
        @FindBy(css = "[selenium-id='Avatar']"), //toDo old locator
    })
    private WebElement userIcon;

    @FindBy(css = ".cl-chat-item-user-name")
    private WebElement userName;

    @FindAll({
        @FindBy(css = "[data-testid=roster-item-location]"),
        @FindBy(css = "[selenium-id=roster-item-location]"), //toDo old locator
    })
    private WebElement location;

    @FindAll({
            @FindBy(css = "[data-testid=chat-item-icons-holder]"),
            @FindBy(css = "[selenium-id=chat-item-icons-holder]") //toDo old locator
    })
    private WebElement channelIcon;

    @FindAll({
            @FindBy(css = "[data-testid='chat-item-icons-holder'] svg"),
            @FindBy(css = "[selenium-id='chat-item-icons-holder'] svg") //toDo old locator
    })
    private WebElement adapterWrappedIcon;

    @FindAll({
            @FindBy(css = "[data-testid='icon-flag-filled'] g"),
            @FindBy(css = "[selenium-id='icon-flag-filled'] g") //toDo old locator
    })
    private WebElement flagIcon;

    private String flagIconCss = "[data-testid='icon-flag-filled'] g"; //""span.icon.svg-icon-flagged";

    @FindBy(css = "[selenium-icon-user-single]")
    private WebElement usercImg;

    @FindBy(css = ".cl-r-roster-item__header__status .cl-r-icon")
    private WebElement userSentiment;

    @FindBy(css = "span.text-parsed-by-emoji")
    private WebElement messageText;

    private String overnightTicketIcon = ".cl-r-icon.cl-r-icon-tickets.cl-r-icon--fill-bright-orange"; //""span.icon>svg.overnight"; old locator

    public ChatInLeftMenu(WebElement element) {
        super(element);
    }

    public ChatInLeftMenu setCurrentDriver(WebDriver currentDriver) {
        this.currentDriver = currentDriver;
        return this;
    }

    public void openConversation() {
        waitForElementToBeClickable(this.getCurrentDriver(), userIcon, 6);
        userIcon.click();
    }

    public String getUserName() {
        return getTextFromElem(this.getCurrentDriver(), userName, 1, "User Name");
    }

    public String getLocation() {
        return location.getText();
    }

    public String getLastMessageText() {
        return messageText.getAttribute("innerText").trim();
    }

    public boolean isValidImg(String adapter) {
        File image = new File(System.getProperty("user.dir")+"/src/test/resources/adaptericons/" + adapter + ".png");
        waitForElementToBeVisible(this.getCurrentDriver(), adapterWrappedIcon,4);
        return isWebElementEqualsImage(this.getCurrentDriver(), adapterWrappedIcon,image);
    }


    public boolean isValidIconSentiment(String message) {
        String sentiment = ApiHelperTie.getTIESentimentOnMessage(message).toLowerCase();
        File image = new File(System.getProperty("user.dir") + "/touch/src/test/resources/sentimenticons/" + sentiment + ".png");
        return isWebElementEqualsImage(this.getCurrentDriver(), userSentiment, image);
    }

    public String getChatsChannel() {
        String iconClass = channelIcon.getAttribute("class");
        switch (iconClass) {
            case "icon svg-icon-webchat":
                return "touch";
            case "icon svg-icon-fbmsg":
                return "fb messenger";
            case "icon icon-fbpost":
                return "fb post";
            default:
                return "unknown icon with tag span[@class='" + iconClass + "']";
        }
    }

    public String getAdapterIconName() {
        return adapterWrappedIcon.getAttribute("name").trim();
    }

    public boolean isOvernightTicketIconShown() {
        return isElementExistsInDOMCss(this.getCurrentDriver(), overnightTicketIcon, 10);
    }

    public boolean isFlagIconShown() {
        return isElementShown(this.getCurrentDriver(), flagIcon, 10);
    }

    public boolean isFlagIconRemoved() {
        return isElementRemoved(this.getCurrentDriver(), findElemByCSS(this.getCurrentDriver(), flagIconCss), 3);
    }

    public boolean isProfileIconNotShown() {
        return isElementRemoved(this.getCurrentDriver(), usercImg, 3);
    }

    public boolean isOvernightTicketRemoved() {
        return isElementRemovedByCSS(this.getCurrentDriver(), overnightTicketIcon, 10);
    }
}