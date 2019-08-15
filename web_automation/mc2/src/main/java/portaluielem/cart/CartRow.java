package portaluielem.cart;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartRow extends AbstractWidget {

    @FindBy(xpath = ".//td[contains(@ng-bind, 'cartItemType')]")
    private WebElement itemType;

    @FindBy(xpath = ".//td[contains(@ng-bind, 'currency')]")
    private WebElement totalAmount;

    protected CartRow(WebElement element) {
        super(element);
    }

    public CartRow setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getCartItemType(){
        return getTextFromElem(this.getCurrentDriver(), itemType, 3, "Item type");
    }

    public String getCartItemTotal(){
        return getTextFromElem(this.getCurrentDriver(), totalAmount, 3, "Item total amount");
    }

}