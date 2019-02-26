package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import interfaces.JSHelper;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

@FindBy(css = "div.scrollable-roster")
public class LeftMenuWithChats extends AbstractUIElement implements JSHelper{

    private String sixthChat = "(.//ul[@class='chats-roster']/li[not(@class='active')])[9]";

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

    @FindBy(xpath = "//div[@class='Select-control']//input")
    private WebElement searchChatInput;

    private String targetProfile = "//div[@class='profile-info']/h2[text()='%s']";

    private String loadingSpinner = "//*[text()='Connecting...']";

    private WebElement getTargetChat(String userName){
        return newConversationRequests.stream().filter(e-> new ChatInLeftMenu(e).getUserName().equals(userName)).findFirst().get();
    }



    public void openNewConversationRequestByAgent(String agent) {
        String userName = getUserNameFromLocalStorage();
        try {
            new ChatInLeftMenu(getTargetChat(userName)).openConversation();
        } catch(WebDriverException|NoSuchElementException e){
            Assert.assertTrue(false, "Chat for '"+userName+"' disappears from chat desk when tries to open it.\n" +
                    "UserID: "+ getUserNameFromLocalStorage());
        }
    }

    public void openNewFromSocialConversationRequest(String userName) {
        new ChatInLeftMenu(getTargetChat(userName)).openConversation();
    }

    public boolean isNewConversationRequestIsShown(int wait, String agent) {
        String userName = getUserNameFromLocalStorage();
        try{
            waitForElementToBeVisibleByXpathAgent(String.format(targetProfile, userName), wait, agent);
            return true;
        } catch(TimeoutException e) {
            return false;
        }
    }

    public boolean isOvernightTicketIconShown(String userName){
        return new ChatInLeftMenu(getTargetChat(userName)).isOvernightTicketIconShown();
    }


    public boolean isOvernightTicketIconRemoved(String userName){
        return new ChatInLeftMenu(getTargetChat(userName)).isOvernightTicketRemoved();
    }

    public boolean isNewConversationRequestFromSocialShownByChannel(String userName, String channel, int wait){
        try{
            waitForElementToBeVisible(newConversationIcon, wait);
            return  newConversationRequests.stream().
                    anyMatch(e-> new ChatInLeftMenu(e).getUserName().equals(userName)
                                &  new ChatInLeftMenu(e).getChatsChannel().equalsIgnoreCase(channel));
        } catch(TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    public boolean isNewConversationRequestFromSocialIsShown(String userName, int wait, String agent) {
        try{
            waitForElementToBeVisibleByXpathAgent(String.format(targetProfile, userName), wait, agent);
            return true;
        } catch(TimeoutException e) {
            return false;
        }
    }

    public boolean isConversationRequestIsRemoved(int wait) {
        String userName = getUserNameFromLocalStorage();
        try{
            waitForElementToBeInVisibleByXpathAgent(String.format(String.format(targetProfile, userName), userName), wait);
            return true;
        } catch(TimeoutException e) {
            return false;
        }
    }

    public void selectFilterOption(String option){
        expandFilterButton.click();
        waitForElementToBeVisibleAgent(filterDropdownMenu, 4);
        filterOptions.stream()
                .filter(e -> e.getText().toLowerCase().contains(option.toLowerCase()))
                .findFirst().get()
                .click();
    }

    public void selectRandomChat(String agent){
        waitForElementToBePresentByXpathAgent(sixthChat, 8);
       List<ChatInLeftMenu> chatsFromWidget =
                                newConversationRequests.stream()
                                .map(e -> new ChatInLeftMenu(e))
                                .filter(e1 -> e1.getUserName().startsWith("test"))
                                .collect(Collectors.toList());
        int numberOfChats = chatsFromWidget.size();
        Random rand = new Random();
        int max = numberOfChats - 1;
        int randomNum = rand.nextInt((max - 0) + 1);
        String userName = chatsFromWidget.get(randomNum).getUserName();
        searchChatInput.sendKeys(userName);
        searchChatInput.sendKeys(Keys.ENTER);
        getTargetChat(userName).click();
    }

    public String getActiveChatUserName(){
        return new ChatInLeftMenu(activeCaht).getUserName();
    }

    public String getActiveChatLocation(){
        return new ChatInLeftMenu(activeCaht).getLocation();
    }
}
