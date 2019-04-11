package abstractclasses;

import drivermanager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;


public abstract class AgentAbstractPage implements WebActions, ActionsHelper, JSHelper {

    private String agent="main agent";

    public AgentAbstractPage(String agent) {
        this.agent = agent;
        HtmlElementLoader.populatePageObject(this, DriverFactory.getDriverForAgent(agent));
    }

    public String getCurrentAgent(){
        return agent;
    }
}