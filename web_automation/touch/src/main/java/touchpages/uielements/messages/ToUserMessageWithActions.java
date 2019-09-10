package touchpages.uielements.messages;

import abstractclasses.AbstractWidget;
import drivermanager.ConfigManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class ToUserMessageWithActions extends AbstractWidget {

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

    private String widgetScroller = "div.scroller";

    public ToUserMessageWithActions(WebElement element) {
        super(element);
    }

    public ToUserMessageWithActions setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public boolean isTextInCardShown(int wait) {
        try{
            waitForElementToBeVisible(this.getCurrentDriver(), toUserTextMessageInCardButton, wait);
            return true;
        } catch (TimeoutException e) {
            if(ConfigManager.isRemote()) {
                scrollUp(this.getCurrentDriver(), widgetScroller, 182);
                return isElementShown(this.getCurrentDriver(), toUserTextMessageInCardButton, wait);
            }
            return false;
        }
    }

    public boolean isCardNotShown(int wait) {
        try{
            waitForElementToBeVisible(this.getCurrentDriver(), toUserCard, wait);
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
                scrollUp(this.getCurrentDriver(), widgetScroller, scrollPosition);
                targetButton.click();
                break;
            } catch (ElementNotVisibleException e){
                scrollPosition=scrollPosition-200;
            }
        }
    }

    public String getTextFromCard(){
        try {
            return toUserMessageInCard.getAttribute("innerText");
        }catch (NoSuchElementException e){
            Assert.assertTrue(false, "Card is not shown");
        }
        return "";
    }

    public String getTextFromAboveCardButton(){
        return getTextFromElem(this.getCurrentDriver(), toUserTextMessageInCardButton, 3, "To user text message");
    }

    public ToUserMessageWithActions fillInInputFieldWithAPlaceholder(String placeholder, String textToInput){
        inputText(this.getCurrentDriver(),
                inputs.stream().filter(e -> e.getAttribute("placeholder").equalsIgnoreCase(placeholder))
                        .findFirst().get()
                , 3, "Input field", textToInput);
        return this;
    }

    public int getNumberOfFieldRequiredErrors(){
        waitForElementsToBeVisible(this.getCurrentDriver(), fieldRequiredErrors, 5);
        return fieldRequiredErrors.size();
    }

    public boolean areRequiredFieldErrorsShown(){
        return areElementsShown(this.getCurrentDriver(), fieldRequiredErrors, 3);
    }
}
