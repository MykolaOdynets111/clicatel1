@support_hours
@Regression
Feature: WhatsApp ORCA :: Supervisor Desk

  Background:
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    And Set agent support hours with day shift

  @no_widget
  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1896")
  Scenario: Supervisor desk: Verify if supervisor can use different filter options for filtering WhatsApp ORCA tickets
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send to agent message by ORCA
    And I select Touch in left menu and Supervisor Desk in submenu
    When Agent select "Tickets" left menu option
    And Agent search chat orca on Supervisor desk
    Then Ticket from orca is present on All tickets filter page
    When Agent refreshes the page
#toDo Uncomment when sentiments will be configured for tenant
#    And Agent filter by "Apple Business Chat" channel and "Positive" sentiment
#    Then Ticket from orca is present on "All tickets" filter page
    When Send to dsdfsdf message by ORCA
    And Send sfdsfsdfsd message by ORCA
#    When Agent refreshes the page
    And Agent filter by "Apple Business Chat" channel and "Neutral" sentiment
    Then Ticket from orca is not present on Supervisor Desk
#    When Send hate you message by ORCA
#    When Agent refreshes the page
#    And Agent filter by "Apple Business Chat" channel and "Negative" sentiment
#    Then Ticket from orca is present on "All tickets" filter page
#    When Agent filter by "Apple Business Chat" channel and "Positive" sentiment
#    Then Ticket from orca is not present on Supervisor Desk commented till chat name would be available


  @TestCaseId("https://jira.clickatell.com/browse/CCD-2801")
  Scenario: CD :: Supervisor Desk :: Tickets :: Verify if tickets are sorted from newest to oldest by default
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    Then Tickets are sorted in descending order

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6052")
  Scenario: CD:: Supervisor Desk:: Tickets:: Supervisor_Desk-Tickets-Closed:: Verify if Supervisor is able to view all the tickets in the closed tab

    Given Setup ORCA Whatsapp integration for Automation Bot tenant
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    And Send 1 messages chat to agent by ORCA
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option

    And Agent search chat orca on Supervisor desk
    Then Ticket from orca is present on All tickets filter page
    When Agent closed ticket for orca

    And Agent open Closed type
    Then Verify ticket is present for orca