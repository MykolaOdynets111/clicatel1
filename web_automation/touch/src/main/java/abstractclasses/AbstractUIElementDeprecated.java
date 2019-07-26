package abstractclasses;

import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import interfaces.WebActionsDeprecated;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

@Deprecated
public class AbstractUIElementDeprecated extends HtmlElement implements WebActionsDeprecated, WebActions, JSHelper, ActionsHelper {

    protected WebDriver currentDriver;

    public void setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
    }

    public WebDriver getCurrentDriver(){
        return this.currentDriver;
    }

}
