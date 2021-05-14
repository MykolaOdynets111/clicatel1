import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.util.Random;

public class StartManyAgents {

    private ThreadLocal<WebDriver> agentDriver = new ThreadLocal<>();
    private BrowserImplementation browserImplementation;
    private ThreadLocal<AgentSteps> agentSteps = new ThreadLocal<>();


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

    @DataProvider (name = "data-provider", parallel = true)
    public Object[][] dpMethod(){
        return getAgents();
    }


    @Test(dataProvider = "data-provider")
    public void agentOpenChatTime(Agents agent){
        agentDriver.set(browserImplementation.browserImplementation());
        agentSteps.set(new AgentSteps(agentDriver.get()));
        agentSteps.get().openManePage().loginWithAgents(agent);
        try {
            Thread.sleep(1200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        agentSteps.get().takeScreenshot(agent);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowsers(){
        agentDriver.get().quit();
    }
}
