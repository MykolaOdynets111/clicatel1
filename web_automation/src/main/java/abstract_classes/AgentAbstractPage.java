package abstract_classes;

import driverManager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;


public abstract class AgentAbstractPage implements WebActions, ActionsHelper, JSHelper {

    public AgentAbstractPage() {
        HtmlElementLoader.populatePageObject(this, DriverFactory.getSecondDriverInstance());
    }



//    public AgentAbstractPage() {
//        WebDriver driver;
//        if (DriverFactory.isThirdDriverExists()){
//            driver=DriverFactory.getThirdDriverInstance();
//        } else {
//            driver = DriverFactory.getSecondDriverInstance();
//        }
//        HtmlElementLoader.populatePageObject(this, driver);
//    }

}
