@no_widget
@no_chatdesk
Feature: Preferences

  Scenario: Verify max 120 hours and min 48 hours limits in Ticket Expiration section
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    When Agent set 47 hours in Ticket Expiration section
    Then "Exceeds minimum limit. Duration must be between 48 and 120 hours." error message is shown in Ticket Expiration section
    When Agent set 121 hours in Ticket Expiration section
    Then "Exceeds maximum limit. Duration must be between 48 and 120 hours." error message is shown in Ticket Expiration section
    When Agent set 48 hours in Ticket Expiration section
    Then Error message is not shown in Ticket Expiration section
    When Agent set 120 hours in Ticket Expiration section
    Then Error message is not shown in Ticket Expiration section