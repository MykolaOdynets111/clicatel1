package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".cl-r-modal")
public class ProfileWindow extends AbstractUIElement {

    @FindBy(css = "[data-testid=setting-group-item]")
    private List<WebElement> listOfElementsWithRoles;

    @FindAll({
            @FindBy(css = "[data-testid=close-modal-btn]"),
            @FindBy(css = ".cl-r-icon.cl-r-icon-close")
    })
    private WebElement closeProfileWindowButton;

    @FindBy(id ="profile-first-name-input")
    private WebElement firstName;

    @FindBy(id ="profile-last-name-input")
    private WebElement lastName;

    @FindBy(id ="profile-email-input")
    private WebElement mail;

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
}
