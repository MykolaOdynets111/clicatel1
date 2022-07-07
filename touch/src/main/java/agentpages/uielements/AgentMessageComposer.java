package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "cl-message-composer__tools-bar")
public class AgentMessageComposer extends AbstractUIElement
{
    @FindAll({
            @FindBy(css = "[data-testid='cl-icon cl-icon-puzzle cl-icon--fill-clickatell-dark-blue-500']"),
            @FindBy(css = "[selenium-id='message-composer-extensions']") //todo old locator
    })
    private WebElement chatToPay;

    public void clickOnChatToPayOption()
    {
        clickElem(this.getCurrentDriver(), chatToPay, 5,"Chat to Pay option");
    }


}