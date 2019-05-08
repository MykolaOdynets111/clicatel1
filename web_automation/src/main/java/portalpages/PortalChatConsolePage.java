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

    @FindBy(xpath = "//p[text()='Live chats active']//following-sibling::p/b")
    private WebElement averageChatsPerAgent;

    public String getWaitingChatsNumber(){
        return getTextFromElemAgent(chatsWaitingCounter, 3, "admin", "Customers waiting for response");
    }

    public String getLiveChatsNumber(){
        return getTextFromElemAgent(liveChatsCounter, 3, "admin", "Customer engaging with an Agent");
    }

    public String getAgentsOnlineNumber(){
        return getTextFromElemAgent(agentsOnlineCounter, 3, "admin", "Total Agents online");
    }

    public String getWidgetValue(String value){
        switch (value) {
            case "Customers waiting for response":
                return getWaitingChatsNumber();
            case "Customer engaging with an Agent":
                return getLiveChatsNumber();
            case "Total Agents online":
                return getAgentsOnlineNumber();
        }
        return "invalid widget name";
    }

    public String getAverageChatsPerAgent(){
        return getTextFromElemAgent(averageChatsPerAgent, 3, "admin", "Average chats per Agent");
    }

}
