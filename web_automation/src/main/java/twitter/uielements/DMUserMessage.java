package twitter.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DMUserMessage extends Widget implements WebActions {

    public DMUserMessage(WebElement element) {
        super(element);
    }

    private String messageTextCss = "div.DirectMessage-text p.tweet-text";

    public String getMessageText(){
        return findElemByCSS(messageTextCss).getAttribute("innerText").split("\n")[0];
    }
}
