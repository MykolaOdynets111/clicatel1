@no_widget
@no_chatdesk
Feature: Deactivating agent user

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4269")
  Scenario: Manage Agent User :: Not possible to deactivate an active admin account
    Given I open portal
    And Login into portal as an admin of Automation Common account
    When I select Touch in left menu and Manage Agent users in submenu
    And Click 'Manage' button for admin user
    And On the right corner of the page click "Deactivate User" button
    Then Error popup with text Cannot deactivate account owner. is shown





