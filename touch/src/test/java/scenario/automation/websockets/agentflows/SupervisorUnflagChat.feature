@Regression
@no_widget
@orca_api
Feature: Supervisor can unflag live chat

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

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2983")
  Scenario: CD :: Agent Desk :: Live Chat :: Flag Chat :: Verify if agent receives "error message" when trying to close the flagged chat
    Given I login as agent of Automation
    And Setup ORCA whatsapp integration for Automation tenant
    And Send to agent message by ORCA
    And Agent has new conversation request from ORCA user
    And Agent click on new conversation request from ORCA
    When Agent click 'Flag chat' button
    And Agent sees 'flag' icon in this chat
    Then Agent hover over "Exit chat" button and see You do not have the ability to close the chat when it has been flagged message
    And Conversation area becomes active with to agent user's message