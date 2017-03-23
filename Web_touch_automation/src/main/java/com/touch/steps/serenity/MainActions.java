package com.touch.steps.serenity;

import com.touch.pages.ChatRoomPage;
import com.touch.pages.LoginPageWeb;
import com.touch.pages.cards.*;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by kmakohoniuk on 11/18/2016.
 */
public class MainActions extends ScenarioSteps {
    ChatRoomPage chatRoomPage;
    LoginPageWeb loginPage;
    NewTicketCard newTicketCard;
    TicketsCard openTickets;
    OpenAccountCard openAccount;
    CreditCardSelectionCard selectCreditCard;
    CalendarCardElement calendarCardElement;
    ContactDetailsCard contactDetailsCard;

    @Step
    public void openMainPage() {
        loginPage.open();
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    @Step
    public void refreshPage() {
        loginPage.refreshBrowser();
    }

    @Step
    public void selectClickatellTenant() {
        loginPage.clickOnClickatellRB();
    }

    @Step
    public void selectGenbankTenant() {
        loginPage.clickOnGenbankRB();
    }

    @Step
    public void clickOnTenantRBWithName(String name) {
        loginPage.clickOnTenantRBWithName(name);
    }

    @Step
    public void openChatRoom() {
        loginPage.clickOnOpenChatRoomButton();
    }

    @Step
    public void closeAndOpenChatRoom() {
        loginPage.clickOnCloseChatRoomButton();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        loginPage.clickOnOpenChatRoomButton();
    }
    @Step
    public void closeChatRoom() {
        loginPage.clickOnCloseChatRoomButton();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Step
    public void closeChatRoomIfEnable() {
        if(loginPage.isCloseChatRoomButtonEnable()){
        loginPage.clickOnCloseChatRoomButton();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
    }
    @Step
    public void clickOnButtonWithText(String text) {
        chatRoomPage.clickOnButtonWithText(text);
    }
    @Step
    public void clickOnRadioButtonWithText(String text) {
        chatRoomPage.clickOnRadioButtonWithText(text);
    }
    @Step
    public String getLastResponceMessage() {
        return chatRoomPage.getLastResponceMessage();
    }

    @Step
    public String getPreviousResponceMessage() {
        return chatRoomPage.getPreviousResponceMessage();
    }

    @Step
    public String getYourLastRequet() {
        return chatRoomPage.getYourLastRequest();
    }

    @Step
    public List<String> getListOfButtonsNameFromLastCard() {
        return chatRoomPage.getListOfButtonsNameFromLastCard();
    }

    @Step
    public List<String> getListOfButtonsNameFromPreviousCard() {
        return chatRoomPage.getListOfButtonsNameFromPreviousCard();
    }

    @Step
    public List<String> getListOfButtonsNameFromCardWithNumberFromEnd(int number) {
        return chatRoomPage.getListOfButtonsNameFromCardWithNumberFromEnd(number);
    }

    @Step
    public List<String> getListOfInformationRowsFromLastCard() {
        return chatRoomPage.getListOfInformationRowsFromLastCard();
    }

    @Step
    public List<String> getListOfInformationRowsFromPreviousCard() {
        return chatRoomPage.getListOfInformationRowsFromPreviousCard();
    }

    @Step
    public List<String> getListOfInformationRowsFromCardWithNumberFromEnd(int number) {
        return chatRoomPage.getListOfInformationRowsFromCardWithNumberFromEnd(number);
    }

    @Step
    public void openCardWithNumberFromTheEnd(int number) {
        chatRoomPage.openFaqDetailsForCardWithNumberFromEnd(number);
    }
    @Step
    public void openRowDetaisInLastCard(String rowTitle) {
        chatRoomPage.openDetailsForRowFromLastCard(rowTitle);
    }
    @Step
    public void goDownToLastCard() {
        chatRoomPage.goDownToLastCard();
    }
    @Step
    public void goDownToTheBottom() {
        chatRoomPage.goDownToTheBottom();
    }
    @Step
    public String getTitleOfLastCard() {
        return chatRoomPage.getTitleFromLastCard();
    }

    @Step
    public String getTitleFromCardWithNumberFromEnd(int number) {
        return chatRoomPage.getTitleFromCardWithNumberFromEnd(number);
    }

    @Step
    public String getTitleOfPreviousCard() {
        return chatRoomPage.getTitleFromPreviousCard();
    }

    @Step
    public void setLoginAndPasswordInCard(String login, String password) {
        chatRoomPage.setLoginAndPasswordInCard(login, password);
    }

    @Step
    public boolean isAccountInformationCardAppeared() {
        return chatRoomPage.isAccountInformationCardAppeared();
    }

    @Step
    public void setValueToFirstInputFieldInLastCard(String value) {
        chatRoomPage.setValueToFirstInputFieldInLastCard(value);
    }

    @Step
    public void selectRateInLastCard(String rate) {
        chatRoomPage.selectRateInLastCard(rate);
    }

    @Step
    public void setDataAndCreateNewTicket(String subject, String description, String email, String phone, String priority) {
        if (!subject.isEmpty())
            newTicketCard.setSubjectInput(subject);
        if (!description.isEmpty())
            newTicketCard.setDescriptionInput(description);
        if (!email.isEmpty())
            newTicketCard.setEmailInput(email);
        if (!phone.isEmpty())
            newTicketCard.setContactNumberInput(phone);
        switch (priority) {
            case "high":
                newTicketCard.clickOnHighPriorityRB();
                break;
            case "medium":
                newTicketCard.clickOnMediumPriorityRB();
                break;
            case "low":
                newTicketCard.clickOnLowPriorityRB();
                break;
            default:
                newTicketCard.clickOnLowPriorityRB();
        }
        newTicketCard.clickOnCreateButton();
    }

    @Step
    public void setMessageToChatAndClickEnter(String value) {
        chatRoomPage.setMessageToChatAndClickEnter(value);
    }

    @Step
    public List<Ticket> getTicketsFromCard(int number) {
        return openTickets.getTicketListFromCardWithNumberFromEnd(number);
    }

    @Step
    public void setDataAndOpenNewAccount(String name, String surname, String email, String phone) {
        if (!name.isEmpty())
            openAccount.setNameInput(name);
        if (!surname.isEmpty())
            openAccount.setSurnameInput(surname);
        if (!email.isEmpty())
            openAccount.setEmailInput(email);
        if (!phone.isEmpty())
            openAccount.setContactNumberInput(phone);
        openAccount.clickOnSubmitButton();
    }

    @Step
    public void selectCreditCardAndBlockIt(int number) {
        selectCreditCard.selectCreditCardWithNumbeInList(number);
        selectCreditCard.clickOnBlockButton();
    }

    @Step
    public void clickOnDayInCalenderElementInCard(String dayNumber) {
        calendarCardElement.clickOnDay(dayNumber);
    }
    @Step
    public void setContactDetailsAndSendIt(String firstName, String lastName, String email, String phone) {
        if (!firstName.isEmpty())
            contactDetailsCard.setFirstNameInput(firstName);
        if (!lastName.isEmpty())
            contactDetailsCard.setLastNameInput(lastName);
        if (!email.isEmpty())
            contactDetailsCard.setEmailInput(email);
        if (!phone.isEmpty())
           contactDetailsCard.setContactNumberInput(phone);
        contactDetailsCard.clickOnSubmitButton();
    }
}
