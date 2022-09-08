@no_widget
@no_chatdesk
Feature: Managing business details

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4563")
  @Regression
  Scenario: CD :: Dashboard :: Settings :: Business Profile :: Verify if admin is able to change/edit on business details
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Business Profile page
    Then Admin can see Settings page with options Business Profile, Chat tags, Auto Responders, Preferences, Surveys
    And Change business details
    Then Refresh page and verify business details was changed for Automation Bot



