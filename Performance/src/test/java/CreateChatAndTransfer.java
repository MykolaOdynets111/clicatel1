import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

public class CreateChatAndTransfer {
    private WebDriver widgetDriver;
    private WebDriver agentDriver;
    private WebDriver secondAgentDriver;
    private BrowserImplementation browserImplementation;
    private WidgetSteps widgetSteps;
    private String userName;
    private String mail;
    private AgentSteps agentSteps;
    private AgentSteps secondAgentSteps;


    @BeforeClass
    public void startBrowsers(){
        userName = "Performance" + Long.toString (new Random().nextLong () & Long.MAX_VALUE, 36).substring(0,4);
        mail = "perf_ui@" + Long.toString (new Random().nextLong () & Long.MAX_VALUE, 36).substring(0,4) + ".com";
        browserImplementation = new BrowserImplementation();
        widgetDriver = browserImplementation.browserImplementation();
        widgetSteps = new WidgetSteps(widgetDriver);
        agentDriver = browserImplementation.browserImplementation();
        agentSteps = new AgentSteps(agentDriver);
        agentSteps.openManePage().loginWithAgent(Credentials.FIRST_AGENT);
        secondAgentDriver = browserImplementation.browserImplementation();
        secondAgentSteps = new AgentSteps(secondAgentDriver);
        secondAgentSteps.openManePage();
    }

    @Test()
    public void loadWidgetPageTime() {
        widgetSteps.loadWidgetPage(URL.widgetUrl);
    }

    @Test(dependsOnMethods = {"loadWidgetPageTime"})
    public void widgetConnectionTime(){
        widgetSteps.openWidget().waitForSalutation();
    }

    @Test(dependsOnMethods = {"widgetConnectionTime"})
    public void botResponseTime(){
        widgetSteps.setText("Hi").clickSendButton().waitForWidgetResponse("Hello");
    }

    @Test(dependsOnMethods = {"botResponseTime"})
    public void agentDeskConnectionTime(){
      agentSteps.openAgentDesk().waitForConnectionToAgentDesk();
    }

    @Test(dependsOnMethods = {"agentDeskConnectionTime"})
    public void userReceiveNameAndMaleFormTime(){
        widgetSteps.setText("to agent").clickSendButton().waitForUserInfoFormIsAppear();
    }

    @Test(dependsOnMethods = {"userReceiveNameAndMaleFormTime"})
    public void agentReceiveChatTime(){
        widgetSteps.fillUserFormAndSubmit(userName, mail);
        agentSteps.waitForChatArrived(userName);
    }

    @Test(dependsOnMethods = {"agentReceiveChatTime"})
    public void agentOpenChatTime(){
        agentSteps.openChat(userName).waitForBotMessageLoad();
    }

    @Test(dependsOnMethods = {"agentOpenChatTime"})
    public void sentMessageByAgentTime(){
        agentSteps.sendMessage("how can I help you");
        widgetSteps.waitForWidgetResponse("how can I help you");
    }

    @Test(dependsOnMethods = {"sentMessageByAgentTime"})
    public void sentMessageByUserTime(){
        widgetSteps.setText("help with performance").clickSendButton();
        agentSteps.waitForMessageFromUser("help with performance");
    }

    @Test(dependsOnMethods = {"sentMessageByUserTime"})
    public void loginAgentTime(){
        secondAgentSteps.loginWithAgent(Credentials.SECOND_AGENT).waitForConnectionToAgentDesk();
    }

    @Test(dependsOnMethods = {"loginAgentTime"})
    public void openTransferWindowTime(){
        agentSteps.openTransferWindow();
    }

    @Test(dependsOnMethods = {"openTransferWindowTime"})
    public void selectAgentInTransferWindowTime(){
        agentSteps.selectAgentAndNote(Credentials.SECOND_AGENT.getName());
    }

    @Test(dependsOnMethods = {"selectAgentInTransferWindowTime"})
    public void selectTransferChatTime(){
        agentSteps.clickTransferButton();
        secondAgentSteps.waitForIncomingTransferAppear();
    }

    @Test(dependsOnMethods = {"selectTransferChatTime"})
    public void transferredChatAppearingTime(){
        secondAgentSteps.acceptIncomingTransfer().waitForChatArrived(userName);
    }

    @Test(dependsOnMethods = {"transferredChatAppearingTime"})
    public void secondAgentOpenChatTime(){
        secondAgentSteps.openChat(userName).waitForMessageFromUser("help with performance");
    }

    @Test(dependsOnMethods = {"secondAgentOpenChatTime"})
    public void secondAgentCloseChatTime(){
        secondAgentSteps.closeChat(userName);
    }


    @AfterClass (alwaysRun = true)
    public void closeBrowsers(){
        closeDriver(widgetDriver);
        closeDriver(agentDriver);
        closeDriver(secondAgentDriver);
    }

    private void closeDriver(WebDriver driver){
        if (!(driver == null)){
            driver.manage().deleteAllCookies();
            driver.quit();
        }
    }
}
