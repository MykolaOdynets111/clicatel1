@no_widget
@Regression
Feature: WhatsApp ORCA :: Supervisor Desk

  @orca_api
  @start_orca_server
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1178")
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

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2899")
  Scenario: CD:: Supervisor desk :: Supervisor_Desk-Chat:: Verify if supervisor can unflag chat
    Given I login as agent of Automation
    When Setup ORCA whatsapp integration for Automation tenant
    And Send to agent message by ORCA
    And Agent has new conversation request from ORCA user
    And Agent click on new conversation request from ORCA
    When Agent click 'Flag chat' button
    Then Agent sees 'flag' icon in this chat
    And I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent search chat ORCA on Supervisor desk
    And Agent click On Live Supervisor Desk chat from ORCA channel
    Then Supervisor Desk Live chat have 'flag on' button
    When Agent click 'Unflag chat' button
    Then Supervisor Desk Live chat from ORCA channel is unflagged