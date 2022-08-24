@no_widget
Feature: Supervisor desk

  Scenario: Supervisor desk:: Verify if tickets are sorted from newest to oldest by default
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    Then Tickets are sorted in descending order