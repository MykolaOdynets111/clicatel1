package portaluielem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".tab-container")
public class SurveyWebChatForm extends BasePortalWindow{

    @FindBy(xpath ="//span[contains(text(), 'CSAT')]")
    private WebElement csatRadioButton;

    @FindBy(xpath ="//span[contains(text(), 'NPS')]")
    private WebElement npsRadioButton;

    @FindBy(css =".count-from")
    private WebElement countFrom;

    @FindBy(css =".cl-r-form-group.cl-r-form-group-primary")
    private WebElement ratingNumbersDropdown;

    @FindBy(css = "[id^='react-select-2-option']")
    private List<WebElement> ratingNumbersVariation;

    public SurveyWebChatForm clickCSATRadioButton(){
        clickElem(this.getCurrentDriver(), csatRadioButton, 2, "CSAT radio button is not found");
        return this;
    }

    public String getFromCount(){
        return getTextFromElem(this.getCurrentDriver(), countFrom, 2, "Count from number is not shown");
    }

    public List<String> getVariationOfRatingCSATScale(){
        clickElem(this.getCurrentDriver(), ratingNumbersDropdown, 2, "Rating number dropdown is not found");
        waitForElementsToBeVisible(this.getCurrentDriver(), ratingNumbersVariation, 2 );
        return ratingNumbersVariation.stream().map(e->e.getText()).collect(Collectors.toList());
    }




}
