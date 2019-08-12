package abstractclasses;

import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AbstractWidget extends Widget implements WebActions, JSHelper, ActionsHelper {

    protected WebDriver currentDriver;

    protected AbstractWidget(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public WebDriver getCurrentDriver(){
        return this.currentDriver;
    }

}
