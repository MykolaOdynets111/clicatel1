@no_widget
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7404")
  Scenario: Supervisor desk:: Verify if possible to load ticket chats by scrolling to the bottom
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    Given Save shown tickets
    When Supervisor scroll Tickets page to the bottom
    Then More tickets are loaded