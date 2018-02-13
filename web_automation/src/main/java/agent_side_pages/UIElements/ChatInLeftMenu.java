package agent_side_pages.UIElements;

import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChatInLeftMenu extends Widget implements WebActions, ActionsHelper, JSHelper {

    @FindBy(css = "div.profile-img")
    private WebElement userIcon;

    public ChatInLeftMenu(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public void openConversation() {
//        executeJSclick(userIcon);
//        moveToElemAndClick(userIcon);
        userIcon.click();
    }
}

