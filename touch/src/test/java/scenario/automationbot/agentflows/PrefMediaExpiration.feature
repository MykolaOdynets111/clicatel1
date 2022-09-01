@no_widget
@no_chatdesk
Feature: Preferences

  Scenario: Verify max 90 days and min 7 days limits in Media Files Expiration section
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    When Agent set 6 days in Media Files Expiration section
    Then "Exceeds minimum limit. Duration must be between 7 days and 90 days." error message is shown in Media Files Expiration section
    When Agent set 91 days in Media Files Expiration section
    Then "Exceeds maximum limit. Duration must be between 7 days and 90 days." error message is shown in Media Files Expiration section
    When Agent set 7 days in Media Files Expiration section
    Then Error message is not shown in Media Files Expiration section
    When Agent set 90 days in Media Files Expiration section
    Then Error message is not shown in Media Files Expiration section

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-110427")
  @Regression
  Scenario: CD :: Dashboard :: Settings :: Preferences :: Verify that the minimum value for media deletion setting is 7 days
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    When Agent set 6 days in Media Files Expiration section
    Then "Exceeds minimum limit Duration must be between 7 days and 90 days" error message is shown in Media Files Expiration section