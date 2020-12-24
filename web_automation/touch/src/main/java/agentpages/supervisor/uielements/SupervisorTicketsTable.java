package agentpages.supervisor.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".supervisor-tickets")
public class SupervisorTicketsTable extends AbstractUIElement {

    @FindBy(css = ".cl-table-body .cl-table-row")
    private List<WebElement> tickets;

    @FindBy(xpath = "//button[text() = 'Assign Manually']")
    private WebElement assignManuallyButton;

    @FindBy(css = "[selenium-id=roster-scroll-container]")
    private WebElement scrolArea;

    @FindBy(xpath = "//button[text() = 'Route to Scheduler']")
    private WebElement routeToSchedulerButton;

    @FindBy(css = ".supervisor-tickets__loading-more")
    private WebElement loadingMoreTickets;

    @FindBy (xpath = "//span[text()='End Date']/ancestor::span/following-sibling::span/span[contains(@class, 'sorting-box__arrow--top')]")
    private  WebElement ascendingArrowOfEndDateColumn;

    public SupervisorDeskTicketRow getTicketByUserName(String userName){
        waitForFirstElementToBeVisible(this.getCurrentDriver(), tickets, 7);
        return tickets.stream().map(e -> new SupervisorDeskTicketRow(e).setCurrentDriver(this.getCurrentDriver())).collect(Collectors.toList())
                .stream().filter(a -> a.getName().toLowerCase()
                        .contains(userName.toLowerCase()))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find ticket with user " + userName));
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
                .map(e -> e.getName())
                .collect(Collectors.toList());
        return list;
    }

    public List<LocalDateTime> getTicketsStartDates() {
        List<LocalDateTime> startDates = tickets.stream().map(e -> new SupervisorDeskTicketRow(e).setCurrentDriver(this.getCurrentDriver())).collect(Collectors.toList())
                .stream().map(a -> a.getStartDate()).collect(Collectors.toList());
        scrollTicketsToTheTop();
        return startDates;
    }

    public List<LocalDateTime> getTicketsEndDates(){
        List<LocalDateTime> endDates = tickets.stream().map(e -> new SupervisorDeskTicketRow(e).setCurrentDriver(this.getCurrentDriver())).collect(Collectors.toList())
                .stream().map(a -> a.getEndDate()).collect(Collectors.toList());
        scrollTicketsToTheTop();
        return endDates;
    }

    public void clickRouteToSchedulerButton(){
        clickElem(this.getCurrentDriver(), routeToSchedulerButton, 5, "'Route to scheduler' button");
    }

    public void scrollTicketsToTheButtom(){
        wheelScroll(this.getCurrentDriver(), scrolArea, 2000, 0,0);
    }

    public void scrollTicketsToTheTop(){
        wheelScroll(this.getCurrentDriver(), scrolArea, -2000, 0,0);
    }

    public void waitForMoreTicketsAreLoading(int waitForSpinnerToAppear, int waitForSpinnerToDisappear){
        waitForAppearAndDisappear(this.getCurrentDriver(), loadingMoreTickets, waitForSpinnerToAppear, waitForSpinnerToDisappear);
    }

    public void loadAllFoundTickets() {
        int ticketsSize;
        do {
            ticketsSize = tickets.size();
            scrollTicketsToTheButtom();
            waitForMoreTicketsAreLoading(2, 5);
        } while (ticketsSize != tickets.size());
        scrollTicketsToTheTop();
    }

    public LocalDateTime getFirstTicketStartDates() {
        return new SupervisorDeskTicketRow(tickets.get(0)).setCurrentDriver(this.getCurrentDriver()).getStartDate();
    }

    public LocalDateTime getFirstTicketEndDates() {
        return new SupervisorDeskTicketRow(tickets.get(0)).setCurrentDriver(this.getCurrentDriver()).getStartDate();
    }

    public void clickAscendingArrowOfEndDateColumn(){
        clickElem(this.getCurrentDriver(), ascendingArrowOfEndDateColumn, 3,
                "Ascending Arrow Of End Date Column");
    }

    public boolean verifyChanelOfTheTicketsIsPresent(String channelName){
        waitForFirstElementToBeVisible(this.getCurrentDriver(), tickets, 7);
        return  tickets.stream()
                .map(e -> new SupervisorDeskTicketRow(e).setCurrentDriver(this.getCurrentDriver()))
                .allMatch(closedChat -> closedChat.getIconName().equalsIgnoreCase(channelName));
    }
}
