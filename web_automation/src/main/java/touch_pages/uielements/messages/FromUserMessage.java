package touch_pages.uielements.messages;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FromUserMessage extends Widget implements WebActions {

    @FindBy(css = "span.text-break-mod")
    private WebElement messageText;

    public FromUserMessage(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public String getMessageText() {
        return messageText.getAttribute("innerText");
//        return messageText.getText();
    }
}
