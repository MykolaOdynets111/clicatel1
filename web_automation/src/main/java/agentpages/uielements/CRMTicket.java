package agentpages.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;

public class CRMTicket extends Widget implements WebActions {

    protected CRMTicket(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);

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

    public void clickTicketNumber(){
        clickElemAgent(crmNumber, 3, "main", "CRM ticket number");
    }


    public void clickEditButton(){
        clickElemAgent(editButton, 3, "main", "CRM ticket 'Edit' button");
    }

    public void clickDeleteButton(){
        clickElemAgent(deleteButton, 3, "main", "CRM ticket 'Delete' button");
    }
}
