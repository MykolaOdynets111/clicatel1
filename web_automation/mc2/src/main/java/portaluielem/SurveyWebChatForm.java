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

    @FindBy(css = ".save-btn")
    private WebElement saveSurveyButton;

    @FindBy(css = ".rate-input label")
    private List<WebElement> rateInputNumbers;

    @FindBy(css = ".number-block")
    private WebElement numberButton;

    @FindBy(css = ".icon.svg-icon-smileicon")
    private WebElement smileButton;

    @FindBy(css = ".icon.svg-icon-staricon")
    private WebElement starButton;

    @FindBy(css = "label .icon.svg-icon-staricon")
    private List<WebElement> starRateRepresentation;

    @FindBy(css = "label [class^='icon svg-icon-smile']")
    private List<WebElement> smileRateRepresentation;

    @FindBy(css = ".rate-input-label.number")
    private List<WebElement> numberRateRepresentation;

    @FindBy(xpath = "//span[text()='Allow customer to leave a note']/following-sibling::div/div[@class='react-switch-handle']")
    private WebElement commentSwitcher;

    @FindBy(css = ".ctl-rate-textarea-title")
    private WebElement commentFieldHeader;

    @FindBy(name = "surveyQuestionTitle")
    private WebElement questionInput;

    @FindBy(css = ".ctl-rate-input-title")
    private WebElement questionPreview;

    @FindBy(xpath="//div[text()='Smiles and stars available only in CSAT mode']")
    private WebElement ratingTypesUnavailableMessage;

    @FindBy(name ="ratingThanksMessage")
    private WebElement thankMessageForm;

    public SurveyWebChatForm clickCSATRadioButton(){
        clickElem(this.getCurrentDriver(), csatRadioButton, 2, "CSAT radio button is not found");
        return this;
    }

    public SurveyWebChatForm clickNPSRadioButton(){
        clickElem(this.getCurrentDriver(), npsRadioButton, 2, "NPS radio button is not found");
        return this;
    }

    public String getFromCount(){
        return getTextFromElem(this.getCurrentDriver(), countFrom, 2, "Count from number is not shown");
    }

    public List<String> getVariationOfRatingCSATScale(){
        List<String> ratingNums;
        clickElem(this.getCurrentDriver(), ratingNumbersDropdown, 2, "Rating number dropdown is not found");
        waitForElementsToBeVisible(this.getCurrentDriver(), ratingNumbersVariation, 2 );
        ratingNums = ratingNumbersVariation.stream().map(e->e.getText()).collect(Collectors.toList());
        clickElem(this.getCurrentDriver(), ratingNumbersDropdown, 2, "Rating number dropdown is not found");
        return ratingNums;
    }

    public void selectNumberFromDropdown(String number){
        clickElem(this.getCurrentDriver(), ratingNumbersDropdown, 2, "Rating number dropdown is not found");
        waitForElementsToBeVisible(this.getCurrentDriver(), ratingNumbersVariation, 2 );
        ratingNumbersVariation.stream().filter(e->e.getText().equals(number)).findFirst()
                .orElseThrow(() -> new AssertionError(number + " number was not found in dropdown.")).click();
    }

    public void clickSaveButton(){
        clickElem(this.getCurrentDriver(), saveSurveyButton, 2, "Save Survey Button is not found");
    }

    public void clickCommentSwitcher(){
        clickElem(this.getCurrentDriver(), commentSwitcher, 2, "Comment switcher is not found");
    }

    public String getSizeOfRateInputNumbers(){
        return rateInputNumbers.size()+"";
    }

    public void selectRateIcon(String icon){
        if(icon.equals("number")){
            clickElem(this.getCurrentDriver(), numberButton, 2, "Number Button is not found");
        } else if(icon.equals("star")){
            clickElem(this.getCurrentDriver(), starButton, 2, "Star Button is not found");
        } else if(icon.equals("smile")){
            clickElem(this.getCurrentDriver(), smileButton, 2, "Smile Button is not found");
        } else {
            new AssertionError("Incorrect Icon tipe was provided");
        }
    }

    public boolean isRateHasCorrectIcons(String icon){
        if(icon.equals("number")){
            return !numberRateRepresentation.isEmpty();
        } else if(icon.equals("star")){
            return !starRateRepresentation.isEmpty();
        } else if(icon.equals("smile")){
            return !smileRateRepresentation.isEmpty();
        } else {
            new AssertionError("Incorrect Icon tipe was provided");
        }
        return false;
    }

    public boolean isCommentFieldShown(){
        return isElementShown(this.getCurrentDriver(), commentFieldHeader, 2);
    }

    public void changeQuestion(String guestion){
            questionInput.clear();
            inputText(this.getCurrentDriver(), questionInput, 1, "Question Input", guestion);
    }

    public String getPreviewQuestion(){
        return getTextFromElem(this.getCurrentDriver(), questionPreview, 1, "Question Preview");
    }

    public boolean isSmileButtonDisabled(){
        moveToElement(this.getCurrentDriver(), smileButton);
        return isElementShown(this.getCurrentDriver(), ratingTypesUnavailableMessage, 1);
    }

    public boolean isStarButtonDisabled(){
        moveToElement(this.getCurrentDriver(), starButton);
        return isElementShown(this.getCurrentDriver(), ratingTypesUnavailableMessage, 1);
    }

    public void setThankMessage(String message){
        inputText(this.getCurrentDriver(), thankMessageForm, 1, "Question Input", message);
    }

}
