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

  @agent_support_hours
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-5317")
  Scenario: Verify if agent can change support hours
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Business Profile page
    When Select 'Specific Agent Support hours' radio button in Agent Supported Hours section
    And Uncheck today day and apply changes
    Then Check that today day is unselected in 'Scheduled hours' pop up
    And 'support hours' are updated in Automation Bot configs



