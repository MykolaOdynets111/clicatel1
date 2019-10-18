package agentpages.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;

public class CRMTicket extends AbstractWidget {

    protected CRMTicket(WebElement element) {
        super(element);
    }

    public CRMTicket setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    @FindBy(xpath = ".//p[@class='date']")
    private WebElement crmCreatedDate;

    @FindBy(xpath = ".//p[@class='note']")
    private WebElement crmNote;

    @FindBy(xpath = ".//a[@class='number']")
    private WebElement crmNumber;

    @FindBy(css = "button.edit")
    private WebElement editButton;

    @FindBy(css = "button.delete")
    private WebElement deleteButton;

    public Map<String, String> getTicketInfo(){
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("createdDate", crmCreatedDate.getText());
        infoMap.put("note", crmNote.getText());
        infoMap.put("number", crmNumber.getText());
        return infoMap;
    }


    public Map<String, String> getTicketInfoExceptDate(){
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("note", crmNote.getAttribute("innerText"));
        infoMap.put("number", crmNumber.getAttribute("innerText"));
        return infoMap;
    }

    public String getCreatedDate(){
        return crmCreatedDate.getText();
    }

    public void clickTicketNumber(){
        clickElem(this.getCurrentDriver(), crmNumber, 3,"CRM ticket number");
    }


    public void clickEditButton(){
        clickElem(this.getCurrentDriver(), editButton, 3,"CRM ticket 'Edit' button");
    }

    public void clickDeleteButton(){
        clickElem(this.getCurrentDriver(), deleteButton, 3,"CRM ticket 'Delete' button");
    }
}
