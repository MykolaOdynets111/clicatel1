package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".customers-overview")
public class CustomersOverviewTab extends AbstractUIElement {

    @FindBy(css = "[selenium-id='tab-customers-overview-tabs-Customers History']")
    private WebElement customersHistoryButton;

    public void clickOnCustomersHistory() {
        clickElem(this.getCurrentDriver(), customersHistoryButton, 5, "Customers History Tab");
    }

}
