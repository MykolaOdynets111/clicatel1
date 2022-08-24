package abstractclasses;

import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;


public abstract class AbstractSocialPage implements WebActions, JSHelper, ActionsHelper {

    private WebDriver currentDriver;

    public AbstractSocialPage(WebDriver driver) {
        this.currentDriver = driver;
        HtmlElementLoader.populatePageObject(this, driver);
    }

    public WebDriver getCurrentDriver(){
        return this.currentDriver;
    }
}
