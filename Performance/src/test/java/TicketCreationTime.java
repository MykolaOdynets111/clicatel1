import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

public class TicketCreationTime {
    private WebDriver widgetDriver;
    private WebDriver agentDriver;
    private BrowserImplementation browserImplementation;
    private WidgetSteps widgetSteps;
    private String userName;
    private String mail;
    private AgentSteps agentSteps;



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
    public void agentSupervisorDescOpenTime(){
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

    @Test(dependsOnMethods = {"loginAgentTime"})
    public void openTransferWindowTime(){
        agentSteps.openTransferWindow();
    }

    @Test(dependsOnMethods = {"openTransferWindowTime"})
    public void selectAgentInTransferWindowTime(){
        agentSteps.selectAgentAndNote(Credentials.SECOND_AGENT.getName());
    }


    @AfterClass (alwaysRun = true)
    public void closeBrowsers(){
        closeDriver(widgetDriver);
        closeDriver(agentDriver);
    }

    private void closeDriver(WebDriver driver){
        if (!(driver == null)){
            driver.manage().deleteAllCookies();
            driver.quit();
        }
    }
}
