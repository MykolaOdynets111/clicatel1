@no_widget
@Regression
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2776")
  Scenario: CD :: Supervisor Desk :: Closed Chat :: Verify if "Start chat" is available in closed chats
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    And Verify that closed chats have Message Customer button
