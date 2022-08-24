package twitter.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DMUserMessage extends AbstractWidget {

    public DMUserMessage(WebElement element) {
        super(element);
    }

    @FindBy(xpath = ".//p[contains(@class, 'tweet-text')]")
    private WebElement messageTextCss;

    public DMUserMessage setCurrentDriver(WebDriver driver){
        this.currentDriver = driver;
        return this;
    }

    public String getMessageText(){
        return findElemByXPATH(this.getCurrentDriver(), ".//p[contains(@class, 'tweet-text')]").getAttribute("innerText").split("\n")[0];
//        return messageTextCss.getAttribute("innerText").split("\n")[0];
    }
}
