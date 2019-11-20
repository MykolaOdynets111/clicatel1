package portaluielem;

import abstractclasses.AbstractWidget;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DepartmentCard extends AbstractWidget {

    @FindBy(css = ".card-title__text")
    private WebElement name;

    @FindBy(css = ".grouping-card__description")
    private WebElement description;

    @FindBy(css = ".cl-actions-dropdown__icon")
    private WebElement threeDotsMenu;

    @FindBy(xpath = ".//span[text() = 'Delete department']")
    private WebElement deleteDepartmentButton;

    @FindBy(xpath = ".//span[text() = 'Manage department']")
    private WebElement manageDepartmentButton;

    @FindBy(xpath = ".//*[@class ='cl-icon total']/parent::span")
    private WebElement agentsQuantity;

    @FindBy(xpath = ".//*[@class ='cl-icon offline']/parent::span")
    private WebElement offlineAgentsNumber;

    @FindBy(xpath = ".//*[@class ='cl-icon online']/parent::span")
    private WebElement onlineAgentsNumber;

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

    @Step(value = "Click Manage Department Button")
    public void clickManageDepartmentButton(){
        clickElem(this.getCurrentDriver(), threeDotsMenu, 10, "Three dots menu");
        clickElem(this.getCurrentDriver(), manageDepartmentButton, 10, "Delete Department Button ");
    }

    public int getNumberOfAgents(){
       return Integer.parseInt(getTextFromElem(this.getCurrentDriver(), agentsQuantity, 0, "Quantity of Agents in card").trim());
    }

    public int getOfflineAgentsNumber(){
        return Integer.parseInt(getTextFromElem(this.getCurrentDriver(), offlineAgentsNumber, 0, "Quantity offline Agents in card").trim());
    }

    public int getOnlineAgentsNumber(){
        return Integer.parseInt(getTextFromElem(this.getCurrentDriver(), onlineAgentsNumber, 0, "Quantity online Agents in card").trim());
    }


}
