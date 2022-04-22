package portaluielem;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(xpath = "//div[@ng-controller='TopUpBalanceCtrl']")
public class TopUpBalanceWindow extends BasePortalWindow {

    @FindBy(xpath = ".//input[@type='number']")
    private WebElement inputBalanceField;

    @FindBy(xpath = "//div[contains(@class, 'error')][not(contains(@class, 'ng-hide'))]")
    private WebElement errorInInputTopUpForm;

    @Step(value = "Verify 'Top up balance' window opened")
    public boolean isShown(){
        return this.getWrappedElement().isDisplayed();
    }

    public String getMinLimit(){
        return  inputBalanceField.getAttribute("placeholder").split("Minimum allowed")[1];
    }

    @Step(value = "Enter top up amount")
    public void enterNewAmount(int amount){
        inputBalanceField.sendKeys(String.valueOf(amount));
    }

    @Step(value = "Enter top up amount")
    public void enterNewAmount(double amount){
        inputBalanceField.clear();
        inputBalanceField.sendKeys(String.valueOf(amount));
    }

    @Step(value = "Get Error about not valid top up sum")
    public String getErrorWhileAddingPopup(){
        return getTextFromElem(this.getCurrentDriver(), errorInInputTopUpForm, 3,
                "Error about not valid top up sum");
    }

    @Step(value = "Verify input field highlighted")
    public boolean isInputHighlighted(){
        return inputBalanceField.getAttribute("class").contains("ng-invalid");
    }
}
