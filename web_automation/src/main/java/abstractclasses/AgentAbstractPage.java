package abstractclasses;

import drivermanager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.TimeoutException;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;


public abstract class AgentAbstractPage implements WebActions, ActionsHelper, JSHelper {

    private String agent="main agent";
    private String loadingSpinner = "//*[text()='Connecting...']";

    public AgentAbstractPage(String agent) {
        this.agent = agent;
        HtmlElementLoader.populatePageObject(this, DriverFactory.getDriverForAgent(agent));
    }

    public boolean waitForLoadingInLeftMenuToDisappear(String ordinalAgentNumber, int waitForSpinnerToAppear, int waitForSpinnerToDisappear){
        try{
            try {
                waitForElementToBeVisibleByXpathAgent(loadingSpinner, waitForSpinnerToAppear, ordinalAgentNumber);
            }catch (TimeoutException e){ }
            waitForElementsToBeInvisibleByXpathAgent(loadingSpinner, waitForSpinnerToDisappear, ordinalAgentNumber);
            return true;
        }
        catch (TimeoutException e){
            return false;
        }
    }

    public String getCurrentAgent(){
        return agent;
    }
}
