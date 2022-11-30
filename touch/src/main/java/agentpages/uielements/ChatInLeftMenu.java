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

    @FindBy(css = "[data-testid='chat-item-icons-holder'] svg")
    private WebElement chatIcon;

    @FindAll({
            @FindBy(css = "[name='flag-filled']")
    })
    private WebElement flagIcon;

    private String flagIconCss = "[data-testid='icon-flag-filled'] g";

    @FindBy(css = "[selenium-icon-user-single]")
    private WebElement usercImg;

    @FindBy(css = ".cl-r-roster-item__header__status .cl-r-icon")
    private WebElement userSentiment;

    @FindBy(css = "span.text-parsed-by-emoji")
    private WebElement messageText;

    @FindBy(css = ".transferring-chat")
    private WebElement transferringChatMessage;

    @FindBy(css = "span.cl-chat-item__footer__date")
    private WebElement chatReceivingTime;

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

    public String getMessageReceivingTime() {
        return getAttributeFromElem(this.getCurrentDriver(), chatReceivingTime, 5, "receiving_time","innerText").trim();
    }

    public boolean isValidIconSentiment(String message) {
        String sentiment = ApiHelperTie.getTIESentimentOnMessage(message).toLowerCase();
        File image = new File(System.getProperty("user.dir") + "/touch/src/test/resources/sentimenticons/" + sentiment + ".png");
        return isWebElementEqualsImage(this.getCurrentDriver(), userSentiment, image);
    }

    public String getChatIconName() {
        return chatIcon.getAttribute("name").trim();
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

    public boolean isChatTransferringMessageShown() {
        return isElementShown(this.getCurrentDriver(), transferringChatMessage, 3);
    }
}