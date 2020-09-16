package portaluielem.supervisor;


import abstractclasses.AbstractWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class SupervisorDeskTicketRow extends AbstractWidget {

    private WebElement baseWebElem = this.getWrappedElement();

    @FindBy(css = ".cl-r-checkbox__icon-container")
    private WebElement checkbox;

    @FindBy(xpath = ".//div[@class='Select-placeholder']")
    private WebElement select;

    @FindBy(css = ".cl-agent-name")
    private WebElement currentAgent;

    @FindBy(css = ".cl-user-name")
    private WebElement userName;

    @FindBy(css = ".cl-table-user-description__location")
    private WebElement location;

    @FindBy(css = ".cl-light-grey span")
    private WebElement status;

    @FindBy(xpath = "//div[@class = 'cl-table-user-data__description']/div[2]")
    private WebElement phone;


   // private String chatConsoleInboxRowNameCss = ".cl-user-name";

    public SupervisorDeskTicketRow(WebElement element) {
        super(element);
    }

    public SupervisorDeskTicketRow setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public void selectCheckbox(){
       clickElem(this.getCurrentDriver(), checkbox,0, "Ticket checkbox");
    }

    public String getName(){
        //WebElement name = baseWebElem.findElement(By.cssSelector(chatConsoleInboxRowNameCss));
        return  userName.getText();
    }

    public String getCurrentAgent(){
        return getTextFromElem(this.getCurrentDriver(), currentAgent, 5, "Current agent");
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
