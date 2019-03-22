package touchpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


@FindBy(css = "ul.ctl-touch-actions-list")
public class TouchActionsMenu extends AbstractUIElement {

    @FindBy(css = "li.ctl-chat-action-item")
    private List<WebElement> touchActions;

    public boolean isTouchActionShown(String touchAction) {
        return touchActions.stream().anyMatch(e->e.getText().equalsIgnoreCase(touchAction));
    }

    public void selectTouchAction(String touchAction) {
        touchActions.stream().filter(e->e.getText().equalsIgnoreCase(touchAction)).findFirst().get().click();
    }
}
