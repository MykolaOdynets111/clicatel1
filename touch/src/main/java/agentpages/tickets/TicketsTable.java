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

    public boolean getCheckboxStatus(){
        TicketRow supervisorDeskTicketRow = new TicketRow(tickets.get(0))
                .setCurrentDriver(this.getCurrentDriver());
        return supervisorDeskTicketRow.getCheckboxStatus();
    }

    public void clickAssignManuallyButton(String userName){
        getTicketByUserName(userName)
                .clickElem(this.getCurrentDriver(), assignTicketButton, 5, "Assign ticket button");
    }

    public boolean closeButtonStatus(){
        return Boolean.parseBoolean(getAttributeFromElem(this.getCurrentDriver(), closeTicket, 5, "Close ticket button", "disabled"));
    }

    public void clickAssignOpenTicketButton(String userName){
        getTicketByUserName(userName).moveToElemAndClick(this.getCurrentDriver(), openTicketButton);
    }

    public List<String> getUsersNames(){
        return tickets.stream()
                .map(e -> new TicketRow(e).setCurrentDriver(this.getCurrentDriver()))
                .map(TicketRow::getName)
                .collect(Collectors.toList());
    }

    public List<LocalDateTime> getTicketsStartDates() {
        List<LocalDateTime> startDates = tickets.stream().map(e -> new TicketRow(e).setCurrentDriver(this.getCurrentDriver())).collect(Collectors.toList())
                .stream().map(TicketRow::getOpenDate).collect(Collectors.toList());
        scrollTicketsToTheTop();
        return startDates;
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

    public boolean verifyCurrentChanelOfTheTickets(String channelName) {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), tickets, 7);
        return tickets.stream().anyMatch(e ->
                new TicketRow(e)
                        .setCurrentDriver(this.getCurrentDriver())
                        .getCurrentChannel().equals(channelName));
    }

    public boolean verifyCurrentDatesOfTheTickets(String dateType, String expectedDateText) {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), tickets, 7);
        return tickets.stream().anyMatch(e ->
                new TicketRow(e)
                        .setCurrentDriver(this.getCurrentDriver())
                        .getDateByName(dateType).contains(expectedDateText));
    }

    public boolean isTicketPresent(String userName) {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), tickets, 7);
        return tickets.stream().anyMatch(e ->
                new TicketRow(e)
                        .setCurrentDriver(this.getCurrentDriver())
                        .getName().equals(userName));
    }

    public int getTicketsCount() {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), tickets, 7);
        return tickets.size();
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
        supervisorDeskTicketRow.acceptTicket(1);
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
