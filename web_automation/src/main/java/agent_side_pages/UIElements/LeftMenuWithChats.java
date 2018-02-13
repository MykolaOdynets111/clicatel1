package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.scrollable-roster")
public class LeftMenuWithChats extends AbstractUIElement {

    @FindBy(xpath = ".//ul[@class='chats-roster']/li[not(@class='active')]")
    private WebElement newConversationRequest;

    public void openNewConversationRequest() {
        waitForElementToBeVisible(newConversationRequest);
        new ChatInLeftMenu(newConversationRequest).openConversation();
//        if(newConversationRequest.isDisplayed()) {
//            new ChatInLeftMenu(newConversationRequest).openConversation();
//        }
        int a = 2;
    }

    public boolean isNewConversationRequestIsShown(int wait) {
            try{
                waitForElementToBeVisible(newConversationRequest, wait);
                return true;
            } catch (TimeoutException e) {
                return false;
            }
    }
}
