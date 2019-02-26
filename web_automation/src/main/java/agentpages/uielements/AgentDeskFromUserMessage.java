package agentpages.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AgentDeskFromUserMessage extends Widget implements WebActions {

    @FindBy(css = "span.text-parsed-by-emoji")
    private WebElement messageText;

    public AgentDeskFromUserMessage(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public String getMessageText() {
        return messageText.getText().trim();
    }
}
