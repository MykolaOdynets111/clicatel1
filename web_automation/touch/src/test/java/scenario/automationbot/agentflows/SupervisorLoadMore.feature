@no_widget
Feature: Supervisor in box : verify Load more

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7404")
  Scenario: Supervisor desk:: Verify if possible to load ticket chats by scrolling to the bottom
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    Given Save shown tickets
    When Supervisor scroll page to the bottom
    Then More tickets are loaded