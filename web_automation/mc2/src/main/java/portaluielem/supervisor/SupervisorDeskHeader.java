package portaluielem.supervisor;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".supervisor-view-header")
public class SupervisorDeskHeader extends AbstractUIElement {

    @FindBy(css = "[type='submit']")
    private WebElement applyFiltersButton;

    @FindBy(css = ".cl-r-checkbox__label")
    private WebElement flaggedOnlyCheckbox;

    public void clickApplyFilterButton(){
        clickElem(this.getCurrentDriver(), applyFiltersButton, 1, "Apply Filters");
    }

    public SupervisorDeskHeader clickFlaggedOnlyCheckbox(){
        clickElem(this.getCurrentDriver(), flaggedOnlyCheckbox, 1, "Flagged Only");
        return this;
    }
}
