@no_widget
@orca_api
@Regression
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2799")
  Scenario: CD:: Flag Chat:: Supervisor desk :: Verify if supervisor can filter chats by "Flagged Only" Conversation status
    Given Setup ORCA abc integration for General Bank Demo tenant
    And I login as Agent of General Bank Demo
    When Send chat for flagging message by ORCA
    Then Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    When Agent click 'Flag chat' button
    Then Agent sees 'flag' icon in this chat
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Supervisor Desk in submenu
    When Supervisor put a check mark on "Flagged Only" and click "Apply Filters" button
    Then Orca request is shown on Supervisor Desk Live page
    And Agent click On Live Supervisor Desk chat from ORCA channel
    And Agent click 'Unflag chat' button