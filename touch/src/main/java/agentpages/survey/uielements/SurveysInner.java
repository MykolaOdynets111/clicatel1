package agentpages.survey.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SurveysInner extends AbstractUIElement {


    @FindBy(css = ".number-block")
    private WebElement numberButton;

    @FindBy(css = "[data-selenium-id='sentiment-positive']")
    private WebElement smileButton;

    @FindBy(name = "star")
    private WebElement starButton;

    @FindBy(xpath = "//div[text()='Smiles and stars available only in CSAT mode']")
    private WebElement ratingTypesUnavailableMessage;

    @FindBy(xpath = "//span[text()='Prompt customer to leave a note:']/following-sibling::label")
    private WebElement commentSwitcher;

    @FindBy(name = "ratingThanksMessage")
    private WebElement thankMessageForm;

    public void clickCommentSwitcher() {
        clickElem(this.getCurrentDriver(), commentSwitcher, 2, "Comment switcher");
    }

    public void setThankMessage(String message) {
        inputText(this.getCurrentDriver(), thankMessageForm, 1, "Question Input", message);
    }

    public void selectRateIcon(String icon) {
        if (icon.equals("number")) {
            clickElem(this.getCurrentDriver(), numberButton, 2, "Number Button");
        } else if (icon.equals("star")) {
            clickElem(this.getCurrentDriver(), starButton, 2, "Star Button");
        } else if (icon.equals("smile")) {
            clickElem(this.getCurrentDriver(), smileButton, 2, "Smile Button");
        } else {
            throw new AssertionError("Incorrect Icon tipe was provided");
        }
    }

    public boolean isSmileButtonDisabled() {
        scrollToElem(this.getCurrentDriver(), smileButton, "Smile button");
        moveToElement(this.getCurrentDriver(), smileButton);
        return isElementShown(this.getCurrentDriver(), ratingTypesUnavailableMessage, 1);
    }

    public boolean isStarButtonDisabled() {
        scrollToElem(this.getCurrentDriver(), starButton, "Star button");
        moveToElement(this.getCurrentDriver(), starButton);
        return isElementShown(this.getCurrentDriver(), ratingTypesUnavailableMessage, 1);
    }
}
