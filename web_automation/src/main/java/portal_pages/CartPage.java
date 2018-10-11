package portal_pages;

import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage implements WebActions, ActionsHelper, JSHelper {

    @FindBy(css = "button.small-centered.button.button-primary")
    private WebElement checkOutButton;

    public void clickCheckoutButton(){
        waitForElementToBeVisibleAgent(checkOutButton, 10);
        checkOutButton.click();
    }
}
