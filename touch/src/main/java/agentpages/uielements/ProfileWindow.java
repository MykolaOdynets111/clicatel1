package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".cl-modal")
public class ProfileWindow extends AbstractUIElement {

    @FindBy(css = "[data-testid=setting-group-item]")
    private List<WebElement> listOfElementsWithRoles;

    @FindAll({
            @FindBy(css = "[data-testid=close-modal-btn]"),
            @FindBy(css = ".cl-r-icon.cl-r-icon-close")
    })
    private WebElement closeProfileWindowButton;

    @FindBy(css ="[data-testid = profile-first-name-input]")
    private WebElement firstName;

    @FindBy(css ="[data-testid = profile-last-name-input]")
    private WebElement lastName;

    @FindBy(css ="[data-testid = profile-email-input]")
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

    public List<String> getListOfRoles(){
        if(listOfElementsWithRoles.size()==0){
            Assert.fail("There are no roles listed in profile window.");
        }
        return listOfElementsWithRoles.stream().map(e -> e.getText().toUpperCase()).collect(Collectors.toList());
    }

    public boolean isCloseProfileButtonShown(){
        return isElementShown(this.getCurrentDriver(), closeProfileWindowButton, 3);
    }

    public void closeProfileWindow(){
        closeProfileWindowButton.click();
    }

    public void closeIfOpened(){
        if (isCloseProfileButtonShown()) closeProfileWindow();
    }

    public void clickResetPasswordButton(){
        waitForElementToBeClickable(this.getCurrentDriver(), resetPasswordButton, 10);
        resetPasswordButton.click();
        resetPasswordButton.click(); // TPORT-105130 (Reset button does not work on single click even manually)
    }

    public String getResetPasswordSuccessMessage(){
        waitForElementToBeVisible(this.getCurrentDriver(), resetPasswordSuccessMessage, 10);
        return resetPasswordSuccessMessage.getText();
    }

}
