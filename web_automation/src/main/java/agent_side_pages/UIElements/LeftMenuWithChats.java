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

    public void openNewConversationRequest(String userName) {
        new ChatInLeftMenu(getTargetChat(userName)).openConversation();
    }



    public boolean isNewConversationRequestFromTouchIsShown(int wait) {
            String userName = getUserNameFromLocalStorage();
            try{
                waitForElementToBeVisibleByXpathAgent(String.format(String.format(targetProfile, userName), userName), wait);
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
}
