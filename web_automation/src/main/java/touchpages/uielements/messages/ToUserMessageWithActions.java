package touchpages.uielements.messages;

import drivermanager.ConfigManager;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.*;
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

    @FindBy(xpath = "./following-sibling::li[@class='ctl-chat-message-container message-to with-content']//div/input")
    private List<WebElement> inputs;

    @FindBy(xpath = "./following-sibling::li[@class='ctl-chat-message-container message-to with-content']//div/span[contains(text(), 'is required')]")
    private List<WebElement> fieldRequiredErrors;

    public ToUserMessageWithActions(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public boolean isTextInCardShown(int wait) {
        try{
            waitForElementToBeVisible(toUserTextMessageInCardButton, wait);
            return true;
        } catch (TimeoutException e) {
            if(ConfigManager.isRemote()) {
                scrollUpWidget(182);
                return isElementShown(toUserTextMessageInCardButton);
            }
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
        return buttons.stream().anyMatch(e -> e.getAttribute("innerText").equalsIgnoreCase(buttonText));
    }

    public void clickButton(String buttonName) {
        WebElement targetButton =  buttons.stream().filter(e -> e.getAttribute("innerText").equalsIgnoreCase(buttonName)).findFirst().get();
        try {
            targetButton.click();
        } catch(ElementNotVisibleException e){
            scrollToButtonAndClick(targetButton);
        }
    }

    private void scrollToButtonAndClick(WebElement targetButton){
        int scrollPosition=2228;
        for (int i =0; i<10; i++){
            try{
                scrollUpWidget(scrollPosition);
                targetButton.click();
                break;
            } catch (ElementNotVisibleException e){
                scrollPosition=scrollPosition-200;
            }
        }
    }

    public String getTextFromCard(){
        return toUserMessageInCard.getAttribute("innerText");
    }

    public String getTextFromAboveCardButton(){
        return getTextFrom(toUserTextMessageInCardButton);
    }

    public ToUserMessageWithActions fillInInputFieldWithAPlaceholder(String placeholder, String textToInput){
        inputs.stream().filter(e -> e.getAttribute("placeholder").equalsIgnoreCase(placeholder))
                .findFirst().get()
                .sendKeys(textToInput);
        return this;
    }

    public int getNumberOfFieldRequiredErrors(){
        waitForElementsToBeVisible(fieldRequiredErrors, 5);
        return fieldRequiredErrors.size();
    }

    public boolean areRequiredFieldErrorsShown(){
        return areElementsShown(fieldRequiredErrors);
    }
}
