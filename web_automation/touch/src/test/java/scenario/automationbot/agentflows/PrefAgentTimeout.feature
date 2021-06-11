@no_widget
@no_chatdesk
Feature: Preferences

  Scenario: Verify max 24 hours and min 10 minutes limits in Agent Chat Timeout section
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    When Agent set 0 hours and 9 minutes in Agent Chat Timeout section
    Then "Exceeds minimum limit. Duration must be between 10 minutes and 24 hours." error message is shown in Agent Chat Timeout section
    When Agent set 24 hours and 1 minutes in Agent Chat Timeout section
    Then "Exceeds maximum limit. Duration must be between 10 minutes and 24 hours." error message is shown in Agent Chat Timeout section
    When Agent set 0 hours and 10 minutes in Agent Chat Timeout section
    Then Error message is not shown in Agent Chat Timeout section
    When Agent set 24 hours and 0 minutes in Agent Chat Timeout section
    Then Error message is not shown in Agent Chat Timeout section
