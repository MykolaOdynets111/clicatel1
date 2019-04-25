package portalpages.uielements;

import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AutoResponderItem extends Widget implements WebActions, ActionsHelper, JSHelper {

    @FindBy(css = "span.automated-message-title")
    private WebElement messageTitle;

    @FindBy(css = "span.cl-icon--collapse")
    private WebElement collapceIcon;

    @FindBy(css = "button.button.button-secondary")
    private WebElement resetToDefaultButton;

    protected AutoResponderItem(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public String getTitle(){
        return messageTitle.getAttribute("innerText");
    }

    public void clickCollapseIcon(){
        clickElemAgent(collapceIcon, 1, "admin", "Collapse icon");
    }

    public void clickResetToDefaultButton(){
        clickElemAgent(resetToDefaultButton, 1, "admin", "Reset to default button");
    }
}