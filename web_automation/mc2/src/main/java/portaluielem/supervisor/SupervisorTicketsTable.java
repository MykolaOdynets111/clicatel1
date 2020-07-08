package portaluielem.supervisor;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".supervisor-tickets")
public class SupervisorTicketsTable extends AbstractUIElement {

    @FindBy(css =".cl-table-body .cl-table-row")
    private List<WebElement> tickets;

    @FindBy(xpath = "//button[text() = 'Assign Manually']")
    private WebElement assignManuallyButton;

    @FindBy(css = "[selenium-id=roster-scroll-container]")
    private WebElement scrolArea;


    public SupervisorDeskTicketRow getTicketByUserName(String userName){
        return tickets.stream().map(e -> new SupervisorDeskTicketRow(e).setCurrentDriver(this.getCurrentDriver())).collect(Collectors.toList())
                .stream().filter(a -> a.getChatConsoleInboxRowName().toLowerCase()
                        .contains(userName.toLowerCase()))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find chat with user " + userName));
    }

    public void selectTicketCheckbox(String getTicketByName){
        getTicketByUserName(getTicketByName).selectCheckbox();
    }

    public void clickAssignManuallyButton(){
        clickElem(this.getCurrentDriver(), assignManuallyButton, 5, "'Route to scheduler' button");
    }

    public List<String> getUsersNames(){
        List<String> list =  tickets.stream()
                .map(e -> new SupervisorDeskTicketRow(e).setCurrentDriver(this.getCurrentDriver()))
                .map(e -> e.getChatConsoleInboxRowName())
                .collect(Collectors.toList());
        return list;
    }

    public void scrollTicketsToTheButtom(){
        wheelScroll(this.getCurrentDriver(), scrolArea, 2000, 0,0);
    }
}
