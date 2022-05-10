@no_widget
Feature: Supervisor desk

  Scenario: Supervisor desk :: Verify that Chat Ended is sorted in descending order by default
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    Then Chats Ended are sorted in descending order