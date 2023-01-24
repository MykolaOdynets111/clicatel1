package agentpages.tickets;

import abstractclasses.AbstractUIElement;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".chats-list-wrapper.chats-list-extended-view")
public class TicketsTable extends AbstractUIElement {

    @FindBy(css = ".cl-table-row")
    private List<WebElement> tickets;

    @FindBy(xpath = ".//button[text() = 'Assign']")
    private WebElement assignTicketButton;

    @FindBy(xpath = ".//a[text() = 'Open']")
    private WebElement openTicketButton;

    @FindBy(xpath = ".//button[text() = 'Route to Scheduler']")
    private WebElement routeToSchedulerButton;

    @FindBy(css = ".supervisor-tickets__loading-more")
    private WebElement loadingMoreTickets;

    @FindBy(xpath = ".//span[text()='Ticket Opened']/ancestor::span/following-sibling::span/span[contains(@class, 'sorting-box__arrow--top')]")
    private WebElement ascendingArrowOfEndDateColumn;

    @NotNull
    private List<TicketRow> getTicketRows() {
        return tickets.stream()
                .map(e -> new TicketRow(e).setCurrentDriver(this.getCurrentDriver()))
                .collect(Collectors.toList());
    }

    @NotNull
    private TicketRow getTicketRow() {
        return new TicketRow(tickets.get(0))
                .setCurrentDriver(this.getCurrentDriver());
    }

    public TicketRow getTicketByUserName(String userName) {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), tickets, 7);
        return getTicketRows()
                .stream().filter(a -> a.getName().toLowerCase().contains(userName.toLowerCase()))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find chat with user " + userName));
    }

    public TicketsTable selectTicketCheckbox(String getTicketByName) {
        getTicketByUserName(getTicketByName).selectCheckbox();
        return this;
    }

    public boolean getCheckboxStatus() {
        return getTicketRow().getCheckboxStatus();
    }

    public void clickAssignManuallyButton(String userName) {
        getTicketByUserName(userName)
                .clickElem(this.getCurrentDriver(), assignTicketButton, 5, "Assign ticket button");
    }

    public void clickAssignOpenTicketButton(String userName) {
        getTicketByUserName(userName).moveToElemAndClick(this.getCurrentDriver(), openTicketButton);
    }

    public List<String> getUsersNames() {
        return tickets.stream()
                .map(e -> new TicketRow(e).setCurrentDriver(this.getCurrentDriver()))
                .map(TicketRow::getName)
                .collect(Collectors.toList());
    }

    public List<LocalDateTime> getTicketsStartDates() {
        List<LocalDateTime> startDates = getTicketRows().stream().map(TicketRow::getOpenDate).collect(Collectors.toList());
        scrollTicketsToTheTop();
        return startDates;
    }

    public void clickRouteToSchedulerButton() {
        clickElem(this.getCurrentDriver(), routeToSchedulerButton, 5, "'Route to scheduler' button");
    }

    public TicketsTable scrollTicketsToTheButton() {
        wheelScroll(this.getCurrentDriver(), this.getWrappedElement(), 2000, 0, 0);
        return this;
    }

    public void scrollTicketsToTheTop() {
        wheelScroll(this.getCurrentDriver(), this.getWrappedElement(), -2000, 0, 0);
    }

    public void waitForMoreTicketsAreLoading(int waitForSpinnerToAppear, int waitForSpinnerToDisappear) {
        waitForAppearAndDisappear(this.getCurrentDriver(), loadingMoreTickets, waitForSpinnerToAppear, waitForSpinnerToDisappear);
    }

    public LocalDateTime getFirstTicketOpenDates() {
        return getTicketRow().getOpenDate();
    }

    public LocalDateTime getFirstTicketEndDates() {
        return getTicketRow().getEndDate();
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

    public List<String> verifyCurrentChanelOfTheTickets() {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), tickets, 7);
        return tickets.stream().map(e -> new TicketRow(e).setCurrentDriver(this.getCurrentDriver()))
                .map(TicketRow::getCurrentChannel)
                .collect(Collectors.toList());
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
        getTicketRow().clickOnUserName();
        getTicketRow().openTicket(1);
    }

    public void acceptFirstTicket() {
        getTicketRow().clickOnUserName();
        getTicketRow().acceptTicket(1);
    }

    public boolean assignManuallyButtonTopPanelVisibility(String userName) {
        return getTicketByUserName(userName)
                .isElementRemoved(this.getCurrentDriver(), assignTicketButton, 5);
    }

    public boolean assignManuallyButtonFirstRowHoverVisibility() {
        getTicketRow().hoverOnUserName();
        return getTicketRow().assignManualButtonVisibility();
    }
}
