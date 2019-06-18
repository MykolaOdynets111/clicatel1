package portalpages.uielements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@FindBy(xpath = "//div[@ng-controller='TopUpBalanceCtrl']")
public class TopUpBalanceWindow extends BasePortalWindow {

    @FindBy(xpath = ".//input[@type='number']")
    private WebElement inputBalanceField;

    public boolean isShown(){
        return this.getWrappedElement().isDisplayed();
    }

    public String getMinLimit(){
        return  inputBalanceField.getAttribute("placeholder").split("Minimum allowed")[1];
    }
}
