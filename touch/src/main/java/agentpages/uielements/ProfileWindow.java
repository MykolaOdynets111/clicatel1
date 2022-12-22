package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".cl-modal")
public class ProfileWindow extends AbstractUIElement {

    @FindAll({
            @FindBy(css = "[data-testid=close-modal-btn]"),
            @FindBy(css = ".cl-r-icon.cl-r-icon-close")
    })
    private WebElement closeProfileWindowButton;

    @FindBy(css = "[data-testid = profile-first-name-input]")
    private WebElement firstName;

    @FindBy(css = "[data-testid = profile-last-name-input]")
    private WebElement lastName;

    @FindBy(css = "[data-testid = profile-email-input]")
    private WebElement mail;

    @FindBy(css = "[data-testid=profile-reset-password]")
    private WebElement resetPasswordButton;

    @FindBy(xpath = "//div[contains(@class,'reset-pass-success-msg')]")
    private WebElement resetPasswordSuccessMessage;

    public String getFirstName() {
        return firstName.getAttribute("value");
    }

    public String getLastName() {
        return lastName.getAttribute("value");
    }

    public String getMail() {
        return mail.getAttribute("value");
    }

    public boolean isFirstNameEditable() {
        return isElementEnabled(this.getCurrentDriver(), firstName, 3);
    }

    public boolean isLastNameEditable() {
        return isElementEnabled(this.getCurrentDriver(), lastName, 3);
    }

    public boolean isMaleEditable() {
        return isElementEnabled(this.getCurrentDriver(), mail, 3);
    }

    public boolean isCloseProfileButtonShown() {
        return isElementShown(this.getCurrentDriver(), closeProfileWindowButton, 3);

    }

    public void closeIfOpened() {
        if (isCloseProfileButtonShown()) {
            closeProfileWindowButton.click();
        }
    }

    public void clickResetPasswordButton() {
        waitForElementToBeClickable(this.getCurrentDriver(), resetPasswordButton, 10);
        resetPasswordButton.click();
        resetPasswordButton.click(); // TPORT-105130 (Reset button does not work on single click even manually)
    }

    public String getResetPasswordSuccessMessage() {
        waitForElementToBeVisible(this.getCurrentDriver(), resetPasswordSuccessMessage, 10);
        return resetPasswordSuccessMessage.getText();
    }
}
