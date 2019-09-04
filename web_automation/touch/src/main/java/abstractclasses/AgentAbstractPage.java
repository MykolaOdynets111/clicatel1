package abstractclasses;

import driverfactory.DriverFactory;
import driverfactory.MC2DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import interfaces.WebActionsDeprecated;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;


public abstract class AgentAbstractPage implements WebActions, ActionsHelper, JSHelper {

    private String agent="main agent";
    private String loadingSpinner = "//*[text()='Connecting...']";

    protected WebDriver currentDriver;

    public AgentAbstractPage(String agent) {
        this.agent = agent;
        currentDriver = DriverFactory.getDriverForAgent(agent);
        HtmlElementLoader.populatePageObject(this, currentDriver);
    }

    public boolean waitForLoadingInLeftMenuToDisappear(int waitForSpinnerToAppear, int waitForSpinnerToDisappear){
        try{
            try {
                waitForElementToBeVisibleByXpath(this.getCurrentDriver(), loadingSpinner, waitForSpinnerToAppear);
            }catch (TimeoutException e){ }
                waitForElementToBeInVisibleByXpath(this.getCurrentDriver(), loadingSpinner, waitForSpinnerToDisappear);
            return true;
        }
        catch (TimeoutException e){
            return false;
        }
    }

    public String getCurrentAgent(){
        return agent;
    }

    public WebDriver getCurrentDriver(){
        return this.currentDriver;
    }
}
