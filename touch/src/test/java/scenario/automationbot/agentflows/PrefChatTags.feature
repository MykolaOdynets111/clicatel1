@no_widget
@no_chatdesk
Feature: Chat Tags

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2945")
  @Regression
  Scenario: CD :: Dashboard :: Configure :: Settings :: Verify if admin can go to settings from Configure option on admin dashboard
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Business Profile page
    Then admin can see Settings page with - options Business Profile, Chat Tags, Auto Responders, Preferences, Surveys