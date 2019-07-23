@egor
Feature: Chat console: Inbox tab


  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4055")
  Scenario: Supervisor inbox :: Verify that 'Any' chats selected by default when a supervisor open an inbox
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Chat console in submenu
    And Select "Inbox" in nav menu
    Then Filter "Any" is selected by default
