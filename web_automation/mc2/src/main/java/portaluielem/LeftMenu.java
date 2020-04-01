package portaluielem;

import abstractclasses.AbstractUIElement;
import io.qameta.allure.Step;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;

@FindBy(css = "div.menu-container")
public class LeftMenu extends AbstractUIElement {

    @FindBy(xpath = "//div[@ng-repeat='menuItem in menu']//a[@ui-sref='launchpad']")
    private List<WebElement> launchpadMenuItems;

    @FindBy(xpath = "//div[@ng-repeat='menuItem in menu']/div")
    private List<WebElement> activeLeftMenuItems;

    @FindBy(xpath = "//a[@ui-sref='launchpad']//span[text()='BACK']")
    private WebElement backLaunchpadButton;

    @FindBy(xpath = "//div[@uib-collapse='menuItem.menuclIsCollapsed'][not(contains(@style,'0px'))]")
    private  WebElement leftSubMenu;

    @FindBy(xpath = "//div[@uib-collapse='menuItem.menuclIsCollapsed'][not(contains(@style,'0px'))]/ul/li//a[@ui-sref-active='active']/span")
    private List<WebElement> submenuItems;

    @Step(value = "Navigate in Left menu")
    public void navigateINLeftMenuWithSubmenu(String menuItem, String subMenuItem){
        waitForElementsToBeVisible(this.getCurrentDriver(), launchpadMenuItems, 5);
        WebElement elem;
        try {
            elem = activeLeftMenuItems
                    .stream().filter(e -> e.getText().equalsIgnoreCase(menuItem)).findFirst()
                    .orElseThrow(() -> new AssertionError("Cannot find '" + menuItem + "' left menu item"));
        }catch (StaleElementReferenceException e){
            waitFor(300);
            elem = activeLeftMenuItems
                    .stream().filter(e1 -> e1.getText().equalsIgnoreCase(menuItem)).findFirst()
                    .orElseThrow(() -> new AssertionError("Cannot find '" + menuItem + "' left menu item"));

        }
        clickElem(this.getCurrentDriver(), elem, 10, menuItem + " left menu item");
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), leftSubMenu, 10);
            waitForElementsToBeVisible(this.getCurrentDriver(), submenuItems, 3);
        }catch (TimeoutException e){
            executeJSclick(activeLeftMenuItems
                            .stream().filter(e1 -> e1.getText().equalsIgnoreCase(menuItem)).findFirst()
                            .orElseThrow(() -> new AssertionError("Cannot find '"+menuItem+"' left menu item")),
                    this.getCurrentDriver());
        }

        executeClickInElemListWithWait(this.getCurrentDriver(), submenuItems, subMenuItem);
    }

    public void navigateINLeftMenu(String menuItem){
        for(int i=0; i<10; i++){
            try{
                activeLeftMenuItems
                        .stream().filter(e -> e.getText().equalsIgnoreCase(menuItem)).findFirst()
                        .orElseThrow(() -> new AssertionError("Cannot find '"+menuItem+"' left menu item"))
                        .click();
                break;
            } catch(NoSuchElementException|StaleElementReferenceException e){
                waitFor(200);
            }
        }
    }

    public void clickBackButton(){
        clickElem(this.getCurrentDriver(), backLaunchpadButton, 3, " Back button in left menu");
    }

    public boolean isBackButtonShown(){
        return isElementShown(this.getCurrentDriver(), backLaunchpadButton, 3);
    }
}
