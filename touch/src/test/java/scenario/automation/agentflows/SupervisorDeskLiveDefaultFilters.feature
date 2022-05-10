Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7406")
  Scenario: Supervisor desk:: Verify that channel status "All Channels" and sentiment "All Sentiments" selected by default when a supervisor open supervisor desk
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Supervisor Desk in submenu
    Then "All Channels" and "All Sentiments" selected as default
    And Filter "All live chats" is selected by default