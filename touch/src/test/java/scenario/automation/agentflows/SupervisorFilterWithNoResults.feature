@no_widget
Feature: Supervisor desk

  Background:
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Supervisor Desk in submenu

  Scenario: Supervisor desk: Verify if error message is displayed when no results are matched (search using only the filters without entering a MSISDN or Name)
    And Agent search chat NoChatsErrorMessage on Supervisor desk
    Then Error "Sorry, no results found. Please refine your search and try again" message is displayed
    When Agent select "Tickets" left menu option
    And Agent search chat NoChatsErrorMessage on Supervisor desk
    Then Error "Sorry, no results found. Please refine your search and try again" message is displayed
    When Agent select "Closed" left menu option
    And Agent search chat NoChatsErrorMessage on Supervisor desk
    Then Error "Sorry, no results found. Please refine your search and try again" message is displayed