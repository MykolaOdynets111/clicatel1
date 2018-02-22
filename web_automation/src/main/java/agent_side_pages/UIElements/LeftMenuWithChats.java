package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import interfaces.JSHelper;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div.scrollable-roster")
public class LeftMenuWithChats extends AbstractUIElement implements JSHelper{

    @FindBy(xpath = ".//ul[@class='chats-roster']/li[not(@class='active')]")
    private List<WebElement> newConversationRequests;

    private WebElement getTargetChat(String userName){
        return newConversationRequests.stream().filter(e-> new ChatInLeftMenu(e).getUserName().equals(userName)).findFirst().get();
    }

    public void openNewConversationRequest() {
        String userName = getUserNameFromLocalStorage();
//        waitForElementToBeVisible(newConversationRequest);
        new ChatInLeftMenu(getTargetChat(userName)).openConversation();
    }


    public boolean isNewConversationRequestIsShown(int wait) {
            String userName = getUserNameFromLocalStorage();
            try{
                waitForElementsToBeVisible(newConversationRequests, wait);
                return newConversationRequests.stream().anyMatch(e-> new ChatInLeftMenu(e).getUserName().equals(userName));
            } catch (TimeoutException e) {
                return false;
            }
    }
}
