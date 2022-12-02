@support_hours
@Regression
@no_widget
Feature: WhatsApp ORCA :: Supervisor Desk

  Background:
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    And Set agent support hours with day shift

  @TestCaseId("https://jira.clickatell.com/browse/CCD-7783")
  Scenario: CD :: Agent Desk :: Supervisor Desk:: Tickets:: Verify when Agent close the assigned ticket then the ticket is displayed at the top in the closed tab window
    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    And Send 1 messages chat to agent by ORCA
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    And Agent search chat orca on Supervisor desk
    Then Ticket from orca is present on All tickets filter page
    When Agent closed ticket for orca
    And Agent open Closed type
    Then Verify ticket is present for orca for 2 seconds

  @TestCaseId("https://jira.clickatell.com/browse/CCD-7779")
  Scenario: CD:: Agent Desk:: Tickets:: Agent_Desk-Tickets-Assigned:: Verify when Agent send message to Assigned ticket an error is not displayed as "Client already has a active conservation with Agent" when there is no active conversation
    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    And I select Touch in left menu and Supervisor Desk in submenu
    And Send 1 messages chat to agent by ORCA
    When Agent select "Tickets" left menu option
    And Agent search chat orca on Supervisor desk
    And Ticket from orca is present on All Tickets filter page
    And Select orca ticket checkbox
    And Click 'Assign manually' button for orca
    And 'Assign chat' window is opened
    And I assign chat on Agent
    And I select Touch in left menu and Agent Desk in submenu
    And Agent select "Tickets" left menu option
    And Agent select Assigned filter on Left Panel
    And Agent search chat orca on Supervisor desk
    Then Ticket from orca is present on Assigned filter page
    When Supervisor clicks on first ticket
    Then Agent checks closed ticket is disabled
    When Agent send new ticket message message
    And Wait for 2 second
    Then Supervisor can see orca ticket with new ticket message message from agent

  @TestCaseId("https://jira.clickatell.com/browse/CCD-7777")
  Scenario: CD :: Agent Desk :: Verify End-User is not connected to the agent when reinitiating a chat after a ticket is created without typing //end
    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    And I select Touch in left menu and Supervisor Desk in submenu
    And Send 1 messages chat to agent by ORCA
    When Agent select "Tickets" left menu option
    And Agent search chat orca on Supervisor desk
    And Ticket from orca is present on All Tickets filter page
    And Set agent support hours for all week
    And I select Touch in left menu and Agent Desk in submenu
    And Send 1 messages new agent by ORCA
    Then Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Agent checks visual indicator with text This chat has been assigned to GBD Main is shown during 2 seconds