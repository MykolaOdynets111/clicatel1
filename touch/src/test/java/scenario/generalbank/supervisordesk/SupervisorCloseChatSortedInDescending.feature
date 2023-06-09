@no_widget
@Regression
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2823")
  Scenario: CD::Supervisor desk :: Verify that Chat Ended is sorted in descending order by default
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    Then Chats Ended are sorted in descending order