package portaluielem;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.modal-content")
public class ModalWindow extends BasePortalWindow {

    @FindBy(css = "span[ng-bind='options.title']")
    private WebElement modalTitle;

    @FindBy(css = "div.discard-description.ng-binding")
    private WebElement modalMessage;

    @FindBy(css = "button.button.button-primary")
    private WebElement primaryButton;

    @Step(value = "Get modal window title")
    public String getModalTitle(){
        return getTextFromElem(this.getCurrentDriver(), modalTitle, 3, "Modal window header");
    }

    @Step(value = "Get modal window message")
    public String getModalMessage(){
        return getTextFromElem(this.getCurrentDriver(), modalMessage, 3, "Modal window message");
    }

    @Step(value = "Get modal window button text")
    public String getModalPrimaryButton(){
        return getTextFromElem(this.getCurrentDriver(), primaryButton, 3, "Modal window primary button");
    }
}
