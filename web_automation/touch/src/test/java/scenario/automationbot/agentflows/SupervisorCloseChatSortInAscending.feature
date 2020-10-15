@no_widget
Feature: Supervisor desk

  Scenario: Supervisor desk :: Verify if Supervisor can sort with Chat Ended Ascending order
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    When Agent click on the arrow of Chat Ended
    Then Chats Ended are sorted in ascending order