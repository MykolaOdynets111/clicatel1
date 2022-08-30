Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-105130")
  Scenario: Supervisor desk:: Verify if Supervisor receive correct message, when pressing 'reset password' in Profile Settings
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    And I click icon with GBD initials
    And admin clicks "Profile Settings" button
    And admin clicks "Reset Password" button
    Then admin views The reset password link has been successfully sent to your email message in Profile Settings
