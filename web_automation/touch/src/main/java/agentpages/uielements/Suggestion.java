package agentpages.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Suggestion extends AbstractWidget {

    protected Suggestion(WebElement element) {
        super(element);
    }

    @FindBy(css = "[selenium-id=suggested-message-text]")
    private WebElement suggestedMessage;

    public Suggestion setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getSuggestionMessage() {
        return suggestedMessage.getAttribute("innerText");
    }
}
