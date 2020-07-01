package portaluielem;


import abstractclasses.AbstractWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class SuperviserDeskTicketsRow extends AbstractWidget {

    private WebElement baseWebElem = this.getWrappedElement();

    @FindBy(css = ".cl-r-checkbox__icon-container")
    private WebElement checkbox;

    @FindBy(xpath = ".//li[@class='cl-actions-dropdown__menu-item']//span[text()='Assign manually']")
    private WebElement assing;

    @FindBy(xpath = ".//div[@class='Select-placeholder']")
    private WebElement select;

    @FindBy(xpath = ".//div[@class='Select-menu']/div")
    private List<WebElement> listOfAgents;

    @FindBy(xpath = ".//div[@class='modal-footer']//button[text()='Assign']")
    private WebElement assignButton;

    @FindBy(css = ".cl-agent-name")
    private WebElement currentAgent;

    @FindBy(css = ".cl-r-chat-item-user-name")
    private WebElement userName;

    @FindBy(css = ".cl-table-user-description__location")
    private WebElement location;

    @FindBy(css = ".cl-light-grey span")
    private WebElement status;

    @FindBy(xpath = "//div[@class = 'cl-table-user-data__description']/div[2]")
    private WebElement phone;


    private String chatConsoleInboxRowNameCss = ".cl-user-name";

    public SuperviserDeskTicketsRow(WebElement element) {
        super(element);
    }

    public SuperviserDeskTicketsRow setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public void selectCheckbox(){
       clickElem(this.getCurrentDriver(), checkbox,0, "Ticket checkbox");
    }

    public void clickAssignManually(){
        clickElem(this.getCurrentDriver(), assing,3, "Assign manually button");
    }

    public void clickAgent(){
        clickElem(this.getCurrentDriver(), listOfAgents.get(0),3, "Agent in the list");
    }

    public void clickAssignButton(){
        clickElem(this.getCurrentDriver(), assignButton,3, "Assign button");
    }

    public String getChatConsoleInboxRowName(){
        WebElement name = baseWebElem.findElement(By.cssSelector(chatConsoleInboxRowNameCss));
        return name.getText();
    }

    public String getCurrentAgent(){
        return getTextFromElem(this.getCurrentDriver(), currentAgent, 5, "Current agent");
    }

    public String getUserName(){
        return userName.getText();//getTextFromElem(this.getCurrentDriver(), userName, 5, "User name");
    }

    public void clickOnUserName(){
        clickElem(this.getCurrentDriver(), userName, 5, "User Name");
    }

    public String getLocation(){
        return  getTextFromElem(this.getCurrentDriver(), location, 2, "Location");
    }

    public String getStatus(){
        return  getTextFromElem(this.getCurrentDriver(), status, 2, "Status");
    }

    public String getPhone(){
        return  getTextFromElem(this.getCurrentDriver(), phone, 2, "Phone");
    }


}
