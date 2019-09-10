package touchpages.uielements.messages;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FromUserMessage extends AbstractWidget {

    @FindBy(css = "span.text-break-mod")
    private WebElement messageText;

    public FromUserMessage(WebElement element) {
        super(element);
    }

    public FromUserMessage setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getMessageText() {
        return messageText.getAttribute("innerText");
//        return messageText.getText();
    }
}
