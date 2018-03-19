package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import driverManager.DriverFactory;
import interfaces.JSHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div.scrollable-roster")
public class LeftMenuWithChats extends AbstractUIElement implements JSHelper{

    @FindBy(xpath = ".//ul[@class='chats-roster']/li[not(@class='active')]")
    private List<WebElement> newConversationRequests;

    private String targetProfille = "//div[@class='profile-info']/h2[text()='%s']";

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
//                WebElement elem =  DriverFactory.getSecondDriverInstance().findElement(
//                        By.xpath(String.format(targetProfille, userName)));
//                isElementShown(DriverFactory.getSecondDriverInstance().findElement(
//                        By.xpath(String.format(targetProfille, userName))), wait);
                waitForElementToBeVisibleByXpath(String.format(String.format(targetProfille, userName), userName), wait);
                return true;
            } catch(TimeoutException e) {
                return false;
            }

//            return isElementNotShown(
//                   )
//                    ,wait);
//            try{
//                waitForElementsToBeVisible(newConversationRequests, wait);
//                return newConversationRequests.stream().anyMatch(e-> new ChatInLeftMenu(e).getUserName().equals(userName));
//            } catch (TimeoutException e) {
//                return false;
//            }
    }
}
