package twitter.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DMUserMessage extends Widget implements WebActions {

    public DMUserMessage(WebElement element) {
        super(element);
    }

    @FindBy(xpath = ".//p[contains(@class, 'tweet-text')]")
    private WebElement messageTextCss;

//    private String messageTextCss = "div.DirectMessage-text p.tweet-text";

    public String getMessageText(){
        return findElemByXPATH(".//p[contains(@class, 'tweet-text')]").getAttribute("innerText").split("\n")[0];
//        return messageTextCss.getAttribute("innerText").split("\n")[0];
    }
}
