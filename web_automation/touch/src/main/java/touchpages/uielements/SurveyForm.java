package touchpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".ctl-rate-card")
public class SurveyForm extends AbstractUIElement {

    @FindBy(css = ".ctl-rate-input-title")
    private WebElement rateTitle;

    @FindBy(css = ".ctl-rate-textarea-title")
    private WebElement commentTitle;

    @FindBy(css = ".ctl-rate-comment")
    private WebElement commentInput;

    @FindBy(css = ".rate-input label")
    private List<WebElement> ratingNumbers;

    @FindBy(css = "[class^='rate-input zero_to']")
    private WebElement npcRatingScale;

    @FindBy(css = "[class^='rate-input one_to']")
    private WebElement csatRatingScale;

    @FindBy(css = ".ctl-rate-button.ctl-button-primary")
    private WebElement noThanksButton;

    @FindBy(css = ".ctl-rate-button.ctl-button-secondary")
    private WebElement submitReviewButton;

    public boolean isNPCRatingScaleDisplayed(){
        return isElementShown(this.getCurrentDriver(), npcRatingScale, 2);
    }

    public boolean isNPSCorrectScaleSHown(){
        return ratingNumbers.size() == 11;
    }

    public boolean isCSATRatingScaleDisplayed(){
        return isElementShown(this.getCurrentDriver(), csatRatingScale, 2);
    }

    public boolean isSurveyDisplayed(){
        return isElementShown(this.getCurrentDriver(), SurveyForm.this, 5);
    }

    public void clickNoThanksButton() {
        clickElem(this.getCurrentDriver(), noThanksButton, 1, "No Thanks button is not found");
    }

    public void clickSubmitReviewButton() {
        clickElem(this.getCurrentDriver(), submitReviewButton, 1, "Submit Review button is not found");
    }

    public boolean isCommentFieldShown(){
        return isElementShown(this.getCurrentDriver(), commentInput, 1);
    }

    public SurveyForm setComment(String comment){
        inputText(this.getCurrentDriver(), commentInput, 1, "Comment Input field is not found", comment);
        return this;
    }

    public String getRateTitleText(){
       return getTextFromElem(this.getCurrentDriver(), rateTitle, 1, "Rate Title is not found");
    }

    public String getCommentTitleText(){
        return getTextFromElem(this.getCurrentDriver(), commentTitle, 1, "Rate Title is not found");
    }

    public SurveyForm selectRatingNumber(String number){
        ratingNumbers.stream()
                .filter(e -> e.getAttribute("for")
                        .replace("rateInput-", "")
                        .equalsIgnoreCase(number))
                .findFirst()
                .orElseThrow(() -> new AssertionError(number +" rate number was not found"))
                .click();
        return this;
    }

}
