package agentpages.tickets;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".chats-list-wrapper.chats-list-extended-view")
public class TicketsTable extends AbstractUIElement {

    @FindBy(css=".cl-table-row")
    private List<WebElement> tickets;

    @FindBy(xpath = "//button[text() = 'Assign']")
    private WebElement assignTicketButton;

    @FindBy(xpath = "//button[text() = 'Close']")
    private WebElement closeTicketButton;

    @FindBy(xpath = "//button[text() = 'Accept']")
    private WebElement acceptTicketButton;

    @FindBy(xpath = "//button[@name = 'Close Ticket']")
    private WebElement closeTicket;

    @FindBy(xpath = ".//a[text() = 'Open']")
    private WebElement openTicketButton;

    @FindBy(xpath = "//button[text() = 'Route to Scheduler']")
    private WebElement routeToSchedulerButton;

    @FindBy(css = ".supervisor-tickets__loading-more")
    private WebElement loadingMoreTickets;

    @FindBy (xpath = "//span[text()='Ticket Opened']/ancestor::span/following-sibling::span/span[contains(@class, 'sorting-box__arrow--top')]")
    private  WebElement ascendingArrowOfEndDateColumn;

    public TicketRow getTicketByUserName(String userName){
        waitForFirstElementToBeVisible(this.getCurrentDriver(), tickets, 7);
        return tickets.stream().map(e -> new TicketRow(e).setCurrentDriver(this.getCurrentDriver())).collect(Collectors.toList())
                .stream().filter(a -> a.getName().toLowerCase()
                        .contains(userName.toLowerCase()))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find ticket with user " + userName));
    }

    public TicketsTable selectTicketCheckbox(String getTicketByName){
        getTicketByUserName(getTicketByName).selectCheckbox();
        return this;
    }

    public void clickAssignManuallyButton(String userName){
        getTicketByUserName(userName)
                .clickElem(this.getCurrentDriver(), assignTicketButton, 5, "Assign ticket button");
    }

    public void clickCloseButton(String userName){
        getTicketByUserName(userName).clickElem(this.getCurrentDriver(), closeTicketButton, 5, "Close ticket button");
    }

    public void clickAcceptButton(String userName){
        getTicketByUserName(userName).clickElem(this.getCurrentDriver(), acceptTicketButton, 5, "Accept ticket button");
    }

    public boolean closeButtonStatus(){
        return Boolean.parseBoolean(getAttributeFromElem(this.getCurrentDriver(), closeTicket, 5, "Close ticket button", "disabled"));
    }

    public void clickAssignOpenTicketButton(String userName){
        getTicketByUserName(userName)
                .moveToElemAndClick(this.getCurrentDriver(), openTicketButton);
//                .clickElem(this.getCurrentDriver(), openTicketButton, 5, "Open ticket button");
    }

    public List<String> getUsersNames(){
        List<String> list =  tickets.stream()
                .map(e -> new TicketRow(e).setCurrentDriver(this.getCurrentDriver()))
                .map(e -> e.getName())
                .collect(Collectors.toList());
        return list;
    }

    public List<LocalDateTime> getTicketsStartDates() {
        List<LocalDateTime> startDates = tickets.stream().map(e -> new TicketRow(e).setCurrentDriver(this.getCurrentDriver())).collect(Collectors.toList())
                .stream().map(a -> a.getOpenDate()).collect(Collectors.toList());
        scrollTicketsToTheTop();
        return startDates;
    }

    public List<LocalDateTime> getTicketsEndDates(){
        List<LocalDateTime> endDates = tickets.stream().map(e -> new TicketRow(e).setCurrentDriver(this.getCurrentDriver())).collect(Collectors.toList())
                .stream().map(a -> a.getEndDate()).collect(Collectors.toList());
        scrollTicketsToTheTop();
        return endDates;
    }

    public void clickRouteToSchedulerButton(){
        clickElem(this.getCurrentDriver(), routeToSchedulerButton, 5, "'Route to scheduler' button");
    }

    public TicketsTable scrollTicketsToTheButtom(){
        wheelScroll(this.getCurrentDriver(), this.getWrappedElement(), 2000, 0,0);
        return this;
    }

    public void scrollTicketsToTheTop(){
        wheelScroll(this.getCurrentDriver(), this.getWrappedElement(), -2000, 0,0);
    }

    public void waitForMoreTicketsAreLoading(int waitForSpinnerToAppear, int waitForSpinnerToDisappear){
        waitForAppearAndDisappear(this.getCurrentDriver(), loadingMoreTickets, waitForSpinnerToAppear, waitForSpinnerToDisappear);
    }

    public LocalDateTime getFirstTicketOpenDates() {
        return new TicketRow(tickets.get(0)).setCurrentDriver(this.getCurrentDriver()).getOpenDate();
    }

    public LocalDateTime getFirstTicketEndDates() {
        return new TicketRow(tickets.get(0)).setCurrentDriver(this.getCurrentDriver()).getEndDate();
    }

    public void clickAscendingArrowOfEndDateColumn() {
        clickElem(this.getCurrentDriver(), ascendingArrowOfEndDateColumn, 3,
                "Ascending Arrow Of End Date Column");
    }

    public boolean verifyChanelOfTheTicketsIsPresent(String channelName) {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), tickets, 7);
        return tickets.stream()
                .map(e -> new TicketRow(e).setCurrentDriver(this.getCurrentDriver()).isValidChannelImg(channelName)).findFirst().get();
    }

    public boolean isTicketPresent(String userName) {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), tickets, 7);
        return tickets.stream().anyMatch(e ->
                new TicketRow(e)
                        .setCurrentDriver(this.getCurrentDriver())
                        .getName().equals(userName));
    }

    public void openFirstTicket() {
        TicketRow supervisorDeskTicketRow = new TicketRow(tickets.get(0))
                .setCurrentDriver(this.getCurrentDriver());
        supervisorDeskTicketRow.clickOnUserName();
        supervisorDeskTicketRow.openTicket(1);
    }

    public void acceptFirstTicket() {
        TicketRow supervisorDeskTicketRow = new TicketRow(tickets.get(0))
                .setCurrentDriver(this.getCurrentDriver());
        supervisorDeskTicketRow.clickOnUserName();
        supervisorDeskTicketRow.acceptTicket();
    }

    public boolean assignManuallyButtonTopPanelVisibility(String userName){
        return getTicketByUserName(userName)
                .isElementRemoved(this.getCurrentDriver(), assignTicketButton, 5);
    }

    public boolean assignManuallyButtonFirstRowHoverVisibility() {
        TicketRow supervisorDeskTicketRow = new TicketRow(tickets.get(0))
                .setCurrentDriver(this.getCurrentDriver());
        supervisorDeskTicketRow.hoverOnUserName();
        return supervisorDeskTicketRow.assignManualButtonVisibility();
    }
}
