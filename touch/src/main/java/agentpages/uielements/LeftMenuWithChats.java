package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import driverfactory.DriverFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@FindBy(xpath = "//div[@class = 'agent-view--left-sidebar' or @class = 'supervisor-view-group-chats-by']/div[1]")
public class LeftMenuWithChats extends AbstractUIElement {

    @FindAll({
    @FindBy(css = "a[data-testid^=chat-list-item]"),
    @FindBy(css = "a[selenium-id^=chat-list-item]")
    })
    private List<WebElement> newConversationRequests;

    @FindBy(css = "[data-testid=filter-dropdown-menu]")
    private WebElement filterDropdownMenu;

    @FindBy(css = "[data-testid=filter-dropdown-menu-item]")
    private List<WebElement> filterOptions;

    @FindBy(css = "[data-testid=unread-msg-count]")
    private WebElement newConversationIcon;

    @FindBy(css = "[data-testid=roster-item]")
    private List<WebElement> chatsList;

    @FindBy(css = ".cl-chat-item--selected")
    private WebElement activeChat;

    @FindBy(css = "[data-testid=roster-scroll-container]")
    private WebElement scrollableArea;

    @FindBy(css = "[data-testid='search-filter-btn']")
    private WebElement searchButton;

    @FindAll({
            @FindBy(xpath = "//div[@class='scrollable-roster']//input"),
            @FindBy(id = "nameOrPhone")
    })
    private WebElement searchChatInput;

    @FindBy(css = "[data-testid=icon-user-single]")
    private WebElement userPicture;

    @FindBy(css = "[data-testid=unread-msg-count]")
    private WebElement userMsgCount;

    @FindBy(css = "[data-testid='tab-navigation-panel-live']")
    private WebElement liveChats;

    @FindBy(css = "[data-testid='tab-navigation-panel-tickets']")
    private WebElement tickets;

    @FindBy(css = "[data-testid='tab-navigation-panel-closed']")
    private WebElement closed;

    @FindBy(css = "[data-testid=open-filter-tab-btn]")
    private WebElement filterButton;

    @FindBy(css ="button .cl-r-button--reset-only")
    private WebElement filterRemove;

    //@FindBy (css = ".cl-message-sender .cl-message-composer .cl-message-composer__inner .cl-message-composer__tools-bar .cl-button cl-button--reset-only[selenium-id~=message-composer-location]" )
    @FindBy (css = "#Union")
    private WebElement locationButton;

    @FindAll({
            @FindBy(css = ".chats-list>.cl-empty-state"),
            @FindBy(css = ".cl-empty-state>div")
    })
    private WebElement noResultsFoundText;

    private String targetProfile = ".//div[contains(@class, 'info')]/h2[text()='%s']";

    private String loadingSpinner = ".//*[text()='Connecting...']";

    private FilterMenu filterMenu;

    private WebElement getTargetChat(String userName){
        return newConversationRequests.stream().filter(e-> new ChatInLeftMenu(e).setCurrentDriver(this.getCurrentDriver())
                .getUserName().equals(userName)).findFirst().orElseThrow(() -> new AssertionError(
                "No chat was found from: " + userName ));
    }

    public List<String> getAllFoundChatsUserNames(){
        return newConversationRequests
                .stream()
                .map(e-> new ChatInLeftMenu(e)
                                .setCurrentDriver(this.getCurrentDriver())
                                .getUserName())
                .collect(Collectors.toList());
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
        openChatByUserName(userName);
    }

    public void openChatByUserName(String userName) {
        new ChatInLeftMenu(getTargetChat(userName)).setCurrentDriver(this.getCurrentDriver()).openConversation();
    }

    public boolean isNewWebWidgetRequestIsShown(int wait) {
        return isNewConversationIsShown(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), wait);
    }

    public boolean isOvernightTicketIconShown(String userName){
        return new ChatInLeftMenu(getTargetChat(userName)).setCurrentDriver(this.getCurrentDriver()).isOvernightTicketIconShown();
    }

    public boolean isFlagIconShown(){
        return new ChatInLeftMenu(activeChat).setCurrentDriver(this.getCurrentDriver()).isFlagIconShown();
    }

    public boolean isFlagIconRemoved(){
        return new ChatInLeftMenu(activeChat).setCurrentDriver(this.getCurrentDriver()).isFlagIconRemoved();
    }

    public boolean isProfileIconNotShown(){
        return new ChatInLeftMenu(activeChat).setCurrentDriver(this.getCurrentDriver()).isProfileIconNotShown();
    }

    public boolean isOvernightTicketIconRemoved(String userName){
          for (int i = 0; i < 25; i++) {
              if(!getAllFoundChatsUserNames().contains(userName))
                  return true;
              waitFor(1000);
          }
          return false;
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

    public boolean isNewConversationIsShown(String userName, int wait) {
        return isElementShownByXpath(this.getCurrentDriver(), String.format(targetProfile, userName), wait);
    }

    public boolean isConversationRequestIsRemoved(int wait, String userName) {
        try{
            waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), String.format(String.format(targetProfile,
                    userName), userName), wait);
            return true;
        } catch(TimeoutException e) {
            return false;
        }
    }

    public void selectChatsMenu(String option){
        if (option.equalsIgnoreCase("Live Chats")){
            clickElem(this.getCurrentDriver(), liveChats, 1, "Live chats menu" );
        } else if (option.equalsIgnoreCase("Tickets")){
            clickElem(this.getCurrentDriver(), tickets, 1, "Tickets menu" );
        }else if (option.equalsIgnoreCase("Closed")) {
            clickElem(this.getCurrentDriver(), closed, 1, "Closed menu");
        } else {
            throw new AssertionError("Incorrect menu option was provided");
        }
    }

    public void searchUserChat(String userId){
        clickOnSearchButton();
        inputUserNameIntoSearch(userId);
        getTargetChat(userId).click();
    }

    public void searchTicket(String userId){
        clickOnSearchButton();
        inputUserNameIntoSearch(userId);
    }

    public void clickOnSearchButton(){
        waitForElementToBeClickable(this.getCurrentDriver(), searchButton, 1);
        executeJSclick(this.getCurrentDriver(), searchButton);
    }

    public void inputUserNameIntoSearch(String userId){
        waitForElementToBeClickable(this.getCurrentDriver(), searchChatInput, 2);
        searchChatInput.sendKeys(userId);
        searchChatInput.sendKeys(Keys.CONTROL, Keys.ENTER);
    }

    public String getNoResultsFoundMessage() {
        return getTextFromElem(this.getCurrentDriver(), this.noResultsFoundText, 8,
                "No results found text").replace("\n", " ");
    }

    public String getActiveChatUserName(){
        return new ChatInLeftMenu(activeChat).setCurrentDriver(this.getCurrentDriver()).getUserName();
    }

    public String getActiveChatLocation(){
        return new ChatInLeftMenu(activeChat).setCurrentDriver(this.getCurrentDriver()).getLocation();
    }

    public String getActiveChatLastMessage(){
        return new ChatInLeftMenu(activeChat).setCurrentDriver(this.getCurrentDriver()).getLastMessageText();
    }


    public boolean isValidImgForActiveChat(String adapter) {
      return new ChatInLeftMenu(activeChat).setCurrentDriver(this.getCurrentDriver()).isValidImg(adapter);
    }

    public boolean isValidIconSentimentForActiveChat(String message) {
         return new ChatInLeftMenu(activeChat).setCurrentDriver(this.getCurrentDriver()).isValidIconSentiment(message);
    }

//    public String getExpandFilterButtonColor() {
//        return Color.fromString(expandFilterButton.getCssValue("color")).asHex();
//    }

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
        return newConversationRequests.size();
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

    public void openFilterMenu() {
        clickElem(this.getCurrentDriver(), filterButton, 1, "Filters Button");
        filterMenu.setCurrentDriver(this.getCurrentDriver());
    }

    public  FilterMenu getFilterMenu(){
        return filterMenu;
    }

    public void applyTicketsFilters(String chanel, String sentiment, boolean flagged){
        openFilterMenu();
        if (!chanel.equalsIgnoreCase("no")) {filterMenu.chooseChannel(chanel);}
        if (!sentiment.equalsIgnoreCase("no")) {filterMenu.fillSentimentsInputField(sentiment);}
        if (flagged) {filterMenu.selectFlaggedCheckbox();}
        filterMenu.clickApplyButton();
    }

    public void applyTicketsFilters(String channel, String sentiment, LocalDate startDate, LocalDate endDate) {
        openFilterMenu();
        if (!channel.equalsIgnoreCase("no")) {filterMenu.chooseChannel(channel);}
        if (!sentiment.equalsIgnoreCase("no")) {filterMenu.fillSentimentsInputField(sentiment);}
        filterMenu.fillStartDate(startDate);
        filterMenu.fillEndDate(endDate);
        filterMenu.clickApplyButton();
    }

    public void removeFilter(){
        clickElem(this.getCurrentDriver(), filterRemove, 1, "Filter Remove button");
    }

    public boolean verifyChanelOfTheChatsIsPresent(String channelName) {
        return chatsList.stream()
                .map(e-> new ChatInLeftMenu(e).setCurrentDriver(this.getCurrentDriver()))
                .allMatch(chat -> chat.getAdapterIconName().equalsIgnoreCase(channelName));
    }
    public void clickonLocationButton(){
        clickElem(this.getCurrentDriver(), locationButton, 2,"LocationButton");
    }
}
