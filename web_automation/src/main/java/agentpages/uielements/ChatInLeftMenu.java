package agentpages.uielements;

import apihelper.ApiHelperTie;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

//    public boolean isValidImgAshot(String adapter) {
//        File image = new File("src/test/resources/adaptericons/" + adapter + ".png");
//        try {
//           // WebElement wel=findElemByXPATHAgent("//span[contains(@class,'http-icon')]//span/*","main");
//            WebElement wel=DriverFactory.getDriverForAgent("main").findElement(By.xpath("//span[contains(@class,'http-icon')]//span/*"));
//            Screenshot fpScreenshot2 =  new AShot().takeScreenshot(DriverFactory.getDriverForAgent("main"),wel);
//            ImageIO.write(fpScreenshot2.getImage(), "PNG", new File("src/test/resources/adaptericonsA/2.png"));
//            Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(DriverFactory.getDriverForAgent("main"), adapterIcon);//.shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(DriverFactory.getDriverForAgent("main"));
//            ImageIO.write(fpScreenshot.getImage(), "PNG", new File("src/test/resources/adaptericonsA/2.png"));
//            ImageIO.write(fpScreenshot2.getImage(), "PNG", new File("src/test/resources/adaptericonsA/22.png"));
//           // new AShot().takeScreenshot(DriverFactory.getDriverForAgent("main"), findElemByXPATHAgent("//span[contains(@class,'http-icon')]//span/*","main"));
//        } catch (Exception e){
//
//        }
//      //  return isWebElementEqualsImageAshot(adapterIcon,image,adapter);
//       // new AShot().takeScreenshot(DriverFactory.getDriverForAgent("main"), findElemByXPATHAgent("//span[contains(@class,'http-icon')]//span/*","main"))
//        return true;
//    }

    public boolean isValidImg(String adapter) {
        File image = new File("src/test/resources/adaptericons/" + adapter + ".png");
          return isWebElementEqualsImage(adapterIcon,image);

    }


    public boolean isValidIconSentiment(String message){
        String sentiment = ApiHelperTie.getTIESentimentOnMessage(message).toLowerCase();
        File image =new File("src/test/resources/sentimenticons/"+sentiment+".png");
        isWebElementEqualsImage(userSentiment,image);
        return true;
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