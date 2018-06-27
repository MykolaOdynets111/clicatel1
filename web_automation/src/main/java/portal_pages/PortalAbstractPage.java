package portal_pages;

import driverManager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public class PortalAbstractPage implements WebActions, ActionsHelper, JSHelper {

    public PortalAbstractPage() {
        HtmlElementLoader.populatePageObject(this, DriverFactory.getSecondDriverInstance());
    }

}
