package portaluielem.supervisor;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.PortalUserRow;

import java.util.List;

@FindBy(css = ".supervisor-view-header")
public class SupervisorDeskHeader extends AbstractUIElement {

    @FindBy(css = "[type='submit']")
    private WebElement applyFiltersButton;

    @FindBy(css = ".cl-r-checkbox__label")
    private WebElement flaggedOnlyCheckbox;

    @FindBy(id = "channel")
    private WebElement channelsDropdown;

    @FindBy(xpath=".//div[contains(@id, 'react-select')]")
    private List<WebElement> dropdownOptions;

    @FindBy(css = "#channel .cl-r-select__placeholder")
    private WebElement channelFilterValue;

    @FindBy(css = "#sentiments .cl-r-select__placeholder")
    private WebElement sentimentsFilterValue;

    public void clickApplyFilterButton(){
        clickElem(this.getCurrentDriver(), applyFiltersButton, 1, "Apply Filters");
    }

    public SupervisorDeskHeader clickFlaggedOnlyCheckbox(){
        clickElem(this.getCurrentDriver(), flaggedOnlyCheckbox, 1, "Flagged Only");
        return this;
    }

    public SupervisorDeskHeader selectChanel(String chanelName){
        clickElem(this.getCurrentDriver(), channelsDropdown, 1, "Channels Dropdown");
        dropdownOptions.stream().filter(e -> e.getText().equalsIgnoreCase(chanelName))
                .findFirst().orElseThrow(() ->
                        new AssertionError(chanelName + " user is not shown on Manage Agent User page")).click();
    return this;
    }

    public String getChannelFilterValue(){
        return getTextFromElem(this.getCurrentDriver(), channelFilterValue, 1, "Channels Filter Value");
    }

    public String getSentimentsFilterValue(){
        return getTextFromElem(this.getCurrentDriver(), sentimentsFilterValue, 1, "Sentiments Filter Value");
    }

}
