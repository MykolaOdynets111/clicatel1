package portaluielem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div[ng-controller='UserGroupsCtrl']")
public class EditUserRolesWindow extends BasePortalWindow {

    @FindBy(xpath = "//div[@class='cl-page-section ng-scope']//label[@class='cl-label checkbox-control control--checkbox']")
    private List<WebElement> touchSolutions;

    @FindBy(xpath = "//div[@class='cl-page-section ng-scope mod-reset-pt']//label[@class='cl-label checkbox-control control--checkbox']")
    private List<WebElement> platformSolutions;

    @FindBy(xpath = "//button[@ng-bind='finishBttn'][not(@id='integration-save')]")
    private WebElement finishButton;

    public EditUserRolesWindow selectNewTouchRole(String roleName){
        waitForElementToBeVisible(this.getCurrentDriver(), this.getWrappedElement(), 5);
        WebElement solution = touchSolutions.stream().filter(e -> e.getText().contains(roleName))
                .findFirst().get();
        clickElem(this.getCurrentDriver(), solution, 2,roleName);
        return this;
    }

    public EditUserRolesWindow selectNewPlatformRole(String roleName){
        waitForElementToBeVisible(this.getCurrentDriver(), this.getWrappedElement(), 5);
        WebElement solution = platformSolutions.stream().filter(e -> e.getText().contains(roleName))
                .findFirst().get();
        clickElem(this.getCurrentDriver(), solution, 2,roleName);
        return this;
    }

    public void clickFinishButton(){
        clickElem(this.getCurrentDriver(), finishButton, 3,"Finish");
    }


}
