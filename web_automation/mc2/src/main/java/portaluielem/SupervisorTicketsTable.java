package portaluielem;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".supervisor-tickets")
public class SupervisorTicketsTable extends AbstractUIElement {

    @FindBy(css =".cl-table-body .cl-table-row")
    private List<WebElement> tickets;

    @FindBy(xpath = "//button[text() = 'Assign Manually']")
    private WebElement assignManuallyButton;

    private WebElement getTicketByName(String name){
        return tickets.stream().filter(e1 -> e1.getText().contains(name)).findFirst()
                .orElseThrow(() -> new AssertionError("Cannot find '" + name + "' ticket"));
    }

    public void selectTicketCheckbox(String getTicketByName){
        clickElem(this.getCurrentDriver(), getTicketByName(getTicketByName)
                .findElement(By.cssSelector(".cl-r-checkbox__icon-container")), 0, "Ticket Checkbox");
    }

    public void clickAssignManuallyButton(){
        clickElem(this.getCurrentDriver(), assignManuallyButton, 5, "'Route to scheduler' button");
    }

}
