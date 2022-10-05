package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".ReactModal__Content.ReactModal__Content--after-open.cl-modal")
public class C2pSendForm extends AbstractUIElement {

    @FindBy(xpath=".//input[@name='orderNumber']")
    private WebElement orderNub;

    @FindBy(xpath = ".//input[@name='price']")
    private WebElement orderPrice;

    @FindBy(css=".cl-button.cl-button--primary")
    private WebElement sendButton;

    public C2pSendForm setOrderNumberField(String order) {
        inputText(this.getCurrentDriver(), orderNub, 5, "order", order);
        return this;
    }

    public C2pSendForm setPriceForOrder(String price) {
        inputText(this.getCurrentDriver(), orderPrice, 3, "Widget input", price);
        return this;
    }

    public void clickSendButton(){
        clickElem(this.getCurrentDriver(), sendButton, 10, "Send button");
    }

}