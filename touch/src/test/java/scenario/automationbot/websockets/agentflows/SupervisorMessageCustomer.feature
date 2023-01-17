@no_widget
@support_hours
@setting_changes
@Regression
@orca_api
Feature: Supervisor desk

  Background:
    Given I open portal
    And Setup ORCA sms integration for Automation Bot tenant
    And Set agent support hours with day shift
    And autoTicketScheduling tenant feature is set to false for Automation Bot
    And Login into portal as an admin of Automation Bot account
    And Send hello message by ORCA

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1713")
  Scenario: CD:: SMS :: Tickets :: Verify if Supervisor can initiate a SMS chat from tickets tab user using "Message Customer" button
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    And Agent search chat sms on Supervisor desk
    And Agent see tickets from sms on Unassigned filter page
    And Agent select sms ticket
    And Click on Message Customer button for sms
    And Message Customer Window is opened
    And Agent send Hi from Supervisor to agent trough SMS chanel
    Then Supervisor can see sms ticket with Hi from Supervisor message from agent

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1724")
  Scenario: CD :: SMS :: Tickets :: Verify Supervisor are unable to send WhatsApp HSM for tickets in SMS
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    And Agent select sms ticket
    And Click on Message Customer button for sms
    And Message Customer Window is opened
    And Agent is unable to send WhatsApp HSM on Tickets for sms chat