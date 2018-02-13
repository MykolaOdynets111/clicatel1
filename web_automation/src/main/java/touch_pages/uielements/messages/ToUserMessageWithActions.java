package touch_pages.uielements.messages;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ToUserMessageWithActions  extends Widget implements WebActions {

    public ToUserMessageWithActions(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }
}
