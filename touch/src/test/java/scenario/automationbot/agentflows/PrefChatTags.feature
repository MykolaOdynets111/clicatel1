@no_widget
@no_chatdesk
Feature: Chat Tags

  @Regression
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2781")
  Scenario: CD :: Dashboard :: Settings :: Chat Tags :: Verify if Admin is able to cancel editing an existing tag
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Chat Tags page
    #And CRM ticket is created on backend with correct information
    Then Edit chat tag and click on X button

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2945")
  @Regression
  Scenario: CD :: Dashboard :: Configure :: Settings :: Verify if admin can go to settings from Configure option on admin dashboard
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Business Profile page
    Then admin can see Settings page with - options Business Profile, Chat tags,Auto Responders, Preferences and Surveys