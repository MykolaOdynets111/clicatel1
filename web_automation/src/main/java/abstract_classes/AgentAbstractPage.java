package abstract_classes;

import driverManager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.WebActions;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;


public abstract class AgentAbstractPage implements WebActions, ActionsHelper {

    public AgentAbstractPage() {
        HtmlElementLoader.populatePageObject(this, DriverFactory.getSecondDriverInstance());
    }





}
