package abstract_classes;

import driverManager.DriverFactory;
import interfaces.WebActions;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;


public abstract class AgentAbstractPage implements WebActions {

    public AgentAbstractPage() {
        HtmlElementLoader.populatePageObject(this, DriverFactory.getSecondDriverInstance());
    }





}
