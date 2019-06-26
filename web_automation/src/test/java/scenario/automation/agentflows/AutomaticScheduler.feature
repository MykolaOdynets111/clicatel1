@no_widget
@no_chatdesk
Feature: Automatic Scheduler

 @TestCaseId("https://jira.clickatell.com/browse/TPORT-4563")
  Scenario: Touch preference :: Verify if agent can enable/disable Automatic Scheduler
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Touch preferences in submenu
    And Click "Chat Desk" nav button
    And Change business details
    Then Refresh page and verify business details was changed for Automation Bot



