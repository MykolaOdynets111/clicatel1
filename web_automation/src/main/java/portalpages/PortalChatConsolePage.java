package portalpages;

import drivermanager.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.uielements.EditUserRolesWindow;

import java.io.File;
import java.util.Map;

public class PortalChatConsolePage extends PortalAbstractPage {

    @FindBy(xpath = "//p[text()='Chats waiting in your queue']/following-sibling::div[@class='average-number--blue ng-binding']")
    private WebElement chatsWaitingCounter;

    @FindBy(xpath = "//p[text()='Live chats active']/following-sibling::div[@class='average-number--blue ng-binding']")
    private WebElement liveChatsCounter;

    @FindBy(xpath = "//p[text()='Agents online']/following-sibling::div[@class='average-number--blue ng-binding']")
    private WebElement agentsOnlineCounter;

    public String getWainingChatsNumber(){
        return chatsWaitingCounter.getText();
    }

    public String getLiveChatsNumber(){
        return liveChatsCounter.getText();
    }

    public String getAgentsOnlineNumber(){
        return agentsOnlineCounter.getText();
    }

}
