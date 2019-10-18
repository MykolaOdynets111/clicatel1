package portaluielem;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AutoResponderItem extends AbstractWidget {

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
    }

    public AutoResponderItem setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getTitle(){
        return messageTitle.getAttribute("innerText");
    }

    public void clickCollapseIcon(){
        clickElem(this.getCurrentDriver(), collapceIcon, 1,"Collapse icon");
    }

    public void clickResetToDefaultButton(){
        clickElem(this.getCurrentDriver(), resetToDefaultButton, 1,"Reset to default button");
    }

    public AutoResponderItem typeMessage(String msg) {
        if (msg != null || !msg.equals("")) {
            textarea.clear();
            inputText(this.getCurrentDriver(), textarea, 5, "Note number", msg);
        }
        return this;
    }

    public boolean isMessageShown() {
        return textarea.isDisplayed();
    }

    public String getMessage() {
        return getAttributeFromElem(this.getCurrentDriver(), textarea, 10, "Test", "value");
    }

    public void clickOnOff(){
        clickElem(this.getCurrentDriver(), buttonOnOff, 10, "On Off button");
    }

}