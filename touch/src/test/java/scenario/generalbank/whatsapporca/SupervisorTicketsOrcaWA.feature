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
    And Update survey management chanel whatsapp settings by ip for Standard Billing
      | ratingEnabled | false        |
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
    And I assign chat on Agent for Agent dropdown
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

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6093")
  Scenario: CD :: Agent Desk :: Tickets :: Verify that notification message pop-up on the bottom right corner when Agent assign tickets to the himself
    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    And I select Touch in left menu and Supervisor Desk in submenu
    And Send 1 messages chat to agent by ORCA
    When Agent select "Tickets" left menu option
    And Agent search chat orca on Supervisor desk
    And Ticket from orca is present on All Tickets filter page
    And Select orca ticket checkbox
    And Click 'Assign manually' button for orca
    And 'Assign chat' window is opened
    And I assign chat on First Department for Department dropdown
    And Send 1 messages chat to agent by ORCA
    And Agent search chat orca on Supervisor desk
    And Ticket from orca is present on Unassigned filter page
    And Select orca ticket checkbox
    And Click 'Assign manually' button for orca
    And 'Assign chat' window is opened
    And I assign chat on First Department for Department dropdown
    And I select Touch in left menu and Agent Desk in submenu
    And Agent select "Tickets" left menu option
    And Agent accepts 2 unassigned tickets
    Then Agent checks ticket assigned toast message on the page appears and disappears
    When Agent select Assigned filter on Left Panel
    Then Agent search chat orca on Supervisor desk
    And Ticket from orca is present on Assigned filter page

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6092")
  Scenario: CD :: Agent Desk :: Tickets :: Unassigned :: Verify the tooltip text "Quickly accepts tickets from oldest to newest" when hovering on tooltip icon on the Quick accept bar
    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    And I select Touch in left menu and Supervisor Desk in submenu
    And Send 1 messages chat to agent by ORCA
    When Agent select "Tickets" left menu option
    And Agent search chat orca on Supervisor desk
    And Ticket from orca is present on All Tickets filter page
    And Select orca ticket checkbox
    And Click 'Assign manually' button for orca
    And 'Assign chat' window is opened
    And I assign chat on First Department for Department dropdown
    And I select Touch in left menu and Agent Desk in submenu
    And Agent select "Tickets" left menu option
    Then Agent checks quick & custom assign options on the page are visible
    And Agent hover over question info button and see Quickly accept tickets from oldest to newest message

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6089")
  Scenario: CD :: Agent Desk :: Tickets :: Verify that notification message pop-up on the bottom right corner when Supervisor assign tickets to the agent
    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    And I select Touch in left menu and Supervisor Desk in submenu
    And I login as second agent of General Bank Demo
    And Second Agent select "Tickets" left menu option
    And Send 1 messages chat to agent by ORCA
    When Agent select "Tickets" left menu option
    And Agent search chat orca on Supervisor desk
    And Ticket from orca is present on All Tickets filter page
    And Select orca ticket checkbox
    And Click 'Assign manually' button for orca
    And 'Assign chat' window is opened
    And I assign chat on second agent for Agent dropdown
    Then second agent checks ticket assigned toast message on the page appears and disappears