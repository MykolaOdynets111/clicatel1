package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css =".cl-extensions-manager-overlay")
public class Extensions extends AbstractUIElement {

    @FindBy(xpath =".//li[text()='All Extensions']")
    private WebElement allExtensionsTab;

    @FindBy(css=".cl-actions-menu__item-text")
    private WebElement paymentRequestItem;

    public void openC2pForm(){
        clickElem(this.getCurrentDriver(), allExtensionsTab, 2, "All Extensions Tab button");
        executeJSclick(this.getCurrentDriver(), paymentRequestItem);
    }

}
