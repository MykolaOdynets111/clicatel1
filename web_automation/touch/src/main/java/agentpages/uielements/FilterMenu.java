package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".cl-r-roster-filter.cl-r-roster-filter--form-opened")
public class FilterMenu extends AbstractUIElement {

    @FindBy (xpath = "//span[text()='Flagged']")
    private WebElement flaggedCheckbox;

    @FindBy(css ="[selenium-id=apply-filter-btn]")
    private WebElement applyFiltersButton;

    @FindBy (xpath = "//div[text()='Select any channel']")
    private WebElement chanelInput;

    public FilterMenu selectFlaggedCheckbox(){
        clickElem(this.getCurrentDriver(), flaggedCheckbox, 1, "Flagged checkbox");
        return this;
    }

    public void clickApplyButton(){
        clickElem(this.getCurrentDriver(), applyFiltersButton, 1, "Apply Filters Button");
    }

    public void fillChanelInputField(String chanel){
        inputText(this.getCurrentDriver(), chanelInput, 1, "Chanel Input", chanel);
    }

}
