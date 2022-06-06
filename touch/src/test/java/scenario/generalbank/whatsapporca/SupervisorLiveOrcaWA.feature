@no_widget
Feature: WhatsApp ORCA :: Supervisor Desk

  @orca_api
  @start_orca_server
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-114900")
  Scenario: CD :: Supervisor Desk :: Chats :: Verify if customer starts a chat there is no agent available, the chat is visible in Supervisor Desk as unassigned
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send to agent message by ORCA
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Supervisor Desk in submenu
#    ToDo added wait till issue with filter will be resolved
    And Wait for 10 second
    And Agent search chat ORCA on Supervisor desk
    Then ORCA request is shown on Supervisor Desk Live page
    And Agent click On Live Supervisor Desk chat from ORCA channel
    Then Supervisor Desk Live chat container header display "No current Agent" instead of agent name
    Then Verify Orca returns Agent Busy message autoresponder during 40 seconds