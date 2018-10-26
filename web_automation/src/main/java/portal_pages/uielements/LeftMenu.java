package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;

@FindBy(css = "div.menu-container")
public class LeftMenu extends AbstractUIElement {


    @FindBy(xpath = "//div[@ng-repeat='menuItem in menu']/div[not(contains(@class, 'collapse'))]/a[not(contains(@class, 'hidden'))]/span")
    private List<WebElement> activeLeftMenuItems;

    @FindBy(xpath = "//div[@uib-collapse='menuItem.menuclIsCollapsed'][not(contains(@style,'0px'))]")
    private  WebElement leftSubMenu;

    @FindBy(xpath = "//div[@uib-collapse='menuItem.menuclIsCollapsed'][not(contains(@style,'0px'))]/ul/li//a[@ui-sref-active='active']/span")
    private List<WebElement> submenuItems;

    public void clickLeftMenuItem(String itemName){
        activeLeftMenuItems.stream().filter(e -> e.getText().equalsIgnoreCase(itemName)).findFirst().get().click();
    }


    public void navigateINLeftMenu(String menuItem, String subMenuItem){
//        waitForElementToBeVisible(findElement(By.xpath("//div[@ng-repeat='menuItem in menu']/div[not(contains(@class, 'collapse'))]/a[not(contains(@class, 'hidden'))]/span")), 5);
        for(int i=0; i<10; i++){
            try{
                activeLeftMenuItems
                        .stream().filter(e -> e.getText().equalsIgnoreCase(menuItem)).findFirst().get().click();
                break;
            } catch(NoSuchElementException|StaleElementReferenceException e){
                waitFor(200);
            }
        }

        waitForElementToBeVisibleAgent(leftSubMenu, 5);
        submenuItems.stream().filter(e -> e.getText().equalsIgnoreCase(subMenuItem)).findFirst().get().click();
    }
}
