package agentpages.supervisor.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".ReactModal__Content")
public class MessageCustomerWindow extends AbstractUIElement {

    @FindBy(css = ".ReactModal__Content header div")
    private WebElement header;

    @FindBy(css =".cl-r-select__control .cl-r-select__indicators")
    private WebElement openViaDropDownButton;

    @FindBy(css = ".cl-r-select__option")
    private List<WebElement> availableChanells;

    @FindBy(css = "button.cl-r-button--primary")
    private WebElement sendButton;

    @FindBy(css = "[name='smsText']")
    private WebElement textInput;

    public String getHeader(){
        return getTextFromElem(this.getCurrentDriver(), header, 1, "Message Customer window header").trim();
    }

    public void setOpenDropDownButton(){
        clickElem(this.getCurrentDriver(), openViaDropDownButton, 1, "Open Via Drop");
    }

    public MessageCustomerWindow selectChanel(String channel){
        setOpenDropDownButton();
        waitForFirstElementToBeVisible(this.getCurrentDriver(), availableChanells, 2);
        availableChanells.stream().filter(e -> e.getText().equalsIgnoreCase(channel)).findFirst()
                .orElseThrow(() -> new AssertionError("Cannot find '" + channel + "' DropDown option")).click();
        return this;
    }

    public MessageCustomerWindow setMessageText(String text){
        //todo remove wait after spinner would be added
        waitFor(3000);
        inputText(this.getCurrentDriver(), textInput, 1, "Message Input", text);
        return this;
    }

    public MessageCustomerWindow clickSubmitButton(){
        clickElem(this.getCurrentDriver(), sendButton, 1, "Send");
        return this;
    }
}
