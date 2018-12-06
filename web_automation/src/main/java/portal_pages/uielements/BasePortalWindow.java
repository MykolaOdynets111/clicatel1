package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div.cl-wizzard.create-integration-container")
public class BasePortalWindow extends AbstractUIElement{

    @FindBy(css = "button.button.button-primary.ng-scope")
    protected WebElement nextButton;

    @FindBy(css = "li.ui-select-choices-group div.ui-select-choices-row")
    protected List<WebElement> selectOptionsInDropdown;
}
