package agentpages.uielements;

import apihelper.ApiHelperTie;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import drivermanager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

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
        @FindBy(xpath = "//div[@class='icons']/span/span[contains(@class,'icon svg')]"),
        @FindBy(xpath = "//div[@class='context-info']/div[@class='icons']/span[contains(@class,'icon svg')]")
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

    public void isValidImg(String adapter) throws Exception {
        File image =new File("src/test/resources/adaptericons/"+adapter+".png");
        BufferedImage expectedImage = ImageIO.read(image);
        boolean result = Shutterbug.shootElement(DriverFactory.getDriverForAgent("main"),adapterIcon,true ).withName("Actual").equals(expectedImage);
        Assert.assertTrue(result,"Image in last message in left menu for "+adapter+ " adapter as not expected. \n");
    }

    public void createValidImg(String adapter) throws Exception {
        Shutterbug.shootElement(DriverFactory.getDriverForAgent("main"),adapterIcon,true ).withName(adapter).save("src/test/resources/sentimenticons/");
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

    public void isValidIconSentiment(String message) throws Exception{
        String sentiment = ApiHelperTie.getTIESentimentOnMessage(message).toLowerCase();
        File image =new File("src/test/resources/sentimenticons/"+sentiment+".png");
        BufferedImage expectedImage = ImageIO.read(image);
        boolean result = Shutterbug.shootElement(DriverFactory.getDriverForAgent("main"),userSentiment,true ).withName("Actual").equals(expectedImage,0.1);
        Assert.assertTrue(result,"Image in last message in left menu for sentiment as not expected. \n");
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