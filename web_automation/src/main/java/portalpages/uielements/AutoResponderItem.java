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

    @FindBy(css = "textarea.go-textarea")
    private WebElement textarea ;

    @FindBy(css = "button.button.button-secondary")
    private WebElement resetToDefaultButton;

    @FindBy(css = "bttn-toggle ng-valid ng-not-empty ng-dirty']")
    private WebElement buttonOnOff;

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

    public AutoResponderItem typeMessage(String msg) {
        if (!msg.equals(null) || !msg.equals("")) {
            textarea.clear();
            inputTextAgent(textarea, 5, "main agent", "Note number", msg);
        }
        return this;
    }

    public boolean isMessageShown() {
        return textarea.isDisplayed();
    }

    public String getMessage() {
        waitForElementToBeVisibleAgent(textarea, 10);
        String messageOnfrontend = textarea.getAttribute("value");
        return messageOnfrontend;
    }

    public void clickOnOff(){
        waitForElementToBeVisibleAgent(buttonOnOff, 10);
        buttonOnOff.click();
    }

}