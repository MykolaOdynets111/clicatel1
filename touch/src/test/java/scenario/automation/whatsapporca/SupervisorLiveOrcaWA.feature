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
#    ToDo added wait till issue with filter will be resolved.
    And Wait for 10 second
    And Agent search chat ORCA on Supervisor desk
    Then ORCA request is shown on Supervisor Desk Live page
    And Agent click On Live Supervisor Desk chat from ORCA channel
    Then Supervisor Desk Live chat container header display "No current Agent" instead of agent name
    Then Verify Orca returns Agent Busy message autoresponder during 40 seconds


  @TestCaseId("https://jira.clickatell.com/browse/TPORT-91643")
  Scenario: CD :: Supervisor Desk :: Chats :: Verify the Chats that are currently in the 'Pending' tab will have a yellow 'Pending' icon on them in the Supervisor view
    Given I login as agent of Automation
    When Setup ORCA whatsapp integration for Automation tenant
    And Send to agent message by ORCA
    And Agent has new conversation request from ORCA user
    And Agent click on new conversation request from ORCA
    When Agent click 'Pending' chat button
    Then Agent receives pending message with orca user name
    Given I open portal
    And Login into portal as an admin of Automation account
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent search chat orca on Supervisor desk
    And Agent click On Live Supervisor Desk chat from ORCA channel
    Then Verify Chat has pending icon in the Chat List
    Then Verify Chat has pending icon in the Chat View