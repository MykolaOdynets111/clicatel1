package agentpages.uielements;

import interfaces.WebActionsDeprecated;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Suggestion extends Widget implements WebActionsDeprecated {

    protected Suggestion(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);

    }

    @FindBy(xpath = ".//p[not(@class='confidence-level')]")
    private WebElement suggestedMessage;

    public String getSuggestionMessage() {
        return suggestedMessage.getAttribute("innerText");
    }
}
