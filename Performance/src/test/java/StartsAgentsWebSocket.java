import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class StartsAgentsWebSocket {

    private ThreadLocal<WebDriver> agentDriver = new ThreadLocal<>();
    private BrowserImplementation browserImplementation;
    private ThreadLocal<AgentLoginPage> agentLoginPage = new ThreadLocal<>();


    @BeforeMethod
    public void startBrowsers(){
        browserImplementation = new BrowserImplementation();
    }

    private Agents[][] getAgents(){
        Agents[][] agents= new Agents[20][1];
        int i =0;
        for (Agents agent: Agents.values()){
            agents[i][0] = agent;
            i++;
        }
        return agents;
    }

    @DataProvider(name = "data-provider", parallel = true)
    public Object[][] dpMethod(){
        return getAgents();
    }


    @Test(dataProvider = "data-provider")
    public void agentOpenChatTime(Agents agent){
        agentDriver.set(browserImplementation.browserImplementation());
        agentDriver.get().get(URL.webSocketAuth);
        agentLoginPage.set(new AgentLoginPage(agentDriver.get()));
        agentLoginPage.get().selectTenant("Performance")
                .selectAgent(agent.getName()).clickAuthenticateButton();
        agentDriver.get().get(URL.webSocketAgentDesk);
        System.out.println("Agent "+agent.getName()+" is logged in " + LocalDateTime.now());
        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        agentLoginPage.get().takeScreenshot(agent);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowsers(){
        agentDriver.get().quit();
    }
}
