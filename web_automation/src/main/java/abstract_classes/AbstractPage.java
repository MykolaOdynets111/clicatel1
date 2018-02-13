package abstract_classes;

import driverManager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;


public abstract class AbstractPage implements WebActions, JSHelper, ActionsHelper {

    public AbstractPage() {
        HtmlElementLoader.populatePageObject(this, DriverFactory.getInstance());
    }



}
