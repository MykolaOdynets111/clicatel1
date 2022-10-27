@no_widget
@Regression
@orca_api
Feature: WhatsApp ORCA :: Supervisor Desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1178")
  @start_orca_server
  Scenario: CD :: Supervisor Desk :: Chats :: Verify if customer starts a chat there is no agent available, the chat is visible in Supervisor Desk as unassigned
    Given Setup ORCA whatsapp integration for Automation tenant
    And Send to agent message by ORCA
    When I open portal
    And Login into portal as an admin of Automation account
    Then I select Touch in left menu and Supervisor Desk in submenu
    When Agent search chat ORCA on Supervisor desk
    Then ORCA request is shown on Supervisor Desk Live page
    When Agent click On Live Supervisor Desk chat from ORCA channel
    And Supervisor Desk Live chat container header display "No current Agent" instead of agent name
    Then Verify Orca returns Agent Busy message autoresponder during 40 seconds for agent for agent

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1437")
  Scenario: CD :: Supervisor :: Live Chat :: Verify the agent name and date are displayed in the visual indicator in agent chat window
    Given I login as agent of Automation
    When Setup ORCA whatsapp integration for Automation tenant
    And Send to agent message by ORCA
    Given I open portal
    And Login into portal as an admin of Automation account
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent search chat ORCA on Supervisor desk
    Then ORCA request is shown on Supervisor Desk Live page
    And Agent click On Live Supervisor Desk chat from ORCA channel
    Then Supervisor Desk Live chat header display "Auto Main" Agent name
    And Supervisor Desk Live chat header display date