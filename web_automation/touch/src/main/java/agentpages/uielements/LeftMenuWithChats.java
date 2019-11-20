package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import apihelper.ApiHelper;
import driverfactory.DriverFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@FindBy(css = "div.scrollable-roster")
public class LeftMenuWithChats extends AbstractUIElement {

    private String tenthChat = "(.//ul[@class='chats-roster']/li[not(@class='active')])[9]";

    @FindBy(xpath = ".//ul[@class='chats-roster']/li[not(@class='active')]")
    private List<WebElement> newConversationRequests;

    @FindBy(css = "span.icon.icon-expand")
    private WebElement expandFilterButton;

    @FindBy(css = "ul.dropdown-menu")
    private WebElement filterDropdownMenu;

    @FindBy(css = "ul.dropdown-menu a")
    private List<WebElement> filterOptions;

    @FindBy(xpath = "//div[@class='profile-info']/span[@class='msg-count']")
    private WebElement newConversationIcon;

    @FindBy(xpath = ".//li/div[@class='roster-item']")
    private List<WebElement> chatsList;

    @FindBy(xpath = ".//ul[@class='chats-roster']/li[@class='active']")
    private WebElement activeCaht;

    @FindBy(css = "div.scrollable-content")
    private WebElement scrollableArea;

    @FindBy(xpath = "//div[@class='scrollable-roster']//input")
    private WebElement searchChatInput;

    @FindBy(xpath = "//div[@class='empty-icon no-border']")
    private WebElement userPicture;

    @FindBy(xpath = "//span[@class='msg-count']")
    private WebElement userMsgCount;

    private String targetProfile = "//div[@class='profile-info']/h2[text()='%s']";

    private String loadingSpinner = "//*[text()='Connecting...']";

    private WebElement getTargetChat(String userName){
        return newConversationRequests.stream().filter(e-> new ChatInLeftMenu(e).setCurrentDriver(this.getCurrentDriver())
                .getUserName().equals(userName)).findFirst().get();
    }

    private WebElement getActiveTargetChat(String userName){
        return chatsList.stream().filter(e-> new ChatInLeftMenu(e).setCurrentDriver(this.getCurrentDriver())
                .getUserName().equals(userName)).findFirst().get();
    }

    public void openNewConversationRequestByAgent() {
        String userName = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
        try {
            new ChatInLeftMenu(getTargetChat(userName)).setCurrentDriver(this.getCurrentDriver()).openConversation();
        } catch(WebDriverException|NoSuchElementException e){
            Assert.fail("Chat for '"+userName+"' disappears from chat desk when tries to open it.\n" +
                    "UserID: "+ getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        }
    }

    public void openNewFromSocialConversationRequest(String userName) {
        new ChatInLeftMenu(getTargetChat(userName)).setCurrentDriver(this.getCurrentDriver()).openConversation();
    }

    public boolean isNewConversationRequestIsShown(int wait) {
        String userName = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
        try{
            waitForElementToBeVisibleByXpath(this.getCurrentDriver(), String.format(targetProfile, userName), wait);
            return true;
        } catch(TimeoutException e) {
            return false;
        }
    }

    public boolean isOvernightTicketIconShown(String userName){
        return new ChatInLeftMenu(getTargetChat(userName)).setCurrentDriver(this.getCurrentDriver()).isOvernightTicketIconShown();
    }

    public boolean isFlagIconShown(String userName){
        return new ChatInLeftMenu(getActiveTargetChat(userName)).setCurrentDriver(this.getCurrentDriver()).isFlagIconShown();
    }

    public boolean isFlagIconRemoved(String userName){
        return new ChatInLeftMenu(getActiveTargetChat(userName)).setCurrentDriver(this.getCurrentDriver()).isFlagIconRemoved();
    }

    public boolean isProfileIconNotShown(String userName){
        return new ChatInLeftMenu(getActiveTargetChat(userName)).setCurrentDriver(this.getCurrentDriver()).isProfileIconNotShown();
    }

    public boolean isOvernightTicketIconRemoved(String userName){
        try {
            return new ChatInLeftMenu(getTargetChat(userName)).setCurrentDriver(this.getCurrentDriver()).isOvernightTicketRemoved();
        } catch(NoSuchElementException e){
            return true;
        }
    }

    public boolean isNewConversationRequestFromSocialShownByChannel(String userName, String channel, int wait){
        try{
            waitForElementToBeVisible(this.getCurrentDriver(), newConversationIcon, wait);
            return  newConversationRequests.stream().
                    anyMatch(e-> new ChatInLeftMenu(e).setCurrentDriver(this.getCurrentDriver()).getUserName().equals(userName)
                                &  new ChatInLeftMenu(e).setCurrentDriver(this.getCurrentDriver()).getChatsChannel().equalsIgnoreCase(channel));
        } catch(TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    public boolean isNewConversationRequestFromSocialIsShown(String userName, int wait) {
        return isElementShownByXpath(this.getCurrentDriver(), String.format(targetProfile, userName), wait);
    }

    public boolean isConversationRequestIsRemoved(int wait) {
        String userName = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
        try{
            waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), String.format(String.format(targetProfile,
                    userName), userName), wait);
            return true;
        } catch(TimeoutException e) {
            return false;
        }
    }

    public void selectFilterOption(String option){
        expandFilterButton.click();
        waitForElementToBeVisible(this.getCurrentDriver(), filterDropdownMenu, 4);
        filterOptions.stream()
                .filter(e -> e.getText().toLowerCase().contains(option.toLowerCase()))
                .findFirst().get()
                .click();
    }

    public List<String> getFilterOption(){
        expandFilterButton.click();
        waitForElementToBeVisible(this.getCurrentDriver(), filterDropdownMenu, 10);
        return filterOptions.stream().map(e -> e.getText()).collect(Collectors.toList());
    }

    public void searchUserChat(String userId){
        waitForElementToBeClickable(this.getCurrentDriver(), searchChatInput, 8);
        searchChatInput.sendKeys(userId);
        searchChatInput.sendKeys(Keys.ENTER);
        getTargetChat(userId).click();
    }

    public String getActiveChatUserName(){
        return new ChatInLeftMenu(activeCaht).setCurrentDriver(this.getCurrentDriver()).getUserName();
    }

    public String getActiveChatLocation(){
        return new ChatInLeftMenu(activeCaht).setCurrentDriver(this.getCurrentDriver()).getLocation();
    }

    public String getActiveChatLastMessage(){
        return new ChatInLeftMenu(activeCaht).setCurrentDriver(this.getCurrentDriver()).getLastMessageText();
    }


    public boolean isValidImgForActiveChat(String adapter) {
      return new ChatInLeftMenu(activeCaht).setCurrentDriver(this.getCurrentDriver()).isValidImg(adapter);
    }

    public boolean isValidIconSentimentForActiveChat(String message) {
         return new ChatInLeftMenu(activeCaht).setCurrentDriver(this.getCurrentDriver()).isValidIconSentiment(message);
    }

    public String getExpandFilterButtonColor() {
        return Color.fromString(expandFilterButton.getCssValue("color")).asHex();
    }

    public String getUserPictureColor() {
        return Color.fromString(userPicture.getCssValue("background-color")).asHex();
    }

    public String getUserMsgCountColor() {
        waitForElementToBeVisible(this.getCurrentDriver(), userMsgCount,10);
        String hexColor = Color.fromString(userMsgCount.getCssValue("background-color")).asHex();
        userMsgCount.click();
        return hexColor;
    }

    public int getNewChatsCount(){
        return  newConversationRequests.size();
    }

    public void waitForAllChatsToDisappear(int secondsWait){
        int size = chatsList.size();
        for(int i =0; i<secondsWait; i++){
            if(size==0) break;
            else{
                waitFor(i*1000);
                size = chatsList.size();
            }
        }
    }

    public boolean waitForConnectingDisappear(int waitForSpinnerToAppear, int waitForSpinnerToDisappear){
        try{
            try {
                waitForElementToBeVisibleByXpath(this.getCurrentDriver(), loadingSpinner, waitForSpinnerToAppear);
            }catch (TimeoutException e){ }
            waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), loadingSpinner, waitForSpinnerToDisappear);
            return true;
        }
        catch (TimeoutException e){
            return false;
        }
    }
}
