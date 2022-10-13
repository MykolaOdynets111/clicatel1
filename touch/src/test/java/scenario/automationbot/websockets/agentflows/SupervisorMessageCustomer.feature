@no_widget
@support_hours
@chat_preferences
@Regression
Feature: Supervisor desk

  Background:
    Given I open portal
    And Setup ORCA sms integration for Automation tenant
    And Set agent support hours with day shift
    And autoTicketScheduling tenant feature is set to false for Automation
    And Login into portal as an admin of Automation account
    And Send hello message by ORCA

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1713")
  Scenario: CD:: SMS :: Tickets :: Verify if Supervisor can initiate a SMS chat from tickets tab user using "Message Customer" button
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    And Agent select sms ticket
    And Click on Message Customer button
    And Message Customer Window is opened
    And Supervisor send Hi from Supervisor to agent trough SMS chanel
    Then Supervisor can see sms ticket with Hi from Supervisor message from agent

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1903")
  Scenario: CD:: SMS :: Tickets :: Verify Agent is unable to send WhatsApp HSM for tickets in SMS chat
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    And Agent select sms ticket
    And Click on Message Customer button
    And Message Customer Window is opened
    And Supervisor is unable to send WhatsApp HSM on Tickets for sms chat