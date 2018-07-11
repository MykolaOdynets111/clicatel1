package abstract_classes;

import driverManager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;


public abstract class AbstractPage implements WebActions, JSHelper, ActionsHelper {

    public AbstractPage() {
        HtmlElementLoader.populatePageObject(this, DriverFactory.getTouchDriverInstance());
    }



}
