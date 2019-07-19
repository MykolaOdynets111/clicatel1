package portalpages.uielements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(xpath = "//div[@ng-controller='TopUpBalanceCtrl']")
public class TopUpBalanceWindow extends BasePortalWindow {

    @FindBy(xpath = ".//input[@type='number']")
    private WebElement inputBalanceField;

    @FindBy(xpath = "//div[contains(@class, 'error')][not(contains(@class, 'ng-hide'))]")
    private WebElement errorInInputTopUpForm;

    public boolean isShown(){
        return this.getWrappedElement().isDisplayed();
    }

    public String getMinLimit(){
        return  inputBalanceField.getAttribute("placeholder").split("Minimum allowed")[1];
    }

    public void enterNewAmount(int amount){
        inputBalanceField.sendKeys(String.valueOf(amount));
    }

    public String getErrorWhileAddingPopup(){
        return getTextFromElemAgent(errorInInputTopUpForm, 3, "main",
                "Error about not valid top up sum");
    }
}
