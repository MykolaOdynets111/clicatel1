package abstractclasses;

import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.htmlelements.element.HtmlElement;


public class AbstractUIElement extends HtmlElement implements WebActions, JSHelper, ActionsHelper {

    public WebDriver currentDriver;

    public void setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
    }

    public WebDriver getCurrentDriver(){
        return this.currentDriver;
    }
}
