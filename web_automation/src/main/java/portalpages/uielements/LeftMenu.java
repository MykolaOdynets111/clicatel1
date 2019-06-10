package portalpages.uielements;

import abstractclasses.AbstractUIElement;
import drivermanager.DriverFactory;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;

@FindBy(css = "div.menu-container")
public class LeftMenu extends AbstractUIElement {

    private String currentAgent;

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

    public void setCurrentAgent(String agent){
        this.currentAgent = agent;
    }

    public void clickLeftMenuItem(String itemName){
        activeLeftMenuItems.stream().filter(e -> e.getText().equalsIgnoreCase(itemName)).findFirst().get().click();
    }

    public void navigateINLeftMenuWithSubmenu(String menuItem, String subMenuItem){
        waitForElementsToBeVisibleAgent(launchpadMenuItems, 5, "admin");
        WebElement elem = activeLeftMenuItems
                .stream().filter(e -> e.getText().equalsIgnoreCase(menuItem)).findFirst().get();
        clickElemAgent(elem, 3, "admin", menuItem + " left menu item");
        try {
            waitForElementToBeVisibleAgent(leftSubMenu, 10);
            waitForElementsToBeVisibleAgent(submenuItems, 3, "admin");
        }catch (TimeoutException e){
            executeJSclick(activeLeftMenuItems
                            .stream().filter(e1 -> e1.getText().equalsIgnoreCase(menuItem)).findFirst().get(),
                    DriverFactory.getAgentDriverInstance());
        }

        executeClickInElemListWithWait(submenuItems, subMenuItem, this.currentAgent);
    }

    public void navigateINLeftMenu(String menuItem){
        for(int i=0; i<10; i++){
            try{
                activeLeftMenuItems
                        .stream().filter(e -> e.getText().equalsIgnoreCase(menuItem)).findFirst().get().click();
                break;
            } catch(NoSuchElementException|StaleElementReferenceException e){
                waitFor(200);
            }
        }
    }

    public void clickBackButton(){
        clickElemAgent(backLaunchpadButton, 3, "admin", " Back button in left menu");
    }
}
