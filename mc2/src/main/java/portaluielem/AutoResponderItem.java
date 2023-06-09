package portaluielem;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import lombok.NonNull;

public class AutoResponderItem extends AbstractWidget {

    @FindBy(css = ".auto-responder-title__text")
    private WebElement messageTitle;

    @FindBy(xpath = ".//button[contains(@class, 'expand')]")
    private WebElement collapceIcon;

    @FindBy(name = "text")
    private WebElement textArea;

    @FindBy(xpath = ".//button[text()='Reset to Default']")
    private WebElement resetToDefaultButton;

    @FindBy(css = ".cl-r-toggle-btn__label")
    private WebElement buttonOnOff;

    @FindBy(xpath = ".//*[contains(text(), 'Save')]")
    private WebElement saveButton;

    @FindBy(css = "div[class='cl-toggle__label']")
    private WebElement toggleButton;

    protected AutoResponderItem(WebElement element) {
        super(element);
    }

    public AutoResponderItem setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getTitle(){
        return messageTitle.getText();
    }

    public void clickCollapseIcon(){
        scrollAndClickElem(this.getCurrentDriver(), collapceIcon, 1,"Collapse icon");
    }

    public void clickResetToDefaultButton(){
        clickElem(this.getCurrentDriver(), resetToDefaultButton, 4,"Reset to default button");
    }
    public void  InputText(String message){
        clickElem(this.getCurrentDriver(), textArea, 1,"Remove text");
        inputText(this.getCurrentDriver(),textArea, 2, "For testing purpose",message);
    }
    public AutoResponderItem typeMessage(@NonNull String msg) {
        if (!msg.equals("")) {
            textArea.clear();
            inputText(this.getCurrentDriver(), textArea, 5, "Note number", msg);
        }
        return this;
    }

    public void clickSaveButton(){
        clickElem(this.getCurrentDriver(), saveButton, 2,"Save button");
    }

    public boolean isMessageShown() {
        return textArea.isDisplayed();
    }

    public String getMessage() {
        return getAttributeFromElem(this.getCurrentDriver(), textArea, 10, "Test", "value");
    }

    public void clickOnOff(){
        clickElem(this.getCurrentDriver(), buttonOnOff, 10, "On Off button");
    }
}