package abstractclasses;

import drivermanager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActionsDeprecated;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;


public abstract class AbstractPage implements WebActionsDeprecated, JSHelper, ActionsHelper {

    public AbstractPage() {
        HtmlElementLoader.populatePageObject(this, DriverFactory.getTouchDriverInstance());
    }

    public AbstractPage(WebDriver driver) {
        HtmlElementLoader.populatePageObject(this, driver);
    }


}
