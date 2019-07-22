package portalpages.uielements;

import abstractclasses.AbstractUIElement;
import abstractclasses.AbstractUIElementDeprecated;
//import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class BasePortalWindow extends AbstractUIElement {

    @FindBy(xpath = "//button[@class='button button-primary ng-scope']")
    protected WebElement nextButton;

    @FindBy(xpath = "//button[@class='button button-primary ng-binding ng-scope']")
    protected WebElement primaryBindingButton;

    @FindBy(css = "li.ui-select-choices-group div.ui-select-choices-row")
    protected List<WebElement> selectOptionsInDropdown;

    @FindBy(xpath = "//span[@class='create-title ng-binding']")
    protected WebElement windowHeader;

    @FindBy(name = "form.cartPaymentDetails")
    protected WebElement cartPaymentDetailsForm;

    @FindBy(xpath =  ".//button[@ng-click='wizardSubmit()'][not(@id='integration-save')]")
    protected WebElement addToCardButton;


    public void clickAddToCardButton(){
        executeJSclick(addToCardButton, getCurrentDriver());
    }

    public void clickPrimaryBindingButton(String agent, String buttonName){
        clickElem(getCurrentDriver(), primaryBindingButton, 5, buttonName);
    }
}
