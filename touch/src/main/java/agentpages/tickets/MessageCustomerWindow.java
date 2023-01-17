package agentpages.tickets;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

@FindBy(css = ".ReactModal__Content")
public class MessageCustomerWindow extends AbstractUIElement {

    @FindBy(css = ".cl-modal-default-header-title")
    private WebElement header;

    @FindBy(css =".cl-select__control .cl-select__indicators")
    private WebElement openViaDropDownButton;

    @FindBy(css = ".cl-select__option")
    private List<WebElement> availableChannels;

    @FindBy(css = "[class='cl-button cl-button--primary']")
    private WebElement sendButton;

    @FindBy(css = "[name='note']")
    private WebElement textInput;

    public String getHeader(){
        return getTextFromElem(this.getCurrentDriver(), header, 1, "Message Customer window header").trim();
    }

    public void setOpenDropDownButton(){
        clickElem(this.getCurrentDriver(), openViaDropDownButton, 1, "Open Via Drop");
    }

    public MessageCustomerWindow selectChanel(String channel){
        setOpenDropDownButton();
        waitForFirstElementToBeVisible(this.getCurrentDriver(), availableChannels, 2);
        availableChannels.stream().filter(e -> e.getText().equalsIgnoreCase(channel)).findFirst()
                .orElseThrow(() -> new AssertionError("Cannot find '" + channel + "' DropDown option")).click();
        return this;
    }

    public boolean isChanelShown(String channel){
        setOpenDropDownButton();
        List<String> channelElementsText = new ArrayList<>();
        availableChannels.stream().forEach(e -> {
            channelElementsText.add(e.getText());
        });
        return channelElementsText.contains(channel);
    }

    public MessageCustomerWindow setMessageText(String text){
        inputText(this.getCurrentDriver(), textInput, 1, "Message Input", text);
        return this;
    }

    public MessageCustomerWindow clickSubmitButton(){
        clickElem(this.getCurrentDriver(), sendButton, 1, "Send");
        return this;
    }
}
