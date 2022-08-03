@no_widget
Feature: WhatsApp ORCA :: Supervisor Desk

  @orca_api
  @start_orca_server
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-114900")
  Scenario: CD :: Supervisor Desk :: Chats :: Verify if customer starts a chat there is no agent available, the chat is visible in Supervisor Desk as unassigned
    Given Setup ORCA whatsapp integration for Automation tenant
    When Send to agent message by ORCA
    Given I open portal
    And Login into portal as an admin of Automation account
    And I select Touch in left menu and Supervisor Desk in submenu
#    ToDo added wait till issue with filter will be resolved
    And Wait for 10 second
    And Agent search chat ORCA on Supervisor desk
    Then ORCA request is shown on Supervisor Desk Live page
    And Agent click On Live Supervisor Desk chat from ORCA channel
    Then Supervisor Desk Live chat container header display "No current Agent" instead of agent name
    Then Verify Orca returns Agent Busy message autoresponder during 40 seconds

  @agent_support_hours
  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45507")
  Scenario: Supervisor desk:: Verify if supervisor can filter tickets by ticket status and apple channel filter option
    Given Setup ORCA abc integration for Automation tenant
    And Set agent support hours with day shift
    When Send to agent message by ORCA
    Given I open portal
    And Login into portal as an admin of Automation account
    And I select Touch in left menu and Supervisor Desk in submenu
    When Agent select "Tickets" left menu option
    And Agent search chat orca on Supervisor desk
    Then Ticket from orca is present on All tickets filter page
    And Select orca ticket checkbox
    When Click 'Assign manually' button
    Then 'Assign chat' window is opened
    When I assign chat on Agent
    And User select Assigned ticket type
    Then Ticket from orca is present on Assigned filter page
    When Agent select "Apple Business Chat" in Chanel container and click "Apply filters" button
    Then Verify that only "apple-business-chat" tickets chats are shown