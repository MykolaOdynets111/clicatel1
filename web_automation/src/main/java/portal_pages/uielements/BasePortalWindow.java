package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
//import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class BasePortalWindow extends AbstractUIElement{

    @FindBy(xpath = "//button[@class='button button-primary ng-scope']")
    protected WebElement nextButton;

    @FindBy(xpath = "//button[@class='button button-primary ng-binding ng-scope']")
    protected WebElement addPaymentMethod;

    @FindBy(xpath = "//button[@class='button button-primary ng-binding ng-scope']")
    protected WebElement addPaymentMethodButton;

    @FindBy(xpath = "//button[@class='button button-primary ng-binding ng-scope']")
    protected  WebElement payNowButton;

    @FindBy(css = "li.ui-select-choices-group div.ui-select-choices-row")
    protected List<WebElement> selectOptionsInDropdown;

    @FindBy(xpath = "//span[@class='create-title ng-binding']")
    protected WebElement windowHeader;

    @FindBy(name = "form.cartPaymentDetails")
    protected WebElement cartPaymentDetailsForm;

//    private ThreadLocal<NgWebDriver> ngWebDriverThreadLocal = new ThreadLocal<>();
//
//    public NgWebDriver getNgDriver(WebDriver driver) {
//        if(ngWebDriverThreadLocal.get()==null){
//            ngWebDriverThreadLocal.set(new NgWebDriver((JavascriptExecutor) driver));
//        }
//        return ngWebDriverThreadLocal.get();
//    }
}
