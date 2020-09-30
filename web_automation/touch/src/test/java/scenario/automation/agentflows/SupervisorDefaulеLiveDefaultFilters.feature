Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-38811")
  Scenario: Supervisor desk:: Default live chats Filters
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Supervisor Desk in submenu
    Then "All Channels" and "All Sentiments" selected as default
    And Filter "All live chats" is selected by default