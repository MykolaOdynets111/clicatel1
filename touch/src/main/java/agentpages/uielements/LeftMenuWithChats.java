package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import driverfactory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@FindBy(css = "[data-testid='routed-tabs']")
public class LeftMenuWithChats extends AbstractUIElement {

    @FindBy(css = "a[data-testid^=chat-list-item]")
    private List<WebElement> newConversationRequests;

    @FindBy(xpath = "//button[text() = 'Continue']")
    private WebElement continueButton;

    @FindBy(xpath = "//button[text() = 'Wait, I want to stay here!']")
    private WebElement waitAndStayButton;

    @FindBy(css = ".cl-routed-tabs__tab--selected")
    private WebElement selectedTab;

    @FindAll({
            @FindBy(css = "[data-testid=roster-item]"),
            @FindBy(css = "[selenium-id=roster-item]") //toDo old locator
    })
    private List<WebElement> chatsList;

    @FindBy(css = ".cl-chat-item--selected")
    private WebElement activeChat;

    @FindBy(css = "[data-testid = 'bulk-messages-toggle']")
    private WebElement bulkButton;

    @FindBy(css = ".cl-chat-item-bulk-message-checkbox label")
    private WebElement bulkPanelChat;

    @FindBy(css = ".cl-chat-item-bulk-message-checkbox label")
    private List<WebElement> bulkPanelChatItems;

    @FindBy(css = "[data-testid = 'chats-list-scroll-container']")
    private WebElement chatsScrollBar;

    @FindBy(xpath = "//input[contains(@class, 'checkbox__input')]")
    private WebElement bulkChatCheckbox;

    @FindAll({
            @FindBy(css = "[data-testid='search-filter-btn']"),
            @FindBy(css = "[selenium-id='search-filter-btn']") //toDo old locator
    })
    private WebElement searchButton;

    @FindAll({
            @FindBy(css = "[data-testid='search-filter-input']"),
            @FindBy(css = "[selenium-id='search-filter-input']")//toDo old locator
    })
    private WebElement searchChatInput;

    @FindAll({
            @FindBy(css = "[data-testid=icon-user-single]"),
            @FindBy(css = "[selenium-id=icon-user-single]") //toDo old locator
    })
    private WebElement userPicture;

    @FindAll({
            @FindBy(css = "[data-testid=unread-msg-count]"),
            @FindBy(css = "[selenium-id=unread-msg-count]") //toDo old locator
    })
    private WebElement userMsgCount;

    @FindAll({
            @FindBy(css = "[data-testid='tab-navigation-panel-live']"),
            @FindBy(css = "[selenium-id='tab-navigation-panel-live']") //toDo old locator
    })
    private WebElement liveChats;

    @FindAll({
            @FindBy(css = "[data-testid='tab-navigation-panel-tickets']"),
            @FindBy(css = "[selenium-id='tab-navigation-panel-tickets']")
    })
    private WebElement tickets;

    @FindAll({
            @FindBy(css = "[data-testid='tab-navigation-panel-closed']"),
            @FindBy(css = "[selenium-id='tab-navigation-panel-closed']") //toDo old locator
    })
    private WebElement closed;

    @FindAll({
            @FindBy(css = "[data-testid='tab-navigation-panel-pending']"),
            @FindBy(css = "[selenium-id='tab-navigation-panel-pending']") //toDo old locator
    })
    private WebElement pending;

    @FindAll({
            @FindBy(css = "[data-testid=open-filter-tab-btn]"),
            @FindBy(css = "[selenium-id=open-filter-tab-btn]") //toDo old locator
    })
    private WebElement filterButton;

    @FindBy(css = "button .cl-r-button--reset-only")
    private WebElement filterRemove;

    @FindBy(css = ".cl-chats-group-item__inner ")
    private List<WebElement> filterOptions;

    @FindAll({
            @FindBy(css = ".chats-list>.cl-empty-state"),
            @FindBy(css = ".cl-empty-state>div")
    })
    private WebElement noResultsFoundText;

    private String targetProfile = ".//div[contains(@class, 'info')]/h2[text()='%s']";
    private String loadingSpinner = ".//*[text()='Connecting...']";

    private FilterMenu filterMenu;

    private int count = 0;

    private WebElement getTargetChat(String userName) {
        return newConversationRequests.stream().filter(e -> new ChatInLeftMenu(e)
                .setCurrentDriver(this.getCurrentDriver())
                .getUserName().equals(userName))
                .findFirst().orElseThrow(() ->
                        new AssertionError("No chat was found from: " + userName));
    }

    public List<String> getAllFoundChatsUserNames() {
        return newConversationRequests.stream()
                .map(e -> new ChatInLeftMenu(e)
                        .setCurrentDriver(this.getCurrentDriver())
                        .getUserName())
                .collect(Collectors.toList());
    }

    public void openNewConversationRequestByAgent() {
        String userName = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
        try {
            new ChatInLeftMenu(getTargetChat(userName)).setCurrentDriver(this.getCurrentDriver()).openConversation();
        } catch (WebDriverException | NoSuchElementException e) {
            Assert.fail("Chat for '" + userName + "' disappears from chat desk when tries to open it.\n" +
                    "UserID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        }
    }

    public void openChatByUserName(String userName) {
        new ChatInLeftMenu(getTargetChat(userName)).setCurrentDriver(this.getCurrentDriver()).openConversation();
    }

    public void openFirstChat() {
        newConversationRequests.get(0).click();
    }

    public void clickBulkButton() {
        bulkButton.click();
    }

    public boolean isBulkPanelEnabled(String isEnabled) {
        return getAttributeFromElem(this.getCurrentDriver(), bulkPanelChat, 5, "Bulk panel", "class").contains(isEnabled);
    }

    public Boolean isNumberOfCheckedChats(int numberOfCheckedBulkChats ) {
        return count == numberOfCheckedBulkChats;
    }

    public String getBulkChatsButtonSelectedStatus() {
        return getAttributeFromElem(this.getCurrentDriver(), bulkButton, 5, "Bulk chats button", "class");
    }

    public void scrollLeftPane() {
        while (bulkPanelChatItems.size() <= 30) {
            wheelScroll(this.getCurrentDriver(), chatsScrollBar, 950, 0,0);
            waitFor(1000);
            //scrollToElem(this.getCurrentDriver(), bulkPanelChatItems.get(bulkPanelChatItems.size() - 1), "Bulk panel checkbox");
        }
    }

    public boolean bulkPanelElementsClickWithoutScroll(int bulkCheckedChats) {
        waitFor(1500);
        waitForElementToBeVisible(this.getCurrentDriver(), bulkPanelChatItems.get(0), 5);
        for (WebElement webElement : bulkPanelChatItems) {
            if (!getAttributeFromElem(this.getCurrentDriver(), webElement, 5, "Bulk panel", "class").contains("disabled")) {
                webElement.findElement(By.tagName("span")).click();
                count++;
                System.out.println("Clicking the bulk checkbox");
            }

            if(count == bulkCheckedChats) {
                return true;
            }
        }
        return false;
    }

    public boolean bulkPanelElementsClick(int bulkCheckedChats) {
        scrollLeftPane();
        wheelScroll(this.getCurrentDriver(), chatsScrollBar, -5500, 0, 0);
        waitFor(2500);
        waitForElementsToBeVisible(this.getCurrentDriver(), bulkPanelChatItems, 5);

        for (WebElement webElement : bulkPanelChatItems) {
                scrollToElem(this.getCurrentDriver(), webElement, "Bulk panel checkbox");
                waitFor(1500);
                if (!getAttributeFromElem(this.getCurrentDriver(), webElement, 5, "Bulk panel", "class").contains("disabled")) {
                    webElement.findElement(By.tagName("span")).click();
                    count++;
                    System.out.println("Clicking the bulk checkbox");
                }

                if(count == bulkCheckedChats) {
                    return true;
                }
            }
        return false;
    }

    public boolean isNewWebWidgetRequestIsShown(int wait) {
        return isNewConversationIsShown(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), wait);
    }

    public boolean isOvernightTicketIconShown(String userName) {
        return new ChatInLeftMenu(getTargetChat(userName)).setCurrentDriver(this.getCurrentDriver()).isOvernightTicketIconShown();
    }

    public boolean isFlagIconShown() {
        return getChatInLeftMenu().isFlagIconShown();
    }

    public boolean isFlagIconRemoved() {
        return getChatInLeftMenu().isFlagIconRemoved();
    }

    public boolean isProfileIconNotShown() {
        return getChatInLeftMenu().isProfileIconNotShown();
    }

    public boolean isOvernightTicketIconRemoved(String userName) {
        for (int i = 0; i < 25; i++) {
            if (!getAllFoundChatsUserNames().contains(userName))
                return true;
            waitFor(1000);
        }
        return false;
    }

    public boolean isNewConversationIsShown(String userName, int wait) {
        return isElementShownByXpath(this.getCurrentDriver(), String.format(targetProfile, userName), wait);
    }

    public boolean isConversationRequestIsRemoved(int wait, String userName) {
        try {
            waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), String.format(String.format(targetProfile,
                    userName), userName), wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void selectChatsMenu(String option) {
        if (option.equalsIgnoreCase("Live Chats")) {
            clickElem(this.getCurrentDriver(), liveChats, 1, "Live chats menu");
        } else if (option.equalsIgnoreCase("Tickets")) {
            clickElem(this.getCurrentDriver(), tickets, 1, "Tickets menu");
        } else if (option.equalsIgnoreCase("Closed")) {
            clickElem(this.getCurrentDriver(), closed, 1, "Closed menu");
        } else if (option.equalsIgnoreCase("Pending")) {
            clickElem(this.getCurrentDriver(), pending, 1, "Closed menu");
        } else {
            throw new AssertionError("Incorrect menu option was provided");
        }
    }

    public boolean searchUserChat(String userId) {
        clickOnSearchButton();
        int wait = 10;

        for (int i = 0; i < wait; i++) {
            try {
                inputUserNameIntoSearch(userId);
                getTargetChat(userId).click();
                if (newConversationRequests.size() > 0) {
                    return true;
                }
            } catch (AssertionError | Exception e) {
                System.out.println("No chats are there after search, chat has not reached yet, so retrying searching");
                waitFor(1000);
            }
        }
        return false;
    }

    public void clickCloseButton() {
        filterMenu.clickCloseFiltersButton();
    }

    public void searchTicket(String userId) {
        clickOnSearchButton();
        inputUserNameIntoSearch(userId);
    }

    public void clickOnSearchButton() {
        waitForElementToBeClickable(this.getCurrentDriver(), searchButton, 1);
        executeJSclick(this.getCurrentDriver(), searchButton);
    }

    public void inputUserNameIntoSearch(String userId) {
        waitForElementToBeClickable(this.getCurrentDriver(), searchChatInput, 2);
        searchChatInput.sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
        searchChatInput.sendKeys(userId);
        searchChatInput.sendKeys(Keys.CONTROL, Keys.ENTER);
    }

    public String getNoResultsFoundMessage() {
        return getTextFromElem(this.getCurrentDriver(), this.noResultsFoundText, 8,
                "No results found text").replace("\n", " ");
    }

    public String getCurrentSelectedTabText() {
        return getTextFromElem(this.getCurrentDriver(), selectedTab, 4,
                "Current selected tab text");
    }

    public String getActiveChatUserName() {
        return getChatInLeftMenu().getUserName();
    }

    public String getActiveChatLocation() {
        return getChatInLeftMenu().getLocation();
    }

    public String getActiveChatLastMessage() {
        return getChatInLeftMenu().getLastMessageText();
    }

    public String getActiveChatReceivingTime() {
        return getChatInLeftMenu().getMessageReceivingTime();
    }

    public String getChatIconName() {
        return getChatInLeftMenu().getChatIconName();
    }

    public boolean isValidIconSentimentForActiveChat(String message) {
        return getChatInLeftMenu().isValidIconSentiment(message);
    }

    public String getUserPictureColor() {
        return Color.fromString(userPicture.getCssValue("background-color")).asHex();
    }

    public String getUserMsgCountColor() {
        waitForElementToBeVisible(this.getCurrentDriver(), userMsgCount, 10);
        String hexColor = Color.fromString(userMsgCount.getCssValue("background-color")).asHex();
        userMsgCount.click();
        return hexColor;
    }

    public int getNewChatsCount() {
        return newConversationRequests.size();
    }

    public void waitForAllChatsToDisappear(int secondsWait) {
        int size = chatsList.size();
        for (int i = 0; i < secondsWait; i++) {
            if (size == 0) break;
            else {
                waitFor(i * 1000);
                size = chatsList.size();
            }
        }
    }

    public boolean waitForConnectingDisappear(int waitForSpinnerToAppear, int waitForSpinnerToDisappear) {
        try {
            try {
                waitForElementToBeVisibleByXpath(this.getCurrentDriver(), loadingSpinner, waitForSpinnerToAppear);
            } catch (TimeoutException e) {
            }
            waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), loadingSpinner, waitForSpinnerToDisappear);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void openFilterMenu() {
        clickElem(this.getCurrentDriver(), filterButton, 1, "Filters Button");
        filterMenu.setCurrentDriver(this.getCurrentDriver());
    }

    public FilterMenu getFilterMenu() {
        return filterMenu;
    }

    public void applyTicketsFilters(String chanel, String sentiment, boolean flagged) {
        openFilterMenu();
        if (!chanel.equalsIgnoreCase("no")) {
            filterMenu.chooseChannel(chanel);
        }
        if (!sentiment.equalsIgnoreCase("no")) {
            filterMenu.fillSentimentsInputField(sentiment);
        }
        if (flagged) {
            filterMenu.selectFlaggedCheckbox();
        }
        filterMenu.clickApplyButton();
    }

    public void applyTicketsFilters(String channel, String sentiment, LocalDate startDate, LocalDate endDate) {
        openFilterMenu();
        if (!channel.equalsIgnoreCase("no")) {
            filterMenu.chooseChannel(channel);
        }
        if (!sentiment.equalsIgnoreCase("no")) {
            filterMenu.fillSentimentsInputField(sentiment);
        }
        filterMenu.fillStartDate(startDate);
        filterMenu.fillEndDate(endDate);
        filterMenu.clickApplyButton();
    }

    public String checkStartDateFilterEmpty() {
        openFilterMenu();
        return filterMenu.isStartDateIsEmpty();
    }

    public void removeFilter() {
        clickElem(this.getCurrentDriver(), filterRemove, 1, "Filter Remove button");
    }

    public boolean verifyChanelOfTheChatsIsPresent(String channelName) {
        return chatsList.stream()
                .map(e -> new ChatInLeftMenu(e).setCurrentDriver(this.getCurrentDriver()))
                .allMatch(chat -> chat.getChatIconName().equalsIgnoreCase(channelName));
    }

    public void clickContinueButton(){
        clickElem(this.getCurrentDriver(), continueButton, 1, "Continue Button");
    }

    public void clickWaitAndStayButton(){
        clickElem(this.getCurrentDriver(), waitAndStayButton, 1, "Wait And Stay Button");
    }

    public LeftMenuWithChats selectOption(String name) {
         getFilterOptionByName(name).click();
         return this;
    }

    public LeftMenuWithChats verifyChatTransferringShown() {
        getChatInLeftMenu().isChatTransferringMessageShown();
        return this;
    }

    private WebElement getFilterOptionByName(String name) {
        return filterOptions.stream()
                .filter(f -> f.findElement(By.cssSelector(".cl-chats-group-item__name")).getText().contains(name))
                .findFirst().orElseThrow(() -> new NoSuchElementException("There is no value with name: " + name));
    }

    private ChatInLeftMenu getChatInLeftMenu() {
        return new ChatInLeftMenu(activeChat).setCurrentDriver(this.getCurrentDriver());
    }

    public void hoverBulkChatButton() {
        waitForElementToBeVisible(this.getCurrentDriver(), bulkButton, 5);
        moveToElement(this.getCurrentDriver(), bulkButton);
    }
}
