package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import dataprovider.FacebookUsers;
import interfaces.JSHelper;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div.scrollable-roster")
public class LeftMenuWithChats extends AbstractUIElement implements JSHelper{

    @FindBy(xpath = ".//ul[@class='chats-roster']/li[not(@class='active')]")
    private List<WebElement> newConversationRequests;

    private String targetProfile = "//div[@class='profile-info']/h2[text()='%s']";

    private WebElement getTargetChat(String userName){
        return newConversationRequests.stream().filter(e-> new ChatInLeftMenu(e).getUserName().equals(userName)).findFirst().get();
    }

    public void openNewConversationRequest() {
        String userName = getUserNameFromLocalStorage();
        new ChatInLeftMenu(getTargetChat(userName)).openConversation();
    }

    public void openNewConversationRequest(String userName) {
        new ChatInLeftMenu(getTargetChat(userName)).openConversation();
    }
//    public boolean isNewConversationRequestIsShown(int wait) {
//            String userName = getUserNameFromLocalStorage();
//            try{
//                waitForElementToBeVisibleByXpathAgent(String.format(String.format(targetProfille, userName), userName), wait);
//                return true;
//            } catch(TimeoutException e) {
//                return false;
//            }
//    }


    public boolean isNewConversationRequestIsShown(int wait, String agent) {
        String userName = getUserNameFromLocalStorage();
        try{
            waitForElementToBeVisibleByXpathAgent(String.format(String.format(targetProfile, userName), userName), wait, agent);
            return true;
        } catch(TimeoutException e) {
            return false;
        }
    }

    public boolean isNewConversationRequestFromSocialIsShown(String userName, int wait) {
        try{
            waitForElementToBeVisibleByXpathAgent(String.format(targetProfile, userName), wait);
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
}
