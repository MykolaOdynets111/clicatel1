package touchpages.uielements.messages;

import abstractclasses.AbstractSocialPage;
import driverfactory.DriverFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class WelcomeMessages extends AbstractSocialPage {

    @FindBy(css = "li.ctl-chat-message-container.message-to span.text-break-mod")
    private List<WebElement>  welcomeTextMessage;

    @FindBy(css = "li.message-to.with-content")
    private WebElement welcomeCardContainer;

    @FindBy(xpath = "(//div[@data-name='card-container']//span)[1]")
    private WebElement welcomeCardText;

    @FindBy(css = "li.message-to.with-content button")
    private List<WebElement> welcomeCardButtons;

    @FindBy(css = "div.to .msg")
    private WebElement toUserMessages;
    String cssWelcomeTextMessage = "li.ctl-chat-message-container.message-to span.text-break-mod";

    public WelcomeMessages(WebDriver driver) {
        super(driver);
    }

    public WelcomeMessages(String agent) {
        super(DriverFactory.getDriverForAgent(agent));
    }

    public boolean isWelcomeCardContainerShown() {
        try {
            return isElementShown(this.getCurrentDriver(), welcomeCardContainer, 5);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<String> getWelcomeCardButtonText(){
        return welcomeCardButtons.stream().map(e -> e.getText()).collect(Collectors.toList());
    }

    public void clickActionButton(String buttonName){
        WebElement button = welcomeCardButtons.stream().filter(e->e.getText().equalsIgnoreCase(buttonName)).findFirst().get();
        button.click();
    }

    public boolean isWelcomeTextMessageShown() {
        if (isElementShown(this.getCurrentDriver(), toUserMessages, 5)){
            return isElementShown(this.getCurrentDriver(), toUserMessages, 1);
        } else {
            return false;
        }
    }

    public String getWelcomeMessageText() {
        waitForElementToBeVisible(this.getCurrentDriver(), toUserMessages, 3);
        return getTextFromElem(this.getCurrentDriver(), toUserMessages, 1, "Welcome message");
    }

    public String getWelcomeCardText() {
        return getTextFromElem(this.getCurrentDriver(), welcomeCardText, 5, "Welcome cart text");
    }
}
