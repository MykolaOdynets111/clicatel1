package touch_pages.uielements.messages;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ToUserMessageWithActions  extends Widget implements WebActions {

    @FindBy(xpath = "./following-sibling::li[@class='ctl-chat-message-container message-to with-content']")
    private WebElement toUserCard;

    @FindBy(xpath = "(./following-sibling::li[@class='ctl-chat-message-container message-to with-content']//span)[3]")
    private WebElement toUserTextMessageInCardButton;

    @FindBy(xpath = "(./following-sibling::li[@class='ctl-chat-message-container message-to with-content']//span)[1]")
    private WebElement toUserMessageInCard;

    @FindBy(xpath = "./following-sibling::li[@class='ctl-chat-message-container message-to with-content']//button/span")
    private List<WebElement> buttons;

    public ToUserMessageWithActions(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public boolean isTextInCardShown(int wait) {
        try{
            waitForElementToBeVisible(toUserTextMessageInCardButton, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isCardNotShown(int wait) {
        try{
            waitForElementToBeVisible(toUserCard, wait);
            return false;
        } catch (TimeoutException e) {
            return true;
        }
    }

    public boolean isButtonShown(String buttonText) {
        return buttons.stream().anyMatch(e -> e.getText().equalsIgnoreCase(buttonText));
    }

    public void clickButton(String buttonName) {
        buttons.stream().filter(e->e.getText().equalsIgnoreCase(buttonName)).findFirst().get().click();
    }

    public String getTextFromCard(){
        return getTextFrom(toUserMessageInCard);
    }
}
