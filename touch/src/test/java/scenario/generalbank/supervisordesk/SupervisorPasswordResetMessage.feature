@Regression
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1418")
  Scenario: CD:: Supervisor Desk:: Verify if Supervisor receive correct message, when pressing 'reset password' in Profile Settings
    Given I open portal
    And Login into portal as an Admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    And I click icon with GBD initials
    And Admin clicks "Profile Settings" button
    And Admin clicks "Reset Password" button
    Then Admin views The reset password link has been successfully sent to your email message in Profile Settings
