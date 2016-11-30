package com.touch.jbehave;

import com.touch.pages.cards.Ticket;
import com.touch.steps.serenity.MainActions;
import com.touch.steps.serenity.VerificationActions;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmakohoniuk on 11/18/2016.
 */
public class FlowSteps {
    List<Ticket> addedTicketsList;

    @Steps
    MainActions mainSteps;
    @Steps
    VerificationActions verification;

    @Given("Open main user page")
    public void openMainPage() {
        mainSteps.openMainPage();
    }

    @Given("Refresh page")
    public void refreshPage() {
        mainSteps.refreshPage();
    }

    @Given("Select Clickatell tenant and open chat")
    public void selectClickatellAndOpenChatRoom() {
        mainSteps.selectClickatellTenant();
        mainSteps.openChatRoom();
    }
    @Given("Select Genbank tenant and open chat")
    public void selectGenbankAndOpenChatRoom() {
        mainSteps.selectGenbankTenant();
        mainSteps.openChatRoom();
    }
    @Given("Bug fixer step-> close and open chat")
    public void closeAndOpenChatRoom() {
        mainSteps.closeAndOpenChatRoom();
    }
    @When("Click on Card-Button with text: '$text'")
    public void clickOnButtonWithText(String text) {
        mainSteps.clickOnButtonWithText(text);
    }

    @When("Login as user '$login' with password '$password'")
    public void login(String login, String password) {
        mainSteps.setLoginAndPasswordInCard(login, password);
        mainSteps.clickOnButtonWithText("Log In");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("Rate your experience with rate: '$rate' and leave comments: '$comments'")
    public void setRateAndComments(String rate, String comments) {
        mainSteps.selectRateInLastCard(rate);
        if (!comments.isEmpty()) {
            mainSteps.setValueToFirstInputFieldInLastCard(comments);
        }
        mainSteps.clickOnButtonWithText("Submit");
    }
    @When("Select credit card which is '$number' in card and block it")
    public void selectCreditCard(String number) {
        mainSteps.selectCreditCardAndBlockIt(Integer.valueOf(number));
    }
    @When("Select day '$dayNumber' in calender in card")
    public void selectDayInCalendar(String dayNumber) {
        mainSteps.clickOnDayInCalenderElementInCard(dayNumber);
    }
    @When("Type message to chat: '$message' and press Enter")
    public void setRateAndComments(String message) {
        mainSteps.setMessageToChatAndClickEnter(message);
    }

    @Then("Verify that last Card has following data:$data")
    public void verifyLastTCardContent(ExamplesTable data) {
        List<String> expectedButtons = new ArrayList<>();
        List<String> expectedInfos = new ArrayList<>();
        for (int i=0;i<data.getRowCount();i++){
            String type =data.getRowAsParameters(i, true).valueAs("type", String.class);
            String value = data.getRowAsParameters(i, true).valueAs("value", String.class);
            switch (type) {
                case "title":
                    verification.verifyThatValuesAreEqual(value, mainSteps.getTitleOfLastCard());
                    break;
                case "info":
                    expectedInfos.add(value);
                    break;
                case "button":
                    expectedButtons.add(value);
                    break;
                default:
                    Assert.assertTrue("User or add other type in CardModel", false);
            }
        }
        if (expectedInfos.size() != 0)
            verification.verifyThatListsContainsSameElements(expectedInfos, mainSteps.getListOfInformationRowsFromLastCard());
        if (expectedButtons.size() != 0)
            verification.verifyThatListsContainsSameElements(expectedButtons, mainSteps.getListOfButtonsNameFromLastCard());
    }

    @When("Add new ticket:$data")
    public void addNewTicket(ExamplesTable data) {
        for (int i=0;i<data.getRowCount();i++) {
            String subject = data.getRowAsParameters(i, true).valueAs("subject", String.class);
            String email = data.getRowAsParameters(i, true).valueAs("email", String.class);
            String description = data.getRowAsParameters(i, true).valueAs("description", String.class);
            String phone = data.getRowAsParameters(i, true).valueAs("phone", String.class);
            String priority = data.getRowAsParameters(i, true).valueAs("priority", String.class);
            mainSteps.setDataAndCreateNewTicket(subject, description, email, phone, priority);

        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addedTicketsList = mainSteps.getTicketsFromCard(Integer.valueOf(2));

    }
    @When("Open account:$data")
    public void openAccount(ExamplesTable data) {
        for (int i=0;i<data.getRowCount();i++) {
            String name = data.getRowAsParameters(i, true).valueAs("name", String.class);
            String surname = data.getRowAsParameters(i, true).valueAs("surname", String.class);
            String email = data.getRowAsParameters(i, true).valueAs("email", String.class);
            String phone = data.getRowAsParameters(i, true).valueAs("phone", String.class);
            mainSteps.setDataAndOpenNewAccount(name, surname, email, phone);
        }
    }

    @Then("Verify that list from card with number: '$number' from the end contains new ticket added previously")
    public void verifyOpenTickets(String number) {
        List<Ticket> ticketsFromCard = mainSteps.getTicketsFromCard(Integer.valueOf(number));
        Assert.assertTrue(ticketsFromCard.containsAll(addedTicketsList));

    }

    @Then("Verify that card with number: '$number' from the end has following data:$data")
    public void verifyPreviousTCardContent(ExamplesTable data, String number) {
        List<String> expectedButtons = new ArrayList<>();
        List<String> expectedInfos = new ArrayList<>();
        for (int i=0;i<data.getRowCount();i++) {
            String type = data.getRowAsParameters(i, true).valueAs("type", String.class);
            String value = data.getRowAsParameters(i, true).valueAs("value", String.class);
            switch (type) {
                case "title":
                    verification.verifyThatValuesAreEqual(value, mainSteps.getTitleOfPreviousCard());
                    break;
                case "info":
                    expectedInfos.add(value);
                    break;
                case "button":
                    expectedButtons.add(value);
                    break;
                default:
                    Assert.assertTrue("User or add other type in CardModel", false);
            }
        }
        verification.verifyThatListsContainsSameElements(expectedInfos, mainSteps.getListOfInformationRowsFromCardWithNumberFromEnd(Integer.valueOf(number)));
        verification.verifyThatListsContainsSameElements(expectedButtons, mainSteps.getListOfButtonsNameFromCardWithNumberFromEnd(Integer.valueOf(number)));
    }

    @Then("Verify that last your request was: '$message'")
    public void verifyYourLastRequest(String message) {
        verification.verifyThatValuesAreEqual(message, mainSteps.getYourLastRequet());
    }

    @Then("Verify that last response was: '$message'")
    public void verifyLastResponse(String message) {
        verification.verifyThatValuesAreEqual(message, mainSteps.getLastResponceMessage());
    }

    @Then("Verify that last response match with: '$regexp'")
    public void verifyLastResponseMatchWith(String regexp) {
        verification.verifyThatValuesAreMatching(regexp, mainSteps.getLastResponceMessage());
    }

    @Then("Verify that previous response was: '$message'")
    public void verifyPreviousResponse(String message) {
        verification.verifyThatValuesAreEqual(message, mainSteps.getPreviousResponceMessage());
    }

    @Then("Verify that Account Information card is available")
    public void verifyThatWeCanSeeAccountInformationCard() {
        Assert.assertTrue("Account Information card is not available. Look to Screen screen shot", mainSteps.isAccountInformationCardAppeared());
    }

    @Then("Verify that card with number: '$number' from the end contains ticket information:$data")
    public void verifyThatCardWithNumberFromTheEndContainsTicketInformation(int number, ExamplesTable data) throws Throwable {
        for (int i=0;i<data.getRowCount();i++) {
            String subject = data.getRowAsParameters(i, true).valueAs("subject", String.class);
            String description = data.getRowAsParameters(i, true).valueAs("description", String.class);
            verification.verifyThatValuesAreEqual(description, addedTicketsList.get(0).getDescription());
            verification.verifyThatValuesAreEqual(subject, addedTicketsList.get(0).getSubject());
            verification.verifyThatValuesAreEqual(mainSteps.getTitleFromCardWithNumberFromEnd(Integer.valueOf(number)), "Ticket created: " + addedTicketsList.get(0).getTicketNumber());
        }
        List<Ticket> ticketsFromCard = mainSteps.getTicketsFromCard(Integer.valueOf(number));
        Assert.assertTrue(ticketsFromCard.containsAll(addedTicketsList));
    }
}
