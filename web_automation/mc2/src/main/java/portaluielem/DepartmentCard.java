package portaluielem;

import abstractclasses.AbstractWidget;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DepartmentCard extends AbstractWidget {

    @FindBy(css = ".card-title__text")
    private WebElement name;

    @FindBy(css = ".grouping-card__description")
    private WebElement description;

    @FindBy(css = ".cl-actions-dropdown__icon")
    private WebElement threeDotsMenu;

    @FindBy(xpath = ".//span[text() = 'Delete department']")
    private WebElement deleteDepartmentButton;

    @FindBy(xpath = ".//*[@class ='cl-icon total']/parent::span")
    private WebElement agentsQuantity;

    public DepartmentCard(WebElement element) {
        super(element);
    }

    public DepartmentCard setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getName(){
        return getTextFromElem(this.getCurrentDriver(), name, 1, "Card Name" );
    }

    public String getDescription(){
        return getTextFromElem(this.getCurrentDriver(), description, 1, "Card Description" );
    }

    @Step(value = "Click Delete Department Button")
    public void clickDeleteDepartmentButton(){
        clickElem(this.getCurrentDriver(), threeDotsMenu, 10, "Three dots menu");
        clickElem(this.getCurrentDriver(), deleteDepartmentButton, 10, "Delete Department Button ");
    }

    public int getNumberOfAgents(){
       return Integer.parseInt(getTextFromElem(this.getCurrentDriver(), agentsQuantity, 0, "Quantity of Agents in card").trim());
    }


}
