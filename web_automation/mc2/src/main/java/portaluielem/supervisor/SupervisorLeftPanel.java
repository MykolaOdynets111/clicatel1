package portaluielem.supervisor;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".cl-routed-tabs__tab-panel")
public class SupervisorLeftPanel extends AbstractUIElement {

    @FindBy(xpath="//a[contains(@class, 'selected')]//div[@class='cl-chats-group-item__name']")
    private WebElement defaultFilter;

    @FindBy(css = ".cl-chats-group-item__name")
    private List<WebElement> ticketsFilterNames;

    public String getFilterByDefaultName(){
        return getTextFromElem(this.getCurrentDriver(), defaultFilter,5,"Default filter").trim();
    }

    public List<String> getFilterNames(){
        return ticketsFilterNames.stream().map(a-> a.getText().trim()).collect(Collectors.toList());
    }

     public void selectTicketType(String type) {
         ticketsFilterNames.stream().filter(a -> a.getText().trim().equalsIgnoreCase(type)).findFirst()
                 .orElseThrow(() -> new AssertionError("Cannot find " + type + " conversation type dropdown option")).click();
     }

}
