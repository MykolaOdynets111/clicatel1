@no_widget
@no_chatdesk
@Regression
Feature: Dashboard :: Settings :: Preferences

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1286")
  @setting_changes
  Scenario: CD :: Dashboard :: Configure :: Settings :: Preferences :: Verify if supervisor can switch the toggle on or off for the "Last agent" setting

    Given I login as agent of Automation Bot
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    And Turn off the Last Agent routing
    Then Verify if user can change Last Agent Routing status to off
    And Verify if user can change Last Agent Routing status to on