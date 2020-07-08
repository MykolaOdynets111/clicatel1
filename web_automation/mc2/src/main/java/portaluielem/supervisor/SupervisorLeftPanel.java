package portaluielem.supervisor;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".cl-routed-tabs__tab-panel")
public class SupervisorLeftPanel extends AbstractUIElement {

    @FindBy(xpath="//button[contains(@class, 'selected')]//div[@class='cl-chats-group-item__name']")
    private WebElement defaultFilter;

    public String getFilterByDefaultName(){
        return getTextFromElem(this.getCurrentDriver(), defaultFilter,5,"Default filter").trim();
    }

}
